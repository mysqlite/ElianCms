package com.elian.cms.syst.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页基类
 * 
 * @author Joe
 * 
 */
public class PaginationSupport implements Serializable {
	private static final long serialVersionUID = 2234283310680151858L;
	/** 默认显示页码数 */
	public static final int DEFAULT_OFFSET_SIZE = 3;
	/** 默认每页行数 */
	public static final int DEFAULT_ROW_SIZE = 10;

	 /** 分页名称 */
	 private String name = "pagination";
	/** 显示页码数 */
	private int offsetSize = DEFAULT_OFFSET_SIZE;
	/** 每页行数 */
	private int rowSize = DEFAULT_ROW_SIZE;
	/** 记录总数 */
	private long rowCount;
	/** 当前页码 */
	private int pageNo = 1;

	/** 别名 */
	private String alias;
	/** 搜索条件内容 */
	private String conditionContent;
	/** 转换后的搜索条件内容 */
	private String converterContent;
	/** 搜索条件属性名称 */
	private String conditionName;
	/** 搜索条件Map */
	private Map<String, String> conditionMap;

	public PaginationSupport() {
	}

	public PaginationSupport(String name) {
		 this.name = name;
	}

	public PaginationSupport(Map<String, String> conditionMap) {
		this.conditionMap = conditionMap;
	}

