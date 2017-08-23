package com.elian.cms.common.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;

public class FileType {
	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
	public final static Set<String> IMGS=new HashSet<String>();
	public final static Set<String> WORD=new HashSet<String>();
	public final static Set<String> PDF=new HashSet<String>();
	public final static Set<String> SWF=new HashSet<String>();
	public final static Set<String> VIDEO=new HashSet<String>();
	public final static Set<String> FILES=new HashSet<String>();
	private FileType() {
		
	}
	static {
		getAllFileType(); // 初始化文件类型信息
		IMGS.add("jpg");IMGS.add("png");IMGS.add("gif");IMGS.add("bmp");
		WORD.add("doc");WORD.add("xls");WORD.add("ppt");WORD.add("docx");WORD.add("xlsx");WORD.add("pptx");
		SWF.add("swf");SWF.add("_swf");
		PDF.add("pdf");
		VIDEO.add("acc");VIDEO.add("avi");VIDEO.add("mid");VIDEO.add("mp3");VIDEO.add("_mp3");VIDEO.add("mp4");//经测试目前网页支持播放
		FILES.addAll(WORD);FILES.addAll(PDF);FILES.addAll(SWF);
	}

	/**
	 * Created on 2010-7-1
	 * <p>
	 * Discription:[getAllFileType,常见文件头信息]
	 * </p>
	 * 
	 * @author:[shixing_11@sina.com]
	 */
	private static void getAllFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
		FILE_TYPE_MAP.put("png", "89504E47"); // PNG (png)
		FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
		
		FILE_TYPE_MAP.put("xls", "D0CF11E0"); // MS Wordword 和 excel的文件头一样
		FILE_TYPE_MAP.put("doc", "D0CF11E0");
		FILE_TYPE_MAP.put("ppt", "D0CF11E0"); 
		FILE_TYPE_MAP.put("pdf", "255044462D312E"); //Adobe Acrobat (pdf)
		
		FILE_TYPE_MAP.put("docx", "504B0304140006000800"); // MS Excel 注意：word 和 excel的文件头一样 offic2010
		FILE_TYPE_MAP.put("xlsx", "504B0304140006000800"); 
		FILE_TYPE_MAP.put("pptx", "504B0304140006000800"); 
		
		FILE_TYPE_MAP.put("swf", "4357530");
		FILE_TYPE_MAP.put("_swf", "4657530");

		FILE_TYPE_MAP.put("acc", "FFF15C400EA0");
		FILE_TYPE_MAP.put("avi", "4657530");
		FILE_TYPE_MAP.put("mid", "4D546864");
		FILE_TYPE_MAP.put("mp3", "FFFB9064");
		FILE_TYPE_MAP.put("_mp3", "49443303");
		FILE_TYPE_MAP.put("mp4", "000000186674797");
		
		//FILE_TYPE_MAP.put("zip", "504B0304");
		//FILE_TYPE_MAP.put("rar", "52617221");
	}
	/**
	 * 获取文件类型，若不是文件类型，返回null
	 * 
	 * @param f
	 * @return
	 */
	public final static String getImageFileType(File f) {
		if (isImage(f)) {
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(f);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return null;
				}
				ImageReader reader = iter.next();
				iis.close();
				return reader.getFormatName();
			}
			catch (IOException e) {
				return null;
			}
			catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Created on 2010-7-1
	 * <p>
	 * Discription:[getFileByFile,获取文件类型,包括图片,若格式不是已配置的,则返回null]
	 * </p>
	 * 
	 * @param file
	 * @return fileType
	 * @author:[shixing_11@sina.com]
	 */
	public final static String getFileByFile(InputStream is) {
		String filetype = null;
		byte[] b = new byte[50];
		try {
			is.read(b);
			filetype = getFileTypeByStream(b);
			is.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return filetype;
	}
	
	/**
	 * 将输入流转换为文件
	 * 
	 * @param ins
	 * @param file
	 */
	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Created on 2010-7-1
	 * <p>
	 * Discription:[getFileTypeByStream]
	 * </p>
	 * 
	 * @param b
	 * @return fileType
	 * @author:[shixing_11@sina.com]
	 */
	public final static String getFileTypeByStream(byte[] b) {
		String filetypeHex = String.valueOf(getFileHexString(b));
		System.out.println(filetypeHex);
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		/*getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.aac")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.ape")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.ape")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/3.ape")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.flac")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.flac")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/3.flac")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.mid")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.mid")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/3.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.wma")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.wma")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/3.wma")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.mp4")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.mp4")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.wmv")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.WMV")));*/
		/*getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/1.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/2.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/3.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/4.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/5.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/6.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/7.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/8.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/9.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/10.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/11.mp3")));
		getFileByFile(FileUtils.openInputStream(new File("C:/Users/Administrator/Desktop/音视频测试文件/12.mp3")));*/
		getFileByFile(FileUtils.openInputStream(new File("H:/电驴下载/学习内容/学习视频/揭露转基因内幕真相，中国人必须看.mp4")));
	}
	
	/**
	 * Created on 2010-7-2
	 * <p>
	 * Discription:[isImage,判断文件是否为图片]
	 * </p>
	 * 
	 * @param file
	 * @return true 是 | false 否
	 * @author:[shixing_11@sina.com]
	 */
	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			}
			else {
				flag = true;
			}
		}
		catch (IOException e) {
			flag = false;
		}
		catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * Created on 2010-7-1
	 * <p>
	 * Discription:[getFileHexString]
	 * </p>
	 * 
	 * @param b
	 * @return fileTypeHex
	 * @author:[shixing_11@sina.com]
	 */
	public final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}