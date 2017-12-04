package com.xindong.api.common.qrcode;

import javax.imageio.ImageIO;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: IBOY
 * Date: 2015-12-29
 * Time: 20:38:23
 * To change this template use File | Settings | File Templates.
 */
public class MergeImageUtils {

//    private Font font = new Font("宋体", Font.PLAIN, 34);// 添加字体的属性设置
    private Font font = new Font("WenQuanYi Zen Hei", Font.BOLD, 28);// 添加字体的属性设置
//    private Font font = new Font("微软雅黑", Font.PLAIN, 30);// 添加字体的属性设置

    private Graphics2D g = null; 

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;



     /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

     /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, String content, int x,
            int y) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.WHITE);//设置字体颜色
            if (this.font != null) 
            	g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
//            if (x >= h || y >= w) {
//                this.x = h - this.fontsize + 2;
//                this.y = w;
//            } else {
//                this.x = x;
//                this.y = y;
//            }
//            this.x = x; 
            this.y = y;
            this.x = w/2-(w/20*(length(content)/4));//20个字符布满图片
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }
    /**
	 * 计算字符长度 中文算2个字符
	 * @param value
	 * @return
	 */
	public static int length(String value) {
	        int valueLength = 0;
	        String chinese = "[\u0391-\uFFE5]";
	        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
	        for (int i = 0; i < value.length(); i++) {
	            /* 获取一个字符 */
	            String temp = value.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	                /* 中文字符长度为2 */
	                valueLength += 2;
	            } else {
	                /* 其他字符长度为1 */
	                valueLength += 1;
	            }
	        }
	        return valueLength;
	 }
    /**
     * 修改图片,返回修改后的图片缓冲区（输出多个文本段） xory：true表示将内容在一行中输出；false表示将内容多行输出
     */
    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr,
            int x, int y, boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.RED);
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (contentArr != null) {
                int arrlen = contentArr.length;
                if (xory) {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length()
                                * this.fontsize / 2 + 5;// 重新计算文本输出位置
                    }
                } else {
                    for (int i = 0; i < arrlen; i++) {
                        g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;// 重新计算文本输出位置
                    }
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     *
     * 时间:2007-10-8
     *
     * @param img
     * @return
     */
    public BufferedImage modifyImageYe(BufferedImage img) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(Color.blue);//设置字体颜色
            if (this.font != null)
                g.setFont(this.font);
            g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d, int x, int y) {

        try {
            int w = b.getWidth();
            int h = b.getHeight();
            g = d.createGraphics();
            //b是图像对象，10是x轴开始位置，560是y轴开始位置,w是宽大小，h是高大小，observer - 转换了更多图像时要通知的对象。
            g.drawImage(b, x,y, w, h, null);
            g.dispose(); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return d;
    }

    public static void main(String[] args) {

    	 MergeImageUtils tt = new MergeImageUtils();
         BufferedImage sbw = tt.loadImageLocal("D:\\test.i.51wot.com\\img\\1111.jpg");//背景图
         BufferedImage d = tt.loadImageLocal("D:\\test.i.51wot.com\\img\\codeLogo2.png");//二维码
//       BufferedImage b = tt
//               .loadImageLocal("D:\\文件(word,excel,pdf,ppt.txt)\\zte-logo.png");
          String ss = "大家，新年快乐！永远幸福美满！很漂亮！";
           ss = "份数：20份";
          tt.writeImageLocal("D:\\test.i.51wot.com\\img\\bg-new.jpg",tt.modifyImage(sbw,ss,80,80) );//640*930 背景图片尺寸
         //往图片上写文件
         BufferedImage bgNew = tt.loadImageLocal("D:\\test.i.51wot.com\\img\\bg-new.jpg");
         tt.writeImageLocal("D:\\test.i.51wot.com\\img\\1231313.jpg",tt.modifyImagetogeter(d, bgNew,30,580));
         //将多张图片合在一起
         System.out.println("success");
    }



}


