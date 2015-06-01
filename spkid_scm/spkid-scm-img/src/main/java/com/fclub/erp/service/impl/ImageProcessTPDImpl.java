package com.fclub.erp.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fclub.common.lang.BizException;
import com.fclub.common.lang.utils.DateUtil;
import com.fclub.common.lang.utils.FileUtil;
import com.fclub.common.lang.utils.ImageUtil;
import com.fclub.common.lang.utils.RandomUtils;
import com.fclub.common.lang.utils.StringUtil;
import com.fclub.common.log.LogUtil;
import com.fclub.erp.dto.goods.BatchResultVo;
import com.fclub.erp.remote.domain.ProcessStatus;
import com.fclub.erp.service.ImageProcessTPD;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.ImportImgRecord;
import com.fclub.tpd.dataobject.Product;
import com.fclub.tpd.dataobject.ProductGallery;
import com.fclub.tpd.helper.ConstantsHelper;
import com.fclub.tpd.mapper.ColorMapper;
import com.fclub.tpd.mapper.ImportImgRecordMapper;
import com.fclub.tpd.mapper.ProductGalleryMapper;
import com.fclub.tpd.mapper.ProductMapper;
import com.fclub.tpd.mapper.ProductSubMapper;

/**
 * 图片具体处理实现类
 */
@Service
public class ImageProcessTPDImpl implements ImageProcessTPD {
	
	public static final int[]				 DEF_IMG_SIZE = new int[]{850, 850};
	public static final List<int[]>			 GEN_IMG_SIZE = Arrays.asList(
			new int[]{850, 850}, new int[]{418, 418}, new int[]{220, 220},
			new int[]{175, 175}, new int[]{150, 150}, new int[]{140, 140}, new int[]{85 , 85 }
	);
	

	/** 商品默认图标识 */
    public static final String               GOODS_DEFAULT_IMG_FLAG  = "default";
    /** 商品色片标识 */
    public static final String               GOODS_TONAL_IMG_FLAG    = "tonal";
    /** 商品局部图标识 */
    public static final String               GOODS_PART_IMG_FLAG     = "part";
	
	
    private static final LogUtil              logger                 = LogUtil
                                                                         .getLogger(ImageProcessTPDImpl.class);

    private static final ExecutorService      thread_pool            = Executors
                                                                         .newFixedThreadPool(Integer.valueOf(System
                                                                             .getProperty(SystemConstant.REMOTE_IMAGESERVICE_CPU)) * 4);

    private static final String               FAILURE_FLAG           = "failure";

    private static Map<String, ProcessStatus> INFOS                  = new ConcurrentHashMap<>();
    private static final String               PREFIX                 = "[图片服务器："
                                                                       + ConstantsHelper.getImageServerIp()
                                                                       + "] ";

    private static final String               MSG_INFO               = "  [INFO] ";
    //private static final String               MSG_DEBUG       = " [DEBUG] ";
    private static final String               MSG_WARN               = "  [WARN] ";
    private static final String               MSG_ERROR              = " [ERROR] ";

    private static final DateFormat           LOG_DATE_FORMAT        = new SimpleDateFormat(
                                                                         "yyyy-MM-dd HH:mm:ss");

    private static final float                DEFAULT_OUTPUT_QUALITY = 0.9f;

    @Autowired
    private ProductMapper                    goodsMapper;
    @Autowired
    private ProductSubMapper               goodsLaborMapper;
    @Autowired
    private ProductGalleryMapper             goodsGalleryMapper;
    @Autowired
    private ColorMapper                       colorMapper;
    @Autowired
    private ImportImgRecordMapper             importImgRecordMapper;

    private boolean                           connectDB              = true;

