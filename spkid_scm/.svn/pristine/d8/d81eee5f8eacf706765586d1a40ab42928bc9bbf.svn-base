/**
 * f-club.cn
 * Copyright (c) 2009-2012 All Rights Reserved.
 */
package com.fclub.tpd.controller;

import static com.fclub.tpd.helper.SessionHelper.setCaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fclub.common.lang.SystemException;
import com.fclub.common.log.LogUtil;

/**
 * Captcha generator controller.
 * 
 * @author michael
 * @version $Id: CaptchaController.java 26 2013-07-04 04:21:24Z zhangshixi $
 */
@Controller
public class CaptchaController {

    public static final Map<char[], byte[]> CAPTCHA_LOCAL_MAP = new ConcurrentHashMap<>();

    /** code length */
    public static final int                 CAPTCHA_LENGTH    = 4;
    /** image width */
    public static final int                 CAPTCHA_WIDTH     = 106;
    /** image height */
    public static final int                 CAPTCHA_HEIGHT    = 22;
    /** top space */
    public static final int                 TOP_SPACE         = 1;
    /** bottom space */
    public static final int                 BOTTOM_SPACE      = 1;

    /** line number */
    public static final int                 LINE_NUMBER       = 30;
    /** image format type */
    public static final String              IMAGE_TYPE        = "jpg";

    /** font name */
    public static final String              FONT_NAME         = "Arial";
    /** font style */
    public static final int                 FONT_STYLE        = Font.BOLD;

    /** captcha codes */
    public static final char[]              CAPTCHA_CODES     = new char[] { '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /** logger */
    private static final LogUtil            LOGGER            = LogUtil.getLogger(CaptchaController.class);
    
    // 预先生成
    static {
        new Thread(new BuildCaptchaThread()).start();
    }

    @RequestMapping("/login/captcha.htm")
    public String generateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (CAPTCHA_LOCAL_MAP.isEmpty()) {
            getCaptcha();
        }
        if (CAPTCHA_LOCAL_MAP.size() < 10) {
           new Thread(new BuildCaptchaThread()).start();
        }
        appendCaptcha(request, response);
        return null;
    }

    private void appendCaptcha(HttpServletRequest request, HttpServletResponse response) {
        char[] codes = CAPTCHA_LOCAL_MAP.keySet().iterator().next();
        if (codes != null && codes.length == CAPTCHA_LENGTH) {
            byte[] bytes = CAPTCHA_LOCAL_MAP.remove(codes);
            setCaptcha(String.valueOf(codes), request);
            writeToPage(bytes, response);
        }
    }

    private static void getCaptcha() {
        char[] codes = buildCaptchaCodes(CAPTCHA_LENGTH);
        byte[] bytes;
        try {
            bytes = buildCaptchaImage(codes);
        } catch (IOException e) {
            String errMsg = "generate captcha error";
            LOGGER.error(errMsg, e);
            throw new SystemException(errMsg, e);
        }
        CAPTCHA_LOCAL_MAP.put(codes, bytes);
    }

    private static char[] buildCaptchaCodes(int length) {
        char[] result = new char[length];
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CAPTCHA_CODES.length);
            result[i] = CAPTCHA_CODES[index];
        }

        return result;
    }

    private static byte[] buildCaptchaImage(char[] codes) throws IOException {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT,
            BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = image.createGraphics();
        // background color
        graphics.setColor(getRandColor(200, 400));
        graphics.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

        // draws the outline of the specified rectangle.
        graphics.setColor(Color.BLUE);
        // graphics.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);

        // generate lines
        Random random = new Random();
        for (int i = 0; i < LINE_NUMBER; i++) {
            int xs = random.nextInt(CAPTCHA_WIDTH);
            int ys = random.nextInt(CAPTCHA_HEIGHT);
            int xe = random.nextInt(LINE_NUMBER >> 2);
            int ye = random.nextInt(LINE_NUMBER >> 2);

            graphics.drawLine(xs, ys, xs + xe, ys + ye);
        }

        // create font
        int fontSize = CAPTCHA_HEIGHT - TOP_SPACE - BOTTOM_SPACE;
        Font font = new Font(FONT_NAME, FONT_STYLE, fontSize);
        graphics.setFont(font);

        int fontSpace = 0;
        int x = fontSpace + 16;
        int y = fontSize;
        for (char code : codes) {
            graphics.drawString(String.valueOf(code), x, y);
            x = x + fontSize + fontSpace;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", out);

        return out.toByteArray();
    }

    /**
     * @param bytes
     * @param response
     */
    private void writeToPage(byte[] bytes, HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream output = null;
        try {
            output = response.getOutputStream();
            output.write(bytes);
        } catch (IOException e) {
            String errMsg = "write captcha to page error";
            LOGGER.error(errMsg, e);
            throw new SystemException(errMsg, e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                String errMsg = "close servlet output stream error";
                LOGGER.error(errMsg, e);
                throw new SystemException(errMsg, e);
            }
        }
    }

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    public static class BuildCaptchaThread implements Runnable {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 50; i++) {
                    getCaptcha();
                }
            } catch (Exception e) {
                LogUtil.getLogger(getClass()).error("build Captcha Error", e);
            }
        }

    }

}
