package com.nju.yummy.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

//该类用于处理图片，将前端的base64码转为图片存储，或将图片转为base64码传回前端
public class ImageUtil {

    /**
     *
     * @param path 图片路径
     * @return 图片base64码
     */
    public static String imageToBase64(String path) {
        BASE64Encoder encoder = new BASE64Encoder();
        File file = new File(path);
        String imgStr = "";
        try {
            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            imgStr = encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgStr;
    }

    /**
     *
     * @param imgStr base64
     * @param path 图片存储路径
     * @return 将base64转为图片并保存，成功返回true，失败返回false
     */
    public static boolean base64ToImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