    public void process(String batchNo, String channel, List<String> dirs, Integer adminId) {

        ProcessStatus processStatus = new ProcessStatus();
        processStatus.setTotalDirs(dirs);
        INFOS.put(batchNo, processStatus);

        //        System.out.println(LOG_DATE_FORMAT.format(new Date())
        //                           + " ============================> begin");

        logger.info(PREFIX + "process image begin...");
        processStatus.getLogs().add(
            LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "开始处理图片...");

        long begin = System.currentTimeMillis();

        //FIXME ftp目录是否需改变
        String path = ConstantsHelper.getImageInPathTpd(channel);

        /*
         * ThreadPoolManager thread_pool = new
         * ThreadPoolManager(Integer.valueOf(System
         * .getProperty(SystemConstant.REMOTE_IMAGESERVICE_CPU)) * 4);
         */

        final List<Future<String>> futureList = new ArrayList<Future<String>>(dirs.size());

        for (String dir : dirs) {
            if (StringUtils.isBlank(dir)) {
                continue;
            }
            futureList.add(thread_pool.submit(new ProcessThread(batchNo, path, dir, adminId,
                processStatus)));
        }

        // 阻塞至线程结束
        blockThread(futureList, processStatus);

        long ms = (System.currentTimeMillis() - begin) / 1000;
        logger
            .info(
                PREFIX
                        + "process image end: total dir num is {0}, success num is {1}, total takes {2} ms",
                dirs.size(), processStatus.getSuccessDirs().size(),
                (System.currentTimeMillis() - begin));
        processStatus.getLogs().add(
            LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "处理图片结束: 总共处理" + dirs.size()
                    + "个目录，成功了 " + processStatus.getSuccessDirs().size() + "个目录，共用时" + (ms / 60)
                    + "分 " + (ms % 60) + "秒");

        /*
         * thread_pool.destroy(); thread_pool = null;
         */

        System.gc();
        System.runFinalization();

        /*
         * // 源文件处理方案 final boolean isBackFile = true; try { Thread.sleep(1000);
         * } catch (InterruptedException e) { // ignore }
         * 
         * for (String dir : dirs) { if (StringUtils.isBlank(dir)) { continue; }
         * try { String srcPath = path + dir; if (!isBackFile) {
         * FileUtils.deleteDirectory(new File(srcPath)); } else { String
         * destPath = System.getProperty(SystemConstant.UPLOAD_ROOT_DIR) +
         * System.getProperty(SystemConstant.IMAGE_BATCH_BACK) + channel;
         * FileUtils.moveDirectoryToDirectory(new File(srcPath), new
         * File(destPath), true); }
         * 
         * } catch (IOException e) { logger.error(PREFIX + "delete dir " + dir +
         * " failed: ", e); processStatus.getLogs().add(
         * LOG_DATE_FORMAT.format(new Date()) + MSG_ERROR + PREFIX + "备份目录：" +
         * dir + "失败：" + e); } }
         */
        processStatus.setFinished(true);
        //        System.out.println(LOG_DATE_FORMAT.format(new Date())
        //                           + " ============================> end");
        // return result;
    }

