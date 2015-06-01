package com.fclub.tpd.helper;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fclub.tpd.biz.BrandService;
import com.fclub.tpd.biz.ProviderService;
import com.fclub.tpd.common.SystemConstant;
import com.fclub.tpd.dataobject.Brand;
import com.fclub.tpd.dataobject.Provider;

public class ComponentHelper {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ProviderService providerService;
    
    public boolean isAdmin() {
    	Provider provider = SessionHelper.getProvider();
    	return provider != null && provider.isAdmin();
    }
    
    public String getBrandNameById(Integer brandId) {
        Brand brand = brandService.findBrand(brandId);
        return brand == null ? null : brand.getBrandName();
    }
    
    public String getProviderCode(Integer providerId) {
        Provider p = providerService.findById(providerId);
        return p == null ? "" : p.getProviderCode();
    }
    
    /**
     * 是否过期发运
     */
    public boolean overtime(Date startDate, Date endDate) {

        if (startDate == null) {
            return false;
        }

        Calendar cstart = Calendar.getInstance();
        cstart.setTime(startDate);
        Calendar cend = Calendar.getInstance();
        if (endDate != null) {
            cend.setTime(endDate);
        }

        int weekDay = cstart.get(Calendar.DAY_OF_WEEK);
        int hour = cstart.get(Calendar.HOUR_OF_DAY);

        cstart.set(Calendar.HOUR_OF_DAY, 23);
        cstart.set(Calendar.MINUTE, 59);
        cstart.set(Calendar.SECOND, 59);
        cstart.set(Calendar.MILLISECOND, 999);

        if ((weekDay > 1 && weekDay < 6 && hour >= 15) || weekDay == 1) { //周一到周五15点以后 / 周日
        	cstart.add(Calendar.DATE, 1);
        } else if (weekDay == 6 && hour >= 15) { // 周五15点以后
            cstart.add(Calendar.DATE, 3);
        } else if (weekDay == 7) { //周六
        	cstart.add(Calendar.DATE, 2);
        }
        //其他时间当日24点前发货
//        System.out.println(weekDay);
//        System.out.println(hour);
//        System.out.println(cstart.getTime());

        if (cstart.before(cend)) {
            return true;
        }

        return false;
    }
    
    public String getImageInPath() {
        return ConstantsHelper.getImageInPath();
    }
    
    public String getImageBcsInPath(){
        return ConstantsHelper.getPram(SystemConstant.GOODS_BCS_IMAGE_IN);
    }

    public static String getImageDomain() {
        return ConstantsHelper.getImageDomain();
    }
    
}
