package com.elian.cms.syst.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.elian.cms.syst.model.SelectItem;
@SuppressWarnings("unchecked")
public class ReadWriteXMLUtils {
	public static String saveXmlFilePath;
	private static final String PATH= Thread.currentThread().getContextClassLoader().getResource("").getPath();
	public static List<SelectItem> readXML(String xmlName,String nodeName){
		List<SelectItem> sList=new ArrayList<SelectItem>();
		SelectItem s=null;
		Document doc=null;
		try {
			doc = (Document) getXmlDocument(PATH+xmlName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Element ele = doc.getRootElement();
		List eles = ele.elements("select");
		for (Iterator childs = eles.iterator(); childs.hasNext();) {
				Element el = (Element) childs.next();
				if(el.attributeValue("name").equals(nodeName)) {
			    s=new SelectItem();
				s.setKey(el.attributeValue("key"));
				s.setValue(el.attributeValue("value"));
				sList.add(s);
				}
		}
		return sList;
	}
	
	/***
	 * 例 1<select name="education" key="Unlimited" value="不限"></select>
	 * 例 2<select idName="education" keyName="Unlimited" valName="不限"></select>
	 * @param xmlName 配置文件名称（xml文件）
	 * @param elementName 如例1 select  节点名称
	 * @param idName 唯一区别的属性  如例1：name为idName，如2:idName
	 * @param valName 如例1:value,例2valName
	 * @param nodeName 为idName的值
	 * @param keyName 如例1：key,例2：keyName
	 * @return
	 */
	public static List<SelectItem> readXML(String xmlName,String elementName,String idName,String keyName,String valName,String nodeName){
		List<SelectItem> sList=new ArrayList<SelectItem>();
		SelectItem s=null;
		Document doc=null;
		try {
			doc = (Document) getXmlDocument(PATH+xmlName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Element ele = doc.getRootElement();
		List eles = ele.elements(elementName);
		for (Iterator childs = eles.iterator(); childs.hasNext();) {
				Element el = (Element) childs.next();
				if(el.attributeValue(idName).equals(nodeName)) {
			    s=new SelectItem();
				s.setKey(el.attributeValue(keyName));
				s.setValue(el.attributeValue(valName));
				sList.add(s);
				}
		}
		return sList;
	}
	
	
	public static SelectItem readXML(String xmlName,String elementName,String idName,String keyName,String valName,
			String nodeName,String key) {
		Document doc=null;
		try {
			doc = (Document) getXmlDocument(PATH+xmlName);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		SelectItem s=new SelectItem();
		Iterator<Element> childs = doc.getRootElement().elements(elementName).iterator();
		while(childs.hasNext()) {
				Element el = childs.next();
				if(el.attributeValue(idName).toLowerCase().equals(nodeName.toLowerCase()) && el.attributeValue(keyName).toLowerCase().equals(key.toLowerCase())) {					
					s.setKey(el.attributeValue(keyName));
					s.setValue(el.attributeValue(valName));
					break;
				}
		}
		return s;
	}
	
	public static Map<String,String> readXML(String xmlName,String elementName,String idName,String nameValue) {
		Document doc=null;
		try {
			doc = (Document) getXmlDocument(PATH+xmlName);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		Map<String, String> map=new HashMap<String, String>();
		Iterator<Element> childs = doc.getRootElement().elements(elementName).iterator();
		while(childs.hasNext()) {
			Element el = childs.next();
			if(el.attributeValue(idName).equals(nameValue)) {	
				Iterator<Element> contents=el.elements("content").iterator();	
				while(contents.hasNext()) {
					Element e = contents.next();
					map.put(e.attributeValue("value"), e.getText());
				}				
				break;
			}
		}		
		return map;
	}
	
	
	
	
	
	
	
	/***
	 * 例 1<select name="education"  value="不限"></select>
	 * 例 2<select idName="education"  valName="不限"></select>
	 * @param xmlName 配置文件名称（xml文件）
	 * @param elementName 如例1 select  节点名称
	 * @param idName 唯一区别的属性  如例1：name为idName，如2:idName
	 * @param valName 如例1:value,例2valName
	 * @param nodeName 为idName的值
	 * @return
	 */
	public static String readXML(String xmlName,String elementName,String idName,String valName,String nodeName){
		String value="";
		Document doc=null;
		try {
			doc = (Document) getXmlDocument(PATH+xmlName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Element ele = doc.getRootElement();
		List eles = ele.elements(elementName);
		for (Iterator childs = eles.iterator(); childs.hasNext();) {
				Element el = (Element) childs.next();
				if(el.attributeValue(idName).equals(nodeName)) {
					value=el.attributeValue(valName);
				}
		}
		return value;
	}

	public static Document getXmlDocument(String saveXmlFilePath) {
		setSaveXmlFilePath(saveXmlFilePath);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = (Document) reader.read(new File(saveXmlFilePath));
		}catch (Exception e) {
			
		}
			return doc;
	}
	
	public static void saveDocument(Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileOutputStream(saveXmlFilePath),format);
		writer.write(doc);
		writer.close();
	}
	
	public static String getSaveXmlFilePath() {
		return saveXmlFilePath;
	}

	public static void setSaveXmlFilePath(String saveXmlFilePa) {
		saveXmlFilePath = saveXmlFilePa;
	}
	
	
	public static void main(String[] args) {
		  //创建根节点
		  Document doc=DocumentHelper.createDocument();
		  //创建处理指令
		  //doc.addProcessingInstruction("xml-stylesheet", "type='text/xsl' href='students.xsl'");
		  //创建根元素
		  //Element root = DocumentHelper.createElement("students");
		  
		  //这样写就看不到处理指令
		  Element root = doc.addElement("SET");
		  //把根元素添加到根节点上
		  doc.setRootElement(root);
		  //创建元素节点
		  Element stu1 = root.addElement("student");
		  Element name1 = stu1.addElement("name");
		  Element age1 = stu1.addElement("age");
		  
		  //设置元素的文本内容
		  name1.setText("老不死的");
		  age1.setText("6287555");
		  Element stu2 = root.addElement("student");
		  Element name2 = stu2.addElement("name");
		  Element age2 = stu2.addElement("age");
		  
		  name2.setText("张三");
		  age2.setText("16");
		  
		//  PrintWriter pw =new PrintWriter(System.out);
		  OutputFormat out = new OutputFormat(" ",true);
		  out.setEncoding("UTF-8");
		  
		  try {
		   //内置缓冲区
		   //doc.write(pw);
		   //pw.flush();
		  // pw.close();
		   //输出到文件
		   XMLWriter xml = new XMLWriter(new FileWriter("dom4jstudents.xml"),out);
		   xml.write(doc);
		   xml.flush();
		   xml.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }

		 }

}
