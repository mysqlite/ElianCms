package com.elian.cms.syst.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 数据项类型类
 * 
 * @author CZH
 * 
 */
public class DataTypeUtils {
    /* 变量字符串 */
    private String htmlcore = "";
    /* 变量html代码 */
    private StringBuffer dateHtml = new StringBuffer();
    /* 变量script代码 */
    private StringBuffer dateScript = new StringBuffer();
    /* 变量css代码 */
    private StringBuffer dateCss = new StringBuffer();
    /* 数组 */
    private Map<String, StringBuffer> map = new HashMap<String, StringBuffer>();

    /**
     * 
     * 日期控件
     * 
     * @param field
     *            字段名称，必须与后台的字段名称相对应
     * 
     */
    public Map<String, StringBuffer> datePicker(String field, String outField) {
        // 引入javascript文件
        htmlcore =
            "<script language='javascript' type='text/javascript' src='plugin/My97DatePicker/WdatePicker.js'>";
        dateScript.append("htmlcore");
        map.put("script", dateScript);
        // HTML代码
        htmlcore =
            "<input name='"
                + field
                + "' value="
                + outField
                + " class='Wdate' type='text' onClick='WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})' />";
        dateHtml.append(htmlcore);
        map.put("html", dateHtml);

        return map;
    }

    /**
     * 
     * 编辑器控件
     * 
     * @param field
     *            字段名称，必须与后台的字段名称相对应
     * 
     */
    public Map<String, StringBuffer> editorPicker(String field) {
        // 引入javascript文件
        htmlcore =
            "<script language='javascript' type='text/javascript' src='plugin/ckeditor/config.js'></script>";
        dateScript.append("htmlcore");
        htmlcore =
            "<script language='javascript' type='text/javascript' src='plugin/ckeditor/ckeditor.js'></script>";
        dateScript.append("htmlcore");
        map.put("script", dateScript);
        // HTML代码
        htmlcore = "<input id='newsContent' name='" + field + "' />";
        dateHtml.append(htmlcore);
        htmlcore = "<script type='text/javascript'>CKEDITOR.replace('newsContent');</script>";
        dateHtml.append(htmlcore);
        map.put("html", dateHtml);

        return map;
    }

    /**
     * 
     * 颜色选择器
     * 
     * @param field
     *            字段名称，必须与后台的字段名称相对应
     * 
     */
    public Map<String, StringBuffer> colorPicker(String field) {
        // 引入css文件
        htmlcore =
            "<link rel='stylesheet' type='text/css' href='plugin/bigcolorpicker/css/jquery.bigcolorpicker.css'>";
        dateCss.append(htmlcore);
        map.put("css", dateCss);
        // 引入javascript文件
        htmlcore =
            "<script language='javascript' type='text/javascript' src='plugin/bigcolorpicker/js/jquery-1.6.1.js'></script>";
        dateScript.append("htmlcore");
        htmlcore =
            "<script language='javascript' type='text/javascript' src='plugin/bigcolorpicker/js/jquery.bigcolorpicker.js'></script>";
        dateScript.append("htmlcore");
        map.put("script", dateScript);
        // HTML代码
        htmlcore = "<input id='newsContent' name='" + field + "' />";
        dateHtml.append(htmlcore);
        htmlcore = "<script type='text/javascript'>CKEDITOR.replace('newsContent');</script>";
        dateHtml.append(htmlcore);
        map.put("html", dateHtml);

        return map;
    }

}