    private void blockThread(List<Future<String>> futureList, ProcessStatus processStatus) {

        for (Future<String> future : futureList) {
            try {
                String dir = future.get();
                processStatus.getProcessDirs().add(dir);
                if (!FAILURE_FLAG.equals(dir)) {
                    processStatus.getSuccessDirs().add(dir);
                    //                    System.out.println("==========================> " + dir);
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error(PREFIX + "occurence thread exception: ", e);
                processStatus.getLogs()
                    .add(MSG_ERROR + PREFIX + "occurence thread exception: " + e);
                continue;
            } finally {
                processStatus.increaseProcess();
            }
        }
    }

    /**
     * 处理一个图片目录
     */
    private void processDir(String batchNo, File imageDir, Integer adminId, BatchResultVo resultVo,
                            List<String> logs) {

        List<ProductGallery> insertGalleries = new ArrayList<>();
        int[] deleteGallery = new int[2];

        String[] imgDir = imageDir.getName().split("-");
        if (imgDir.length != 3 && imgDir.length != 4) {
            throw new BizException("目录结构不对: " + imageDir.getName());
        }
        // imgDir[0] 货号
        String colorCode = imgDir[1];
        String goodsSn = imgDir[2];
        String rep = "";
        if (imgDir.length == 4) {
            rep = imgDir[3];
        }

        resultVo.setGoodsSn(goodsSn);
        resultVo.setColorCode(colorCode);
        resultVo.setRep(rep);

        if (StringUtils.isNotBlank(rep) && !StringUtils.equalsIgnoreCase(rep, "REP")) {
            throw new BizException("覆盖方式须使用[-REP]为后缀");
        }
        if (goodsSn.length() < 10 || goodsSn.length() > 15) {
            throw new BizException("商品款号必须为10-15位");
        }
        Integer goodsId = null;
        Integer colorId = null;
        Integer modelId = null;
        if (connectDB) {
            goodsId = goodsMapper.findByGoodsSn(goodsSn);
            if (goodsId == null) {
                throw new BizException("商品款号不存在");
            }
            Product g = goodsMapper.get(goodsId);
            if(g.getGoodsAudit() == 1) {
                throw new BizException("商品已审核，无法导入");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("colorName", colorCode);
            colorId = colorMapper.queryByName(param);
            if (colorId == null) {
                throw new BizException("颜色名称不存在");
            }
            if (goodsLaborMapper.getCountByGoodsAndColor(goodsId, colorId) < 1) {
                throw new BizException("款号与颜色对应的商品不存在");
            }
        }

        boolean append = true;
        if (StringUtils.isNotBlank(rep)) {
            deleteGallery[0] = goodsId;
            deleteGallery[1] = colorId;
//            deleteGalleryImg(goodsId, colorId, logs);
        } else {
            for (File image : imageDir.listFiles()) {
                if (!image.isFile()) {
                    continue;
                }
                if (image.getName().endsWith(".db")) {
                    continue;
                }
                //                if (image.getName().startsWith("model") && image.getName().endsWith(".txt")) {
                //                    continue;
                //                }
                String imageName = image.getName().substring(0, image.getName().lastIndexOf("."));
                String imageSuffix = image.getName().substring(image.getName().lastIndexOf("."))
                    .toLowerCase();
                if (!ArrayUtils.contains(ImageUtil.SUFFIX_IMAGE, imageSuffix)) {
                    logger.error("图片格式不对：" + image.getName());
                    continue;
                }
                if (goodsId == null || colorId == null) {
                    continue;
                }
                if (StringUtils.equals(imageName, "1")) { // 默认图
                    if (goodsGalleryMapper.getCountByGoodsColorImg(goodsId, colorId, GOODS_DEFAULT_IMG_FLAG) > 0) {
                        throw new BizException("该款号颜色默认图片已存在");
                    }
                    append = false;
                    //                } else if (StringUtils.equals(imageName, "2")) { // 色片
                    //                    if (goodsGalleryMapper.getCountByGoodsColorImg(goodsId, colorId,
                    //                        CommonConstants.GOODS_TONAL_IMG_FLAG) > 0) {
                    //                        throw new BizException("该款号颜色色片图片已存在");
                    //                    }
                    //                    append = false;
                }
            }
        }

        // 处理图片
        int index = 0;
        if (append) {
            Integer count = goodsGalleryMapper.getCountByGoodsColorImg(goodsId, colorId, null);
            if (count != null && count != 0) {
                index = count;
            }
        }
        for (File image : imageDir.listFiles()) {
            if (!image.isFile()) {
                continue;
            }
            if (image.getName().endsWith(".db")) {
                continue;
            }

            // 商品模特
            //            if (image.getName().startsWith("model") && image.getName().endsWith(".txt")) {
            //                String modelCode = StringUtils.trim(image.getName().substring(5,
            //                    image.getName().indexOf(".txt")));
            //                if(StringUtils.isNotBlank(modelCode)) {
            //                    modelId = goodsModelMapper.getModelIdByCode(modelCode);
            //                    if(modelId == null) {
            //                        throw new BizException("找不到模特[模特编码:"+modelCode+"]");
            //                    }
            //                }
            //                continue;
            //            }
            // logger.info("[ image server: {0} ] process image image {1}/{2} begin",
            // getIp(), imageDir.getName(), image.getName());
            long begin = System.currentTimeMillis();

            String imageName = image.getName().substring(0, image.getName().lastIndexOf("."));
            String imageSuffix = image.getName().substring(image.getName().lastIndexOf("."))
                .toLowerCase();

            if (!ArrayUtils.contains(ImageUtil.SUFFIX_IMAGE, imageSuffix)) {
                logger.error("图片格式不对：" + image.getName());
                continue;
            }
            
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(new FileInputStream(image));
            } catch (IOException e) {
                logger.error("图片格式不对：" + image.getName(), e);
            }
            // TODO: 暂时不检查原图尺寸
            if (bi != null && (bi.getWidth() != DEF_IMG_SIZE[0] || bi.getHeight() != DEF_IMG_SIZE[1])) {
                throw new BizException("图片规格不对，请使用尺寸为"+DEF_IMG_SIZE[0]+"*"+DEF_IMG_SIZE[1]+"的原图 [" + image.getName() + "]");
            }

            String imgDefault = "";
            if (StringUtils.equals(imageName, "1")) { // 默认图
                imgDefault = GOODS_DEFAULT_IMG_FLAG;
                //            } else if (StringUtils.equals(imageName, "2")) { // 色片
                //                imgDefault = CommonConstants.GOODS_TONAL_IMG_FLAG;
            } else { // 局部
                imgDefault = GOODS_PART_IMG_FLAG;
            }

            // 生成图片，保存到数据库
            ProductGallery gallery = newDefaultProductGallery();
            if (goodsId == null) {
                goodsId = Integer.valueOf(RandomUtils.generateNumString(8));
            }
            if (colorId == null) {
                colorId = Integer.valueOf(RandomUtils.generateNumString(8));
            }
            gallery.setGoodsId(goodsId);
            gallery.setColorId(colorId);
            gallery.setImgDefault(imgDefault);
            gallery.setImgAid(adminId);
            gallery.setImgTime(DateUtil.getCurrentTime());
            int sort = 0;
            try {
                sort = Integer.valueOf(imageName);
            } catch (NumberFormatException e) {
                // 名称不是数字
                sort = 20;
            }
            gallery.setSortOrder(100 - sort);
            try {
                generateGallary(image, bi, gallery, imageSuffix, logs, ++index);
            } catch (Exception e) {
                logger.error(PREFIX + "process image " + imageDir.getName() + "/" + image.getName() + " failed: ", e);
                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_ERROR + PREFIX + "处理图片："
                         + imageDir.getName() + "/" + image.getName() + "失败：" + e.getMessage());

                throw new BizException("商品图片导入异常，请联系管理员");
            }

            insertGalleries.add(gallery);

            logger.info(PREFIX + "process image {0}/{1} success, use {2} ms", imageDir.getName(),
                image.getName(), System.currentTimeMillis() - begin);
            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "处理图片："
                     + imageDir.getName() + "/" + image.getName() + "成功，用时"
                     + (System.currentTimeMillis() - begin) + "毫秒");
            image = null;
        }

