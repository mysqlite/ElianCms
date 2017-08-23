package com.elian.cms.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * 图片辅助类
 * 
 * @author liufang
 * 
 */
public abstract class ImageUtils {
	/**
	 * 图片的后缀
	 */
	public static final String[] IMAGE_EXT = new String[] { "jpg", "jpeg","gif", "png", "bmp" };

	/**
	 * 是否是图片
	 * 
	 * @param ext
	 * @return "jpg", "jpeg", "gif", "png", "bmp" 为文件后缀名者为图片
	 */
	public static boolean isValidImageExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : IMAGE_EXT) {
			if (s.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the underlying input stream contains an image.
	 * 
	 * @param in
	 *            input stream of an image
	 * @return <code>true</code> if the underlying input stream contains an
	 *         image, else <code>false</code>
	 */
	public static boolean isImage(final InputStream in) {
		ImageInfo ii = new ImageInfo();
		ii.setInput(in);
		return ii.check();
	}

	/**
	 * 获得水印位置
	 * 
	 * @param width
	 *            原图宽度
	 * @param height
	 *            原图高度
	 * @param p
	 *            水印位置 1-5，其他值为随机。1：左上；2：右上；3：左下；4：右下；5：中央。
	 * @param offsetx
	 *            水平偏移。
	 * @param offsety
	 *            垂直偏移。
	 * @return 水印位置
	 */
	public static Position markPosition(int width, int height, int p,int offsetx, int offsety) {
		if (p < 1 || p > 5) {
			p = (int) (Math.random() * 5) + 1;
		}
		int x, y;
		switch (p) {
		// 左上
		case 1:
			x = offsetx;
			y = offsety;
			break;
		// 右上
		case 2:
			x = width + offsetx;
			y = offsety;
			break;
		// 左下
		case 3:
			x = offsetx;
			y = height + offsety;
			break;
		// 右下
		case 4:
			x = width + offsetx;
			y = height + offsety;
			break;
		// 中央
		case 5:
			x = (width / 2) + offsetx;
			y = (height / 2) + offsety;
			break;
		default:
			throw new RuntimeException("never reach ...");
		}
		return new Position(x, y);
	}

	/**
	 * 水印位置
	 * 
	 * 包含左边偏移量，右边偏移量。
	 * 
	 * @author liufang
	 * 
	 */
	public static class Position {
		private int x;
		private int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	/***
	 * 获取图片的宽
	 * @param file
	 * @return
	 */
	public static int getImgWidth(File file) {
		BufferedImage src=null;
		 try {
			 src = javax.imageio.ImageIO.read(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		if(src!=null) {
			return src.getWidth();
		}else {
			return 0;
		}
	}
	
	/***
	 * 获取图片的高
	 * @param file
	 * @return
	 */
	public static int getImgHeight(File file) {
		BufferedImage src=null;
		 try {
			 src = javax.imageio.ImageIO.read(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		if(src!=null) {
			return src.getHeight();
		}else {
			return 0;
		}
	}
	
	/**
	 * 判断图片的宽高是否达到标准
	 * @param maxW 最大宽度
	 * @param maxH 最大高度
	 * @param minW 最小宽度
	 * @param minH 最小高度
	 * @param file    图片文件
	 * @return
	 */
	public static boolean imageWidthOrHeight(int maxW,int maxH,int minW,int minH,File file) {
		int width=getImgWidth(file);
		int height=getImgHeight(file);
		if(height>maxH||height<minH||width>maxW||width<minW) {
			return false;
		}else {
			return true;
		}
	}
}
