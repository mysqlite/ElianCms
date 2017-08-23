package com.elian.cms.syst.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class PagedingDivUtils {

	private static int STEP = 3;// 偏移量,即显示2x3+1=7个
	private static int LEFT_NUM = 0;// 左界限
	private static int RIGHT_NUM = 0;// 右界限
	public final static Integer PAGESIZE=20;
	/**
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param totalSize
	 *            总记录数
	 * @return links
	 */
	public static String getPageLink(int currentPage,int totalSize, int step) {
		/** get totalPage **/
		STEP = step;
		/** get totalPage **/
		int totalPage = (totalSize % PAGESIZE == 0) ? (totalSize / PAGESIZE)
				: (totalSize / PAGESIZE + 1);

		String links = "";

		if (currentPage > totalPage)
			currentPage = totalPage;
		if (currentPage < 1)
			currentPage = 1;

		/** 总页数大于1时候才进行分页处理 **/
		if (totalPage > 1)
			links += PagedingDivUtils.setLink(currentPage, totalPage)
					+ PagedingDivUtils.setScript();
		return links;
	}

	/**
	 * 获取url链接
	 * 
	 * @return url
	 */
	@SuppressWarnings("unchecked")
	public static String getUrl() {
		String url = "";
		/** 获取request对象 **/
		HttpServletRequest request = ServletActionContext.getRequest();
		/** 获取所有的参数 **/
		Enumeration param = request.getParameterNames();
		/** 遍历所有参数,如果不等于showpage,则把当前参数加入url中,其中showpage为固定参数,表示为当前页码 **/
		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			if (!pname.equalsIgnoreCase("showpage")) {
				url += pname + "=" + request.getParameter(pname) + "&";
			}
		}
		/** 判断是否有参数,如果没有则直接在url后面加?,反之加&(上面一句已处理) **/
		if (!"".equals(url))
			url = request.getRequestURI() + "?" + url;
		else
			url = request.getRequestURI() + "?";
		return url;
	}

	@SuppressWarnings("unchecked")
	public static String getStaticUrl(String... pnames) {
		String url = "";
		/** 获取request对象 **/
		HttpServletRequest request = ServletActionContext.getRequest();
		/** 获取所有的参数 **/
		Enumeration param = request.getParameterNames();
		/** 遍历所有参数,如果不等于showpage,则把当前参数加入url中,其中showpage为固定参数,表示为当前页码 **/
		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			if (pname.equals("invitation.comtype")) {// 招标类型
				String tpname = "t";
				if (!pname.equalsIgnoreCase("showpage")) {
					url += "_" + tpname + request.getParameter(pname) + "";
				}
			}
			if (pname.equals("province.provId")) {// 省份
				String pnamer = "p";
				if (!pname.equalsIgnoreCase("showpage")) {
					url += "_" + pnamer + request.getParameter(pname) + "";
				}
			}
			if (pname.equals("newsTypeId")) {// 新闻
				String pnamer = "list";
				if (!pname.equalsIgnoreCase("showpage")) {
					url += pnamer + "_" + request.getParameter(pname) + "";
				}
			}
			if (pname.equals("inforTypeId")) {// 资讯
				String pnamer = "list";
				if (!pname.equalsIgnoreCase("showpage")) {
					url += pnamer + "_" + request.getParameter(pname) + "";
				}
			}
		}
		/** 判断是否有参数,如果没有则直接在url后面加?,反之加&(上面一句已处理) **/
		if (!"".equals(url))
			url = request.getRequestURI() + "?" + url;
		else
			url = request.getRequestURI() + "?";
		return url;
	}

	/**
	 * 处理数字链接的左右边界值
	 * 
	 * @param currentPage
	 * @param totalPage
	 */
	public static void setBounds(int currentPage, int totalPage) {
		if (currentPage - STEP < 1) {
			LEFT_NUM = 1;
		}
		else {
			LEFT_NUM = currentPage - STEP;
		}
		if (currentPage + STEP > totalPage) {
			RIGHT_NUM = totalPage;
		}
		else {
			RIGHT_NUM = currentPage + STEP;
		}

		/** 如果页数大于(2xSTEP+1),但是显示少于(2xSTEP+1),则强制显示(2xSTEP+1) **/
		if (totalPage <= 2 * STEP + 1) {
			LEFT_NUM = 1;
			RIGHT_NUM = totalPage;
		}
		else {
			if (2 * STEP + 1 <= totalPage) {
				if (RIGHT_NUM < 2 * STEP + 1) {
					RIGHT_NUM = 2 * STEP + 1;
				}
			}
			if (totalPage - 2 * STEP > 0) {
				if (LEFT_NUM > totalPage - 2 * STEP) {
					LEFT_NUM = totalPage - 2 * STEP;
				}
			}
		}
	}

	public static String setLink(int currentPage, int totalPage) {
		/** 处理链接 **/
		String url = getUrl();

		String links2 = "<ul id='pagination-digg' class='pagination clearfix'>";

		/** 显示分页信息 **/
		// links2+="<li title='Total Pages:"+totalPage+",Current Page:"+currentPage+"' class='size'><a href='javascript:void(0)'>页码:"+currentPage+"/"+totalPage+"</a></li>";

		/** 采用数字方式显示链接 **/

		/** 处理首页与上页 **/
		if (currentPage == 1) {
			// links2+="<li title='First Page' class='page_jump no_click'>&laquo;首页</li>";
			// links2+="<li title='Previous Page' class='page_jump no_click'>&#139;上页</li>";
		}
		else {
			// links2+="<li title='First Page' class='page_jump'><a href='"+url+"showpage=1'>&laquo;首页</a></li>";
			links2 += "<li title='上一页' class='page_jump'><a  target='_self' href='"
					+ url
					+ "showpage="
					+ (currentPage - 1)
					+ "'>&#139;上一页</a></li>";
		}
		/** 获取左右边界值 **/
		setBounds(currentPage, totalPage);
		/** 处理中间页 **/
		for (int i = LEFT_NUM; i <= RIGHT_NUM; i++) {
			if (i != currentPage)
				links2 += "<li title='第 " + i
						+ "页' class='page_jump'><a  target='_self'  href='"
						+ url + "showpage=" + i + "'>" + i + "</a></li>";
			else
				links2 += "<li title='当前第 "
						+ i
						+ "页' class='pages cur'>"
						+ i
						+ "</li><input type='hidden' name=\"showpage\" id=\"showpage\" value='"
						+ i + "'/>";
		}
		/** 处理下页与末页 **/
		if (currentPage == totalPage) {
			// links2+="<li title='Next Page' class='page_jump no_click'>下页&#155;</li>";
			// links2+="<li title='Last Page' class='page_jump no_click'>末页&raquo;</li>";
		}
		else {
			links2 += "<li title='下一页' class='page_jump'><a  target='_self' href='"
					+ url
					+ "showpage="
					+ (currentPage + 1)
					+ "'>下一页&#155;</a></li>";
			// links2+="<li title='Last Page' class='page_jump'><a href='"+url+"showpage="+totalPage+"'>末页&raquo;</a></li>";
		}
		/** 添加跳转框 **/
		links2 += "<li class='page'><input type='text' id='ipage' class='ipage size2' title='请输入页码' value='"
				+ currentPage
				+ "'  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>";
		links2 += "/" + "<span id='itotal' >" + totalPage + "</span></li>";
		links2 += "<li title='跳转页面' class='go'><a href='javascript:void(0)' id='igo' class='igo'>GO</a></li>";

		links2 += "</ul>";
		links2 += "<div style='clear:both;'></div>";
		return links2;
	}

	public static String getstaticPageLink(String url, String staticFile,
			int currentPage, int pageSize, int totalSize, int step) {
		String url2 = url
				+ PagedingDivUtils.getStaticUrl().toString().substring(
						PagedingDivUtils.getStaticUrl().lastIndexOf("?") + 1);
		/** get totalPage **/
		STEP = step;
		/** get totalPage **/
		int totalPage = (totalSize % pageSize == 0) ? (totalSize / pageSize)
				: (totalSize / pageSize + 1);
		String links = "";

		if (currentPage > totalPage)
			currentPage = totalPage;
		if (currentPage < 1)
			currentPage = 1;

		/** 总页数大于1时候才进行分页处理 **/
		if (totalPage > 1)
			links += PagedingDivUtils.setStaticLinkString(url2, currentPage,
					totalPage)
					+ PagedingDivUtils.setStaticScript(staticFile);
		return links;
	}

	public static String setStaticLinkString(String url, int currentPage,
			int totalPage) {
		/** 处理链接 **/
		// String url = getUrl();
		String links2 = "<ul id='pagination-digg' class='pagination clearfix'>";
		/** 显示分页信息 **/
		// links2+="<li title='Total Pages:"+totalPage+",Current Page:"+currentPage+"' class='size'><a href='javascript:void(0)'>页码:"+currentPage+"/"+totalPage+"</a></li>";
		/** 采用数字方式显示链接 **/
		/** 处理首页与上页 **/
		if (currentPage == 1) {
			// links2+="<li title='First Page' class='page_jump no_click'>&laquo;首页</li>";
			// links2+="<li title='Previous Page' class='page_jump no_click'>&#139;上页</li>";
		}
		else {
			// links2+="<li title='First Page' class='page_jump'><a href='"+url+"p_1'>&laquo;首页</a></li>";
			links2 += "<li title='上一页' class='page_jump'><a target='_self' href='"
					+ url
					+ "_pg"
					+ (currentPage - 1)
					+ ".html'>&#139;上一页</a></li>";
		}
		/** 获取左右边界值 **/
		setBounds(currentPage, totalPage);
		/** 处理中间页 **/
		for (int i = LEFT_NUM; i <= RIGHT_NUM; i++) {
			if (i != currentPage)
				links2 += "<li title='第 " + i
						+ "页' class='page_jump'><a target='_self' href='" + url
						+ "_pg" + i + ".html'>" + i + "</a></li>";
			else
				links2 += "<li title='当前第 "
						+ i
						+ "页' class='pages cur'>"
						+ i
						+ "</li><input type='hidden' name=\"showpage\" id=\"showpage\" value='"
						+ i + "'/>";
		}
		/** 处理下页与末页 **/
		if (currentPage == totalPage) {
			// links2+="<li title='Next Page' class='page_jump no_click'>下页&#155;</li>";
			// links2+="<li title='Last Page' class='page_jump no_click'>末页&raquo;</li>";
		}
		else {
			links2 += "<li title='下一页' class='page_jump'><a target='_self' href='"
					+ url
					+ "_pg"
					+ (currentPage + 1)
					+ ".html'>下一页&#155;</a></li>";
			// links2+="<li title='Last Page' class='page_jump'><a href='"+url+"showpage="+totalPage+"'>末页&raquo;</a></li>";
		}
		/** 添加跳转框 **/
		links2 += "<li class='page'><input type='text' id='ipage' class='ipage size2' title='请输入页码' value='"
				+ currentPage
				+ "'  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"   onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>";
		links2 += "/" + "<span id='itotal' >" + totalPage + "</span></li>";
		// onkeypress=\"if (event.keyCode == 13)TOPAGE(this)\"
		links2 += "<li title='跳转页面' class='go'><a id='igo' class='igo'>GO</a></li>";
		links2 += "</ul>";
		links2 += "<div style='clear:both;'></div>";
		return links2;
	}

	public static String setStaticScript(String staticFile) {
		String script = "";
		script += "<script type='text/javascript'>";
		script += "G=function(id){return document.getElementById(id);};";
		script += "FORMAT=function(e){";
		script += "   var ie=navigator.appName=='Microsoft Internet Explorer'?true:false;";
		script += "   if(!ie) var key = e.which;";
		script += "   else var key = event.keyCode;";
		script += "   var itotal=parseInt((G('itotal').innerHTML));";
		script += " if(key==13){if(CHECK(G('ipage').value)){window.location.href=TOPAGE(G('ipage').value);}}";
		script += "   if (key==8|key==46|(key>=48|key<=57)){ return true;}";
		script += "   else{alert('请输入合法数字');G('ipage').value=G(\"showpage\").value;return false;}";
		script += "};";
		script += " CHECK=function(page){";
		script += "   var itotal=parseInt((G('itotal').innerHTML));";
		script += "   var pageshow=parseInt((G(\"showpage\").value));";
		script += "   if(page>itotal|page<1){G('ipage').value=G(\"showpage\").value;alert('输入超出有效范围'); return false;}";
		script += "    if(page==pageshow){return false;} ";
		script += "   else{ return true;}";
		script += "};";
		script += "TOPAGE=function(page){";
		script += "   var url=window.location.href;";
		script += "   var index=url.indexOf('_pg');";
		script += "   var index2=window.location.href.lastIndexOf('/');";
		script += "   var index3=window.location.href.lastIndexOf('.html');";
		script += "    var url2= window.location.href.substring(0,index2)+'/' +'"
				+ staticFile + "'; ";
		script += "   if(index<0){ url=url.substring(index2,index3);url2=window.location.href.substring(0,index2)+url+'_pg'+page+'.html';}";
		script += "   else{url=url.substring(index2,index3);url2=window.location.href.substring(0,index2)+url+'_pg'+page+'.html';";
		script += "    var url2=url2.substring(0,index)+'_pg'+page+'.html';}  ";
		script += "   return url2;";
		script += "};";
		script += " G('ipage').onkeypress=function(){return FORMAT(event);};";
		script += " G('igo').onclick=function(){";
		script += "   var ipage=G('ipage').value;";
		script += "   if(CHECK(ipage)) {window.location.href=TOPAGE(ipage);}";
		// script
		// +=" G('ipage').onkeypress=function(){if (event.keyCode == 13){var ipage=G('ipage').value;if(CHECK(ipage)){window.location.href=TOPAGE(ipage);}}";
		script += "};";
		// script +="  G('pgup').onclick=function(){ " ;
		// script +=
		// " var ipage=parseInt(G(\"showpage\").value)-1;if(CHECK(ipage)){window.location.href=TOPAGE(ipage);}}";
		// script +=
		// "  G('pgdown').onclick=function(){ var ipage=parseInt(G(\"showpage\").value)+1;if(CHECK(ipage)){window.location.href=TOPAGE(ipage);}}";
		script += "</script>";
		return script;
	}

	public static String setScript() {

		String script = "";
		script += "<script type='text/javascript'>";
		script += "G=function(id){return document.getElementById(id);};";
		script += "FORMAT=function(e){";
		script += "   var ie=navigator.appName=='Microsoft Internet Explorer'?true:false;";
		script += "   if(!ie) var key = e.which;";
		script += "   else var key = event.keyCode;";
		script += " if(key==13){if(CHECK(G('ipage').value)){window.location.href=TOPAGE(G('ipage').value);}}";
		script += "   if (key==8|key==46|(key>=48|key<=57)){ return true;}";
		script += "   else{alert('请输入合法数字');G('ipage').value=G(\"showpage\").value;return false;}";
		script += "   };";
		script += "CHECK=function(page){";
		script += "   var itotal=parseInt((G('itotal').innerHTML));";
		script += "   var pageshow=parseInt((G(\"showpage\").value));";
		script += "  if(page>itotal|page<1){G('ipage').value=G(\"showpage\").value;alert('输入超出有效范围'); return false;}";
		script += "    if(page==pageshow){return false;} ";
		script += "  else{ return true;}";
		script += "};";
		script += "TOPAGE=function(page){";
		script += "   var url=window.location.href;";
		script += "   var index=url.indexOf('showpage');";
		script += "   var index2=url.indexOf('pagesize') ; ";
		script += "   if(index<0){ if(index2>-1) {url=url+'&showpage='+page;}else{url=url+'?showpage='+page;}}";
		script += "   else{url=url.substring(0,index);url=url+'showpage='+page;}";
		script += "  return url;";
		script += "};";
		script += "G('ipage').onkeypress=function(){return FORMAT(event);};";
		script += "G('igo').onclick=function(){";
		script += "   var ipage=G('ipage').value;";
		script += "   if(CHECK(ipage)) {window.location.href=TOPAGE(ipage);}";
		script += "};";
		script += "</script>";
		return script;
	}

}