        if (connectDB) {
            try {
                operateDirData(batchNo, insertGalleries, deleteGallery, goodsSn, colorCode,
                    goodsId, colorId, adminId, logs, modelId);
            } catch (Exception e) {
                logger.error(PREFIX + "insert db dir: {0} error: {1}", imageDir.getName(), e);
                //                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX
                //                    + "目录："+imageDir.getName()+"操作数据库失败：" + e.getMessage());

                for (ProductGallery record : insertGalleries) {
                    deleteImageFile(record);
                }
                throw new RuntimeException("数据库异常：", e);
            }
        }
    }

	/**
     * 操作数据库
     */
    @Transactional
    private void operateDirData(String batchNo, List<ProductGallery> insertGalleries,
                                int[] deleteGallery, String goodsSn, String colorCode,
                                Integer goodsId, Integer colorId, Integer adminId,
                                List<String> logs, Integer modelId) {

        if (deleteGallery[0] != 0) {
            deleteGalleryImg(deleteGallery[0], deleteGallery[1], logs);
            logger.info(PREFIX + "delete gallery when REP: [goodsId={0}, colorId={1}]",
                deleteGallery[0], deleteGallery[1]);
            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX
                     + "覆盖方式时删除原图及记录: [goodsId=" + deleteGallery[0] + ", colorId="
                     + deleteGallery[1] + "]");
        }
        for (ProductGallery record : insertGalleries) {
            goodsGalleryMapper.insert(record);
            logger.info(
                PREFIX + "insert into gallery : [goodsId={0}, colorId={1}, imgDefault={2}]",
                record.getGoodsId(), record.getColorId(), record.getImgDefault());
            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX
                     + "插入gallery表：[goodsId=" + record.getGoodsId() + ", colorId="
                     + record.getColorId() + ", imgDefault=" + record.getImgDefault() + "]");
        }

        if (batchNo.length() > 16) {
            batchNo = batchNo.substring(0, batchNo.length() - 16);
        }

        insertImportRecord(batchNo, adminId, goodsSn, colorCode, goodsId, colorId);
        logger.info(PREFIX + "insert import_img_record : [batchNo={0}, goodsId={1}, colorId={2}]",
            batchNo, goodsId, colorId);
        logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "插入导入记录: [batchNo="
                 + batchNo + ", goodsId=" + goodsId + ", colorId=" + colorId + "]");

