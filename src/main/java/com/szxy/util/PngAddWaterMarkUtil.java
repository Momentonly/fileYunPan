package com.szxy.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: zwz
 * @Date:2020/11/3 14:47
 * @Description: 图片添加水印工具类
 **/
public class PngAddWaterMarkUtil {

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 证照图片下载地址具有时效性， 过期则不能下载图片
        String imgFilePath = "http://xxxx:8802/zzwxxk-servicexx/criterion/download?appId=a26aee2ca13c43cd9c25ceb93e6d058a&format=png&id=40288ac6668a546e01669a4404ee03cd&styleCode=11340000002986432D0057&time=1604393600852&sign=3d46ece138aba534a7e2f71d7f03ca69";
        String waterMarkContent = "滁州市信息共享 办电专用";
        getPhotoAndAddWaterMark(imgFilePath, waterMarkContent, "0");
        System.out.println("水印添加完成");
    }


    /**
     * 根据 donwUrl 下载图片，并添加水印
     *
     * @param imgFilePath
     * @param waterMarkContent
     * @param type             0 身份证 1 营业执照
     * @return
     */
    public static String getPhotoAndAddWaterMark(String imgFilePath, String waterMarkContent, String type) {
        Font font = null;
        // 水印在图片 x 轴的位置
        float waterMarkX = 0.0f;
        // 水印在图片 y 轴的位置
        float waterMarkY = 0.0f;
        // 水印颜色
        Color color = Color.LIGHT_GRAY;
        // 水印透明度 0-1 之间
        float alpha = 0.5f;
        // 身份证
        if ("0".equals(type)) {
            // 水印字体
            font = new Font("微软雅黑", Font.ITALIC, 45);
            waterMarkX = 20;
            waterMarkY = 300;

        }  // 营业执照
        else {
            // 水印字体
            font = new Font("微软雅黑", Font.ITALIC, 100);
            waterMarkX = 500;
            waterMarkY = 800;
        }
        InputStream inStream = getPngInputStream(imgFilePath);
        ByteArrayOutputStream outStream = getWaterMarkBi(color, font, waterMarkContent, inStream, alpha, waterMarkX, waterMarkY);
        String base64Png = getBase64Png(outStream);
//        Base64Util.base64StringToFile(base64Png, "E:/out.png");
//        System.out.println("data:image/png;base64," + base64Png);
        return base64Png;
    }

    /**
     * 获取图片 base64 编码
     *
     * @param outStream
     * @return
     */
    private static String getBase64Png(ByteArrayOutputStream outStream) {
        byte[] data = outStream.toByteArray();
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(data);
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 获取水印图片对象
     *
     * @param color
     * @param font
     * @param waterMarkContent
     * @param inStream
     * @param alpha
     * @param wateMarkX
     * @param waterMarkY
     * @return
     * @throws IOException
     */
    private static ByteArrayOutputStream getWaterMarkBi(Color color, Font font, String waterMarkContent
            , InputStream inStream, float alpha, float wateMarkX, float waterMarkY) {
        // 加载图片
        BufferedImage buImage = null;
        try {
            buImage = ImageIO.read(inStream);
            // 获取图片宽
            int width = buImage.getWidth();
            // 获取图片高
            int height = buImage.getHeight();
            //添加水印
            BufferedImage waterMarkBi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            // 画笔对象
            Graphics2D g = waterMarkBi.createGraphics();
            g.drawImage(buImage, 0, 0, width, height, null);
            //水印颜色
            g.setColor(color);
            //水印字体
            g.setFont(font);
            int x = width - getWatermarkLength(waterMarkContent, g);  //这是一个计算水印位置的函数，可以根据需求添加
            int y = height - 2 * getWatermarkLength(waterMarkContent, g);
            // 旋转角度
            g.rotate(Math.toRadians(325), (double) waterMarkBi.getWidth() / 2, (double) waterMarkBi.getHeight() / 2);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 水印位置
            g.drawString(waterMarkContent, wateMarkX, waterMarkY);
            g.dispose(); //释放资源
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream(2048);
            ImageIO.write(waterMarkBi, "png", outStream);
            outStream.flush();
            return outStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /**
     * 根据 downloadUrl  下载图片地址，获取 InputStream 流对象
     *
     * @param imgFilePath 下载图片地址
     * @return
     */
    private static InputStream getPngInputStream(String imgFilePath) {
        URL url = null;
        //打开链接
        HttpURLConnection conn = null;
        try {
            url = new URL(imgFilePath);
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream is = conn.getInputStream();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私有化构造器
     */
    private PngAddWaterMarkUtil() {
    }

}