	public PaginationSupport(String name, Map<String, String> conditionMap) {
		this.name = name;
		this.conditionMap = conditionMap;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffsetSize() {
		return offsetSize;
	}

	public void setOffsetSize(int offsetSize) {
		this.offsetSize = offsetSize;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getConditionContent() {
		return conditionContent;
	}

	public void setConditionContent(String conditionContent) {
		this.conditionContent = conditionContent;
	}

	public String getConverterContent() {
		return converterContent;
	}

	public void setConverterContent(String converterContent) {
		this.converterContent = converterContent;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public Map<String, String> getConditionMap() {
		return conditionMap;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * rowSize;
	}

	/**
	 * 获取总页数
	 */
	public long getPageCount() {
		if (rowCount % rowSize == 0)
			return rowCount / rowSize;
		else
			return (rowCount / rowSize) + 1;
	}

	/**
	 * 获取前台分页信息
	 */
	public String getPageInfo() {
		StringBuilder sb = new StringBuilder();
		// addPageScript(sb);
		addPageTag(sb);
		return sb.toString();
	}

	/**
	 * 添加分页用到的JS代码
	 */
	public void addPageScript(StringBuilder sb) {
		sb.append(" <script type=\"text/javascript\"> ");
		sb.append(System.getProperty("line.separator"));
		sb.append("     //<![CDATA[  ");
		sb.append(System.getProperty("line.separator"));

		sb.append("        function go(url, pName, pNo, conditionContent, conditionName, rowSize, currentPage){");
		sb.append(System.getProperty("line.separator"));
		sb.append("          var pageNo = document.getElementById(pNo);");
		sb.append(System.getProperty("line.separator"));
		sb.append("          var cPage = document.getElementById(currentPage);");
		sb.append(System.getProperty("line.separator"));
		sb.append("          if(Number(pageNo.value)==Number(cPage.value))");
		sb.append(System.getProperty("line.separator"));
		sb.append("            return;");
		sb.append(System.getProperty("line.separator"));
		sb.append("          window.location.href=url+\"?\"+pName+\".pageNo=\"+Number(pageNo.value,1)+\"&\"+pName+\".conditionContent=\"+conditionContent+\"&\"+pName+\".conditionName=\"+conditionName+\"&\"+pName+\".rowSize=\"+rowSize;");
		sb.append(System.getProperty("line.separator"));
		sb.append("        }");
		sb.append(System.getProperty("line.separator"));
		sb.append("        ");
		sb.append(System.getProperty("line.separator"));
		sb.append("        function numberFormat(pageNo,lastPage){");
		sb.append(System.getProperty("line.separator"));
		sb.append("          var idNum = document.getElementById(pageNo);");
		sb.append(System.getProperty("line.separator"));
		sb.append("          if(!Number(idNum.value)||idNum.value==null||idNum.value==undefined){");
		sb.append(System.getProperty("line.separator"));
		sb.append("              idNum.value=1;");
		sb.append(System.getProperty("line.separator"));
		sb.append("            }");
		sb.append(System.getProperty("line.separator"));
		sb.append("          else{");
		sb.append(System.getProperty("line.separator"));
		sb.append("            var last = document.getElementById(lastPage);");
		sb.append(System.getProperty("line.separator"));
		sb.append("            if(Number(idNum.value)>Number(last.value)){");
		sb.append(System.getProperty("line.separator"));
		sb.append("              idNum.value=last.value;");
		sb.append(System.getProperty("line.separator"));
		sb.append("            }");
		sb.append(System.getProperty("line.separator"));
		sb.append("          }");
		sb.append(System.getProperty("line.separator"));
		sb.append("          idNum.value=Number(idNum.value);");
		sb.append(System.getProperty("line.separator"));
		sb.append("        }");
		sb.append(System.getProperty("line.separator"));
		sb.append("        function checkEnter(event, clickId){");
		sb.append(System.getProperty("line.separator"));
		sb.append("    　　    		if(event.keyCode == 13){");
		sb.append(System.getProperty("line.separator"));
		sb.append("    　　      			document.getElementById(clickId).click();");
		sb.append(System.getProperty("line.separator"));
		sb.append("    　　    		}");
		sb.append(System.getProperty("line.separator"));
		sb.append("    　　		}");
		sb.append(System.getProperty("line.separator"));

		sb.append(System.getProperty("line.separator"));
		sb.append("     //]]>   ");
		sb.append(System.getProperty("line.separator"));
		sb.append(" </script> ");
		sb.append(System.getProperty("line.separator"));
	}

	/**
	 * 添加分页标签代码
	 */
	public void addPageTag(StringBuilder sb) {
		sb.append("<ul id='pagination-digg' class='pagination clearfix' style='float: left'>");
		sb.append("<input id=\"currentPage\" name=\"pagination.pageNo\" type=\"hidden\" value=\"")
				.append(pageNo).append("\">");
		long pageCount = getPageCount();
		sb.append("<input id=\"lastPage\" type=\"hidden\" value=\"").append(
				pageCount).append("\">");
		if (pageNo > 1) {
			sb.append("<li title='首页' class='page_jump'>");
			sb.append("<a onclick='toPage(1)'>首页</a>");
			sb.append("</li>");
			
			sb.append("<li title='上一页' class='page_jump'>");
			sb.append("<a onclick='toPage(").append(pageNo - 1).append(
					")'>&#139;上一页</a>");
			sb.append("</li>");
		}
		setMiddlePage(sb, pageCount);
		if (pageNo < pageCount) {
			sb.append("<li title='下一页' class='page_jump'>");
			sb.append("<a onclick='toPage(").append(pageNo + 1).append(
					")'>下一页&#155;</a>");
			sb.append("</li>");
			
			sb.append("<li title='尾页' class='page_jump'>");
			sb.append("<a onclick='toPage(").append(pageCount).append(
					")'>尾页</a>");
			sb.append("</li>");
		}
		sb.append("<li class='page'>");
		sb.append("<input type='text' id='pageNo' class='ipage size2' title='请输入页码' onkeydown=\"checkEnter(event, 'igo')\" onkeyup=\"numberFormat('pageNo','lastPage')\"");
		sb.append("value='").append(pageNo).append("'/>");
		sb.append("<span class=\"page-count\">&nbsp;/");
		sb.append(pageCount == 0 ? 1 : pageCount);
		sb.append("</span>");
		sb.append("</li>");
		sb.append("<li title='跳转页面' class='go'>");
		sb.append("<a id='igo' class='igo' onclick=\"go('pageNo','currentPage')\">GO</a>");
		sb.append("</li>");
		sb.append("</ul>");
	}

	/**
	 * 设置分页中间显示页码
	 */
	public void setMiddlePage(StringBuilder sb, long pageCount) {
		for (int i = pageNo - offsetSize; i < pageNo; i++) {
			if (i > pageCount)
				break;
			if (i < 1)
				continue;
			sb.append("<li title='第").append(i).append("页' class='page_jump'>");
			sb.append("<a onclick='toPage(").append(i).append(")'>").append(i)
					.append("</a>");
			sb.append("</li>");
		}
		if (pageNo <= pageCount)
			sb.append("<li title='第" + pageNo + "页' class='pages cur'>"
					+ pageNo + "</li>");
		for (int i = pageNo + 1; i <= pageNo + offsetSize; i++) {
			if (i > pageCount)
				break;
			sb.append("<li title='第").append(i).append("页' class='page_jump'>");
			sb.append("<a onclick='toPage(").append(i).append(")'>").append(i)
					.append("</a>");
			sb.append("</li>");
		}
	}

	public String addSearchParams() {
		return new StringBuilder("&").append(name).append(".conditionContent=")
				.append(conditionContent == null ? "" : conditionContent)
				.append("&").append(name).append(".conditionName=").append(
						conditionName == null ? "" : conditionName).append("&")
				.append(name).append(".rowSize=").append(rowSize).toString();
	}
}