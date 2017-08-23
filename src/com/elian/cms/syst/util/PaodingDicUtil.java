package com.elian.cms.syst.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.paoding.analysis.knife.PaodingMaker;

import org.apache.commons.io.FileUtils;
import org.springframework.util.CollectionUtils;

public class PaodingDicUtil {
	private static final Properties properties=PaodingMaker.getProperties(PaodingMaker.DEFAULT_PROPERTIES_PATH);
	private static final String MYTEMPDICFILENAME="myWord.txt";	
	private static final String MYDICFILENAME="myWord.dic";
	private static final int MAXFILELENGTH=100;
	private static final long WORDVALIDTIME=24*60*60*1000*5;//搜索关键字有效期      默认5天
	private static final long RECOMPLILEDICtIME=24*60*60*1000*10;//两次编译字典的时间间隔
	private static boolean reCompileDic=false;
	
	public static void addNewWord(String word){
		try{
			if(StringUtils.isBlank(word))	return;
			String[] words=word.split("( )+");		
			File myDicFile=createMyDicFile();
			writeToFile(myDicFile,words);
			List<String> keys=validataFileLength(myDicFile);
			addToDic(keys);		//增加关键字到词典
			reCompileDic();				
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private static void reCompileDic() {
		if(reCompileDic){
			String parentDicPath=properties.getProperty("paoding.dic.home.absolute.path");
			File dicFile=new File(parentDicPath,MYDICFILENAME);
			if(dicFile.exists()){
				if(dicFile.length()==0) return;
				File f=new File(parentDicPath+"\\.compiled\\most-words-mode","vocabulary.dic.compiled");
				if(f.isFile()){
					f.delete();
				}
			}
		}
	}

	private static void addToDic(List<String> keys) throws IOException {
		if(CollectionUtils.isEmpty(keys)) return;
			String parentDicPath=properties.getProperty("paoding.dic.home.absolute.path");
			File dicFile=new File(parentDicPath,MYDICFILENAME);
			if(!dicFile.exists()) dicFile.createNewFile();
			String file=FileUtils.readFileToString(dicFile);
			Iterator<String> itor=keys.iterator();
			while (itor.hasNext()) {
				String line = itor.next().trim();
				if(file.contains(line+"\r\n"))
					itor.remove();				
			}
			if(!CollectionUtils.isEmpty(keys))
				FileUtils.writeLines(dicFile,keys,true);
	}
	
	private static void writeToFile(File myDicFile, String[] words) throws IOException {
			String file= FileUtils.readFileToString(myDicFile);
			for(int i=0;i<words.length;i++){
				if(words[i].length()>=2&&words[i].length()<=5){	//长度大于2小于5的
					if(file.contains(words[i])){
						file=findAllFile(file, words[i],false);
					}else{
						file=file+words[i]+" "+1+" "+new Date().getTime()+"\r\n";
					}
				}
			}
			FileUtils.writeStringToFile(myDicFile, file);
	}

	private static String findAllFile(String file,String word,boolean hasEquealWord){
		if(StringUtils.isBlank(file))
			if(!hasEquealWord)
				return word+" "+1+" "+new Date().getTime()+"\r\n";
			else return "";
		if(file.contains(word)){
			String preString=file.substring(0, file.indexOf(word));
			preString=preString.substring(0, preString.lastIndexOf("\r\n")==-1?0:preString.lastIndexOf("\r\n")+2);
			String backString=file.substring(file.indexOf(word),file.length());
			backString=backString.substring(backString.indexOf("\r\n")==-1?0:backString.indexOf("\r\n")+2);
			
			String line= file.substring(preString.length(),file.length()-backString.length());
			String[] items=line.split("( )+");
			if(items[0].equals(word)){
				hasEquealWord=true;
				line=items[0]+" "+(Integer.parseInt(items[1])+1)+" "+new Date().getTime()+" \r\n";
			}
			return preString+line+findAllFile(backString, word ,hasEquealWord);
		}else
			return file;
	}
	
	private static List<String> validataFileLength(File myDicFile) throws IOException {
		List<String> Keys=new ArrayList<String>();
		List<String> list = null;
		list = FileUtils.readLines(myDicFile);
		Iterator<String> itor=list.iterator();
		String[] item=null;
		int i=-1;
		while (itor.hasNext() && i<MAXFILELENGTH) {
			i++;
			String str = (String) itor.next();
			if(i>0){
				item=str.split("( )+");
				Date lastDate=new Date(Long.parseLong(item[2])+WORDVALIDTIME);
				//搜索次数大于2的
				if(Integer.parseInt(item[1])>2){
					Keys.add(item[0]);
					itor.remove();
				}else if(new Date().after(lastDate)){
					itor.remove();
				}
			}else{
				Date lastCompileDicTime=new Date(Long.parseLong(str)+RECOMPLILEDICtIME);
				if(new Date().after(lastCompileDicTime)){
					reCompileDic=true;
					list.set(0, new Date().getTime()+"");
				}
			}
		}
		FileUtils.writeLines(myDicFile, list);
		return Keys;
	}


	private static File createMyDicFile() throws IOException {
		String parentDicPath=properties.getProperty("paoding.dic.home.absolute.path");
		File myDicFile=new File(parentDicPath,MYTEMPDICFILENAME);
		if(!myDicFile.exists()){
			myDicFile.createNewFile();
			List<String> initLines=new ArrayList<String>();
			initLines.add(new Date().getTime()+"");
			FileUtils.writeLines(myDicFile, initLines);
		}
		return myDicFile;
	}

	public static void main(String[] args) {
//		PaodingDicUtil util=new PaodingDicUtil();
//		util.addNewWord("海洋");
	}
}