//        if (modelId != null) {
//            Goods goods = new Goods();
//            goods.setGoodsId(goodsId);
//            goods.setModelId(modelId);
//            goodsMapper.updateModelById(goods);
//            logger.info(PREFIX + "update goods modelId : [goodsId={0}, modelId={1}]", goodsId,
//                modelId);
//            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "更新商品模特ID: [goodsId="
//                     + goodsId + ", modelId=" + modelId + "]");
//        }
    }

    /**
     * 插入图片导入记录
     */
    private void insertImportRecord(String batchNo, Integer adminId, String goodsSn,
                                    String colorCode, Integer goodsId, Integer colorId) {
        ImportImgRecord record = new ImportImgRecord();
//        record.setImpBatchNo(StringUtils.substringBefore(batchNo, "-REP"));
        record.setGoodsSn(goodsSn);
        record.setGoodsId(goodsId);
        record.setColorCode(colorCode);
        record.setColorId(colorId);
        record.setIrAid(adminId);
        record.setIrTime(new Date());
        importImgRecordMapper.insert(record);
    }

    /**
     * 覆盖方式时删除原图
     */
    private void deleteGalleryImg(Integer goodsId, Integer colorId, List<String> logs) {
        if (goodsId == null || colorId == null) {
            return;
        }
        List<ProductGallery> list = goodsGalleryMapper.getByGoodsColor(goodsId, colorId);
        for (ProductGallery gallery : list) {
            deleteImageFile(gallery);
        }
        goodsGalleryMapper.deleteByGoodsColor(goodsId, colorId);
        logger.info(PREFIX + "delete gallery when REP: [goodsId={0}, colorId={1}]", goodsId,
            colorId);
        logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "覆盖方式时删除原图及记录: [goodsId="
                 + goodsId + ", colorId=" + colorId + "]");
    }

    /**
     * 生成缩略图并保存
     * 
     * @param imageFile
     * @param gallery
     * @param imageSuffix
     * @throws Exception
     */
    private void generateGallary(File imageFile, BufferedImage image, ProductGallery gallery, String imageSuffix,
                                 List<String> logs, int index) throws Exception {

        // gallery
        gallery.setImgDesc("");
        String path[] = getOutFilePath(gallery.getGoodsId());
        String domaimPath = path[0];
        String outPath = path[1];
        if (StringUtils.equals(gallery.getImgDefault(), GOODS_TONAL_IMG_FLAG)) {

            String outFileName = getOutFileName(gallery.getGoodsId(), gallery.getColorId(),
                "goods", imageSuffix, index);
            // 保存文件
            // UploadUtil.dumpAsset(imageFile, outFileName, outPath);
            // FileUtil.moveFile(imageFile, outPath + File.separator +
            // outFileName);
            FileUtil.copyFile(imageFile, outPath + File.separator + outFileName);

            String imgUrl = domaimPath + outFileName;

            gallery.setImgUrl(imgUrl);
//            gallery.setImgOriginal(imgUrl);
//            gallery.setThumbUrl(imgUrl);
//            gallery.setMiddleUrl(imgUrl);
//            gallery.setBigUrl(imgUrl);
//            gallery.setTeenyUrl(imgUrl);
//            gallery.setSmallUrl(imgUrl);
//            gallery.setUrl120160(imgUrl);
//            gallery.setUrl99132(imgUrl);
//            gallery.setUrl480640(imgUrl);
//            gallery.setUrl5684(imgUrl);
//            gallery.setUrl222296(imgUrl);
//            gallery.setUrl342455(imgUrl);
//            gallery.setUrl170227(imgUrl);
//            gallery.setUrl135180(imgUrl);
//            gallery.setUrl251323(imgUrl);
//            gallery.setUrl502646(imgUrl);
//            gallery.setUrl12001600(imgUrl);

        } else {
            String imgOriginalOutFileName = getOutFileName(gallery.getGoodsId(), gallery.getColorId(), null, imageSuffix, index);

            String imgOriginal = domaimPath + imgOriginalOutFileName;
            gallery.setImgOriginal(imgOriginal);

            if(image == null) {
                image = ImageIO.read(new FileInputStream(imageFile));
            }
            //            gallery.setImgUrl(genFile(image, path, gallery, imageSuffix, "goods", new int[] { 1800,
            //                    2400 }, true));
            gallery.setImgUrl(imgOriginal);
            
            doGenerateImages(image, path, gallery, imgOriginalOutFileName, imageSuffix);
//            gallery.setThumbUrl(genFile(image, path, gallery, imageSuffix, "thumb", new int[] { 72,
//                    96 }, true, index));
//            gallery.setMiddleUrl(genFile(image, path, gallery, imageSuffix, "middle", new int[] {
//                    270, 360 }, true, index));
//            gallery.setBigUrl(genFile(image, path, gallery, imageSuffix, "big", new int[] { 600,
//                    800 }, true, index));
//            gallery.setTeenyUrl(genFile(image, path, gallery, imageSuffix, "teeny", new int[] { 30,
//                    40 }, true, index));
//            gallery.setSmallUrl(genFile(image, path, gallery, imageSuffix, "small", new int[] {
//                    180, 240 }, true, index));
//            gallery.setUrl120160(genFile(image, path, gallery, imageSuffix, "120x160", new int[] {
//                    120, 160 }, true, index));
//            gallery.setUrl99132(genFile(image, path, gallery, imageSuffix, "99x132", new int[] {
//                    99, 132 }, true, index));
//            gallery.setUrl480640(genFile(image, path, gallery, imageSuffix, "480x640", new int[] {
//                    480, 640 }, true, index));
//            gallery.setUrl5684(genFile(image, path, gallery, imageSuffix, "63x84", new int[] { 63,
//                    84 }, true, index));
//            gallery.setUrl222296(genFile(image, path, gallery, imageSuffix, "222x296", new int[] {
//                    222, 296 }, true, index));
//            gallery.setUrl342455(genFile(image, path, gallery, imageSuffix, "342x455", new int[] {
//                    342, 455 }, false, index));
//            gallery.setUrl170227(genFile(image, path, gallery, imageSuffix, "170x227", new int[] {
//                    170, 227 }, true, index));
//            gallery.setUrl135180(genFile(image, path, gallery, imageSuffix, "135x180", new int[] {
//                    135, 180 }, true, index));
//            gallery.setUrl251323(genFile(image, path, gallery, imageSuffix, "251x323", new int[] {
//                    251, 323 }, false, index));
//            gallery.setUrl502646(genFile(image, path, gallery, imageSuffix, "502x646", new int[] {
//                    502, 646 }, false, index));
//            gallery.setUrl12001600(genFile(image, path, gallery, imageSuffix, "1200x1600",
//                new int[] { 1200, 1600 }, true, index));
            FileUtil.copyFile(imageFile, outPath + File.separator + imgOriginalOutFileName);
        }
    }

    private void doGenerateImages(BufferedImage image, String[] path, ProductGallery gallery, String sourceImgName, String imageSuffix) {
    	for (int[] size : GEN_IMG_SIZE) {
    		genFile(image, path, gallery, imageSuffix, sourceImgName, size, true);
    	}
	}

	private void deleteImageFile(ProductGallery gallery) {
        String path = ConstantsHelper.getPicRootPath();
        System.err.println(path);
        
        String sourceImg = gallery.getImgUrl();
        if (StringUtils.isNotBlank(sourceImg)) {
        	String imageSuffix = sourceImg.substring(sourceImg.lastIndexOf(".")).toLowerCase();
        	for (int[] size : GEN_IMG_SIZE) {
        		FileUtil.deleteFile(new File(path, buildResizeImageName(sourceImg, size, imageSuffix)));
        	}        	
        	FileUtil.deleteFile(new File(path, sourceImg));
        }
        
//        if (StringUtils.isNotBlank(gallery.getThumbUrl())) {
//        }
//        if (StringUtils.isNotBlank(gallery.getMiddleUrl())) {
//            FileUtil.deleteFile(new File(path, gallery.getMiddleUrl()));
//        }
//        if (StringUtils.isNotBlank(gallery.getBigUrl())) {
//            FileUtil.deleteFile(new File(path, gallery.getBigUrl()));
//        }
//        if (StringUtils.isNotBlank(gallery.getTeenyUrl())) {
//            FileUtil.deleteFile(new File(path, gallery.getTeenyUrl()));
//        }
//        if (StringUtils.isNotBlank(gallery.getSmallUrl())) {
//            FileUtil.deleteFile(new File(path, gallery.getSmallUrl()));
//        }
//        if (StringUtils.isNotBlank(gallery.getImgOriginal())) {
//            FileUtil.deleteFile(new File(path, gallery.getImgOriginal()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl120160())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl120160()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl99132())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl99132()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl480640())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl480640()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl5684())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl5684()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl135180())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl135180()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl251323())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl251323()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl502646())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl502646()));
//        }
//        if (StringUtils.isNotBlank(gallery.getUrl12001600())) {
//            FileUtil.deleteFile(new File(path, gallery.getUrl12001600()));
//        }
    }

    /**
     * 剪裁图片 [default output quality is 0.9f]
     * 
     * @param image 原图
     * @param path [数据库路径, 全路径]
     * @param gallery
     * @param imageSuffix 图片后缀
     * @param sourceImgName
     * @param size [width, height]
     * @param keepAspectRatio 是否保持原始比例
     * @param index
     */
    private String genFile(BufferedImage image, String[] path, ProductGallery gallery,
                           String imageSuffix, String sourceImgName, int[] size, boolean keepAspectRatio) {

        return genFile(image, path, gallery, imageSuffix, sourceImgName, size, keepAspectRatio, DEFAULT_OUTPUT_QUALITY);
    }

    private String genFile(BufferedImage image, String[] path, ProductGallery gallery,
                           String imageSuffix, String sourceImgName, int[] size, boolean keepAspectRatio, float quality) {
        String outImageName = buildResizeImageName(sourceImgName, size, imageSuffix);
        String imgFullName = path[1] + outImageName;
        if (StringUtil.isBlank(imgFullName)) {
            logger.warn("generate image name failed: [goodsId={0}, colorId={1}]",
                gallery.getGoodsId(), gallery.getColorId());
            return "";
        }
        boolean result = ImageUtil.resize(image, size[0], size[1], imgFullName, keepAspectRatio, quality);
        if (!result) {
            logger.warn("generate image failed: [goodsId={0}, colorId={1}, size={2}]",
                gallery.getGoodsId(), gallery.getColorId(), size);
            return "";
        }
        logger.debug("generate image: [goodsId={0}, colorId={1}, name={2}]", gallery.getGoodsId(),
            gallery.getColorId(), path[0] + outImageName);
        return path[0] + outImageName;
    }

    private String buildResizeImageName(String sourceImgName, int[] size, String imageSuffix) {
    	return sourceImgName + "." + size[0] + "x" + size[1] + imageSuffix;
    }
    
    private String[] getOutFilePath(Integer goodsId) {
        StringBuffer outPath = new StringBuffer(200);
        String idpath = RandomUtils.toFixdLengthString(goodsId, 9);
        outPath.append(System.getProperty(SystemConstant.BATCH_IMAGE_OUT_TPD)).append(File.separator)
            .append(idpath.substring(0, 3)).append(File.separator).append(idpath.substring(3, 6))
            .append(File.separator);
        String path = outPath.toString();
        String outFilePath = ConstantsHelper.getPicRootPath() + File.separator + path;
        File outFile = new File(outFilePath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        return new String[] { path, outFilePath };
    }

    private String getOutFileName(Integer goodsId, Integer colorId, String type, String suffix, int index) {
        StringBuffer outName = new StringBuffer();
        outName.append(StringUtils.substring(String.valueOf(goodsId), -3))
        	   .append("-")
               .append(colorId)
               .append("_");
        
        	if (type != null && !type.isEmpty()) {
        		outName.append(type).append("_");
        	}
        	
        	outName.append(index)
            //.append(RandomUtils.generateNumString(5))
            .append("_")
            .append(DateUtil.getCurrentTime().getTime())
            .append(suffix);
        return outName.toString();
    }

    private class ProcessThread implements Callable<String> {

        private final String        batchNo;
        private final String        path;
        private final String        dir;
        private final Integer       adminId;
        private final ProcessStatus processStatus;
        private final BatchResultVo resultVo;
        private final List<String>  logs;

        public ProcessThread(String batchNo, String path, String imageDir, Integer adminId,
                             ProcessStatus processStatus) {
            this.batchNo = batchNo;
            this.path = path;
            this.dir = imageDir;
            this.adminId = adminId;
            this.processStatus = processStatus;
            this.resultVo = new BatchResultVo();
            logs = new ArrayList<>();
        }

        @Override
        public String call() {
            logger.info(PREFIX + "process dir: {0} begin ", dir);
            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "开始处理目录：" + dir);

            long begin = System.currentTimeMillis();

            File imageDir = new File(path + dir);
            if (!imageDir.exists()) {
                logger.warn(PREFIX + "dir {0} is not exist.", path + dir);
                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_WARN + PREFIX + "目录：" + dir + "不存在");
                resultVo.setMessage("目录：" + dir + "不存在");
                processStatus.getLogs().addAll(logs);
                processStatus.getResults().add(resultVo);
                return FAILURE_FLAG;
            }
            if (!imageDir.isDirectory()) {
                logger.warn(PREFIX + "dir {0} is not a directory.", path + dir);
                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_WARN + PREFIX + dir + "不是目录");

                resultVo.setMessage(dir + "不是目录");
                processStatus.getLogs().addAll(logs);
                processStatus.getResults().add(resultVo);
                return FAILURE_FLAG;
            }

            try {
                processDir(batchNo, imageDir, adminId, resultVo, logs);
            } catch (BizException be) {
                resultVo.setMessage(be.getMessage());
                logger.error(PREFIX + "process dir " + dir + " error: ", be);
                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_ERROR + PREFIX + "处理目录：" + dir
                         + "失败，原因：" + be.getMessage());

                processStatus.getLogs().addAll(logs);
                processStatus.getResults().add(resultVo);
                return FAILURE_FLAG;
            } catch (Exception e) {
                resultVo.setMessage("未知异常");
                logger.error(PREFIX + "process dir " + dir + " error: ", e);
                logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_ERROR + PREFIX + "处理目录：" + dir
                         + "失败，原因：" + e.getMessage());

                processStatus.getLogs().addAll(logs);
                processStatus.getResults().add(resultVo);
                return FAILURE_FLAG;
            }
            resultVo.setMessage("导入成功");

            imageDir = null;

            logger.info(PREFIX + "process dir {0} success, use {1} ms", dir,
                System.currentTimeMillis() - begin);
            logs.add(LOG_DATE_FORMAT.format(new Date()) + MSG_INFO + PREFIX + "处理目录：" + dir
                     + "成功，用时" + ((System.currentTimeMillis() - begin) / 1000) + "秒");

            processStatus.getLogs().addAll(logs);
            processStatus.getResults().add(resultVo);
            return dir;
        }
    }

    @Override
    public ProcessStatus getStatus(String batchNo) {

        logger.debug("get status from server, batchNo={0}", batchNo);
        ProcessStatus status = INFOS.get(batchNo);
        if (status != null) {
            status.getLogback().clear();
            status.getLogback().addAll(status.getLogs());
            status.getLogs().clear();
        }
        return status;
    }

    @Override
    public void removeStatus(String batchNo) {
        INFOS.remove(batchNo);
    }

    private ProductGallery newDefaultProductGallery() {
    	ProductGallery gallery = new ProductGallery();
    	final String empty = "";
    	gallery.setThumbUrl(empty);
    	gallery.setMiddleUrl(empty);
    	gallery.setBigUrl(empty);
    	gallery.setTeenyUrl(empty);
    	gallery.setSmallUrl(empty);
    	gallery.setUrl120160(empty);
    	gallery.setUrl99132(empty);
    	gallery.setUrl480640(empty);
    	gallery.setUrl5684(empty);
    	gallery.setUrl222296(empty);
    	gallery.setUrl342455(empty);
    	gallery.setUrl170227(empty);
    	gallery.setUrl135180(empty);
    	gallery.setUrl251323(empty);
      	gallery.setUrl502646(empty);
      	gallery.setUrl12001600(empty);
    	
		return gallery;
	}
    
    @Override
    public void processByHand(String batchNo, String channel, Integer adminId) {
        // do nothing...
    }
}
