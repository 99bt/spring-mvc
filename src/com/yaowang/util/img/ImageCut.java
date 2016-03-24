package com.yaowang.util.img;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageCut {
	/**
	 * jpg图片格式
	 */
	private static final String IMAGE_FORM_OF_JPG = "jpg";
	/**
	 * png图片格式
	 */
	private static final String IMAGE_FORM_OF_PNG = "png";
	
	static{
		//不使用缓存
		ImageIO.setUseCache(false);
	}
	
	private static Iterator<ImageReader> getImageReadersByFormatName(String postFix) {
		if (IMAGE_FORM_OF_PNG.equals(postFix)) {
			return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_PNG);
		}else {
			return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
		}
	}
	/**
	 * 截取
	 * @param in
	 * @param out
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param postFix
	 * @throws IOException
	 */
	public static void cutImg(InputStream in, OutputStream out, int x, int y, int width, int height, String postFix, Integer w) throws IOException{
		try {
			Iterator<ImageReader> it = getImageReadersByFormatName(postFix);
			ImageReader reader = it.next();
			ImageInputStream iis = ImageIO.createImageInputStream(in);
	    	reader.setInput(iis, true);
	    	ImageReadParam param = reader.getDefaultReadParam();
	    	
	    	Rectangle rect = new Rectangle(x, y, width, height);
	    	param.setSourceRegion(rect);
	    	
	    	BufferedImage bi = reader.read(0, param);
	    	ImageIO.write(bi, postFix, out);
	    	
	    	iis.close();
		} finally{
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	/**
	 * 创建圆形的图片
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public static void createRoundImg(InputStream is, OutputStream os) throws IOException{
		try {
			BufferedImage bis = ImageIO.read(is); 
			// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
			int size = 0;
			if (bis.getWidth() > bis.getHeight()) {
				//宽大于高
				size = bis.getHeight();
			}else {
				//宽小于高
				size = bis.getWidth();
			}
			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, size, size);

			BufferedImage biw = new BufferedImage(size, size, Transparency.TRANSLUCENT);
			Graphics2D g2d = biw.createGraphics();
			
			g2d.setClip(shape);
			// 使用 setRenderingHint 设置抗锯齿 
			g2d.drawImage(bis, 0, 0, size, size, Color.WHITE, null);
			
			g2d.dispose(); 
			ImageIO.write(biw, "png", os); 
		} finally{
			os.close();
			is.close();
		}
	}
}
