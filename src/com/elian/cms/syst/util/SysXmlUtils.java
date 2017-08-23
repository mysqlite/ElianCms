package com.elian.cms.syst.util;

import java.util.List;
import java.util.Map;

import com.elian.cms.syst.model.SelectItem;

public class SysXmlUtils {
	private static final String  defIdName="name";
	private static final String defKeyname="key";
	private static final String defValName="value";
	private static final String defelementName="select";
	//public static String readXML(String xmlName,String elementName,String idName,String valName,String nodeName)
	public static String getString(String nodeName) {
		
	 return	ReadWriteXMLUtils.readXML("SysConfig.xml","odd",defIdName,defValName,nodeName);
	}
	public static List<SelectItem> getXMLSelect(String nodeName){
		
		return ReadWriteXMLUtils.readXML("Elian.xml",defelementName,defIdName,defKeyname,defValName,nodeName);
	}
	
	public static SelectItem getXMLSelect(String nodeName,String nodeKey){		
		return ReadWriteXMLUtils.readXML("Elian.xml",defelementName,defIdName,defKeyname,defValName,nodeName,nodeKey);
	}
	
	public static SelectItem getContentActionName(String nodeName,String nodeKey){		
		return ReadWriteXMLUtils.readXML("Elian.xml","content",defIdName,defKeyname,defValName,nodeName,nodeKey);
	}
	
	public static Map<String, String> getDefaultSiteHeadAndFoot(String site){
		return ReadWriteXMLUtils.readXML("Elian.xml","SiteHeadAndFoot","name",site);
	}
	
	public static void main(String[] args) {
		System.out.println(getString("quartz"));
	}
}
