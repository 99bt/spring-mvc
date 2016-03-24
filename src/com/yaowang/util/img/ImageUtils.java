/* 
 * @(#)ImageUtils.java    Created on 2006-4-13
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/ImageUtils.java,v 1.12 2008/02/13 08:59:42 yangm Exp $
 */
package com.yaowang.util.img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.yaowang.util.filesystem.util.FileSystemUtil;
import com.yaowang.util.filesystem.util.FileUtil;

/**
 * 图片工具类
 * 
 * @author liangxiao
 * @version $Revision: 1.12 $, $Date: 2008/02/13 08:59:42 $
 */
public class ImageUtils {
    /**
     * 修改图片大小
     * 
     * @param src
     *            源图片的路径
     * @param dest
     *            目标图片的路径
     * @param width
     *            宽
     * @param height
     *            高
     * @throws IOException
     */
    public static void changeSize(InputStream is, OutputStream os, int width, int height) throws IOException {
    	BufferedImage bis = ImageIO.read(is); // 构造Image对象
        is.close();
        
        int srcWidth = bis.getWidth(null); // 得到源图宽
        int srcHeight = bis.getHeight(null); // 得到源图高

        if (width <= 0 || width > srcWidth) {
			width = bis.getWidth();
		}
    	if (height <= 0 || height > srcHeight) {
			height = bis.getHeight();
		}

        // 若宽高小于指定最大值，不需重新绘制
        if (srcWidth <= width && srcHeight <= height) {
            return;
        } else {
            double scale = ((double) width / srcWidth) > ((double) height / srcHeight) ? ((double) height / srcHeight)
                    : ((double) width / srcWidth);
            width = (int) (srcWidth * scale);
            height = (int) (srcHeight * scale);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(bis, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
            ImageIO.write(bufferedImage, "jpeg", os);
            os.close();
        }
    }
    
    /**
     * 等比例修改图片大小，目标图片的大小不会超过指定的宽、高，以小的为准
     * 
     * @param src
     *            源图片的路径
     * @param dest
     *            目标图片的路径
     * @param width
     *            宽
     * @param height
     *            高
     * @throws Exception 
     */
    public static void changeOppositeSize(String src, String dest, int width, int height) throws Exception {
		BufferedImage bis = ImageIO.read(new File(src)); // 构造Image对象
        int srcWidth = bis.getWidth(null); // 得到源图宽
        int srcHeight = bis.getHeight(null); // 得到源图高

        if (width <= 0 || width > srcWidth) {
			width = bis.getWidth();
		}
    	if (height <= 0 || height > srcHeight) {
			height = bis.getHeight();
		}

        // 若宽高小于指定最大值，不需重新绘制
        if (srcWidth <= width && srcHeight <= height) {
        	FileUtils.copyFile(new File(src), new File(dest));
            return;
        } else {
            double scale = ((double) width / srcWidth) > ((double) height / srcHeight) ? ((double) height / srcHeight)
                    : ((double) width / srcWidth);
            width = (int) (srcWidth * scale);
            height = (int) (srcHeight * scale);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(bis, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
            FileOutputStream out = new FileOutputStream(dest); // 输出到文件流
            ImageIO.write(bufferedImage, "jpeg", out);
            out.close();
        }

    }

    /**
     * 等比例修改原图片大小，大小不会超过指定的宽、高，以小的为准
     * 
     * @param src
     * @param width
     * @param height
     * @throws IOException
     */
	public static void changeSize(String src, int width, int height) throws IOException {
		BufferedImage bis = ImageIO.read(new File(src)); // 构造Image对象
        int srcWidth = bis.getWidth(null); // 得到源图宽
        int srcHeight = bis.getHeight(null); // 得到源图高

        if (width <= 0 || width > srcWidth) {
			width = bis.getWidth();
		}
    	if (height <= 0 || height > srcHeight) {
			height = bis.getHeight();
		}

        // 若宽高小于指定最大值，不需重新绘制
        if (srcWidth <= width && srcHeight <= height) {
            return;
        } else {
            double scale = ((double) width / srcWidth) > ((double) height / srcHeight) ? ((double) height / srcHeight)
                    : ((double) width / srcWidth);
            width = (int) (srcWidth * scale);
            height = (int) (srcHeight * scale);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(bis, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
            FileOutputStream out = new FileOutputStream(src); // 输出到文件流
            ImageIO.write(bufferedImage, "jpeg", out);
            out.close();
        }

    }
}
