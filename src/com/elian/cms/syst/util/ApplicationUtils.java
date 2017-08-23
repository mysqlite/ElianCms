package com.elian.cms.syst.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.model.User;

/**
 * 应用程序运行环境
 * 
 * @author Joe
 */
public final class ApplicationUtils {
	private static final String FORWARD_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
	/**当前项目跟路径*/
	public static final String PATH = "path";
	/** 验证码 */
	public static final String CAPTCHA = "captcha";
	/** 当前用户 */
	public static final String USER = "_user";
	/** 当前站点信息 */
	public static final String SITE = "_site";
	/** 当前组织 */
	public static final String SUBSTATUS = "_substation";
	public static final String HOSPITAL = "_hospital";
	public static final String COMPANY = "_company";
	/** 权限信息 */
	public static final String ACTION_SET = "actionSet";
	/** Ajax映射ID */
	public static final String ID = "id";
	/** Ajax映射IDS */
	public static final String IDS = "ids";
	/** Ajax映射DISABLE */
	public static final String DISABLE = "disable";
	/** Ajax映射STATUS */
	public static final String STATUS = "_status";
	/** Ajax映射SORT */
	public static final String SORT = "sort";
	/** Ajax映射下拉列表值 */
	public static final String SELECTED_VALUE = "selectedValue";
	/** 当前用户角色 */
	public static final String ROLELIST = "_roleList";
	/**缓存登录前的页面*/
	public static final String LOGIN_PAGE_BEFORE="_forward_page";

	public static Map<String, Object> getSession() {
		return ServletActionContext.getContext().getSession();
	}

	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public static void setUser(User user) {
		getSession().put(USER, user);
	}

	public static User getUser() {
		if(getSession()==null)return null;
		return (User) getSession().get(USER);
	}
	
	public static User getUser(HttpServletRequest request) {
		return (User) getSessionDWR(request).getAttribute(USER);
	}
	
	
	public static void setForwardPage(String url) {
		getSession().put(LOGIN_PAGE_BEFORE, url);
	}

	public static String getForwardPage() {
		String url="";
			if(getSession().get(LOGIN_PAGE_BEFORE)!=null) {
				url=getSession().get(LOGIN_PAGE_BEFORE).toString();
			}
		return  url;
	}
	
	public static void removeForwardPage() {
		getSession().remove(LOGIN_PAGE_BEFORE);
	}

	public static void setImgFtp(Ftp ftp) {
		if(getSession()!=null)
			getSession().put(EhcacheUtils.IMG_FTP, ftp);
	}

	public static void setSwfFtp(Ftp ftp) {
		if(getSession()!=null)
			getSession().put(EhcacheUtils.SWF_FTP, ftp);
	}
	
	public static void setFileFtp(Ftp ftp) {
		if(getSession()!=null)
			getSession().put(EhcacheUtils.FILE_FTP, ftp);
	}

	public static void setImgFtp(Ftp ftp,HttpServletRequest request) {
		if(getSession()!=null)
			getSessionDWR(request).setAttribute(EhcacheUtils.IMG_FTP,ftp);
	}

	public static void setStaticFtp(Ftp ftp) {
		if(getSession()!=null)
			getSession().put(EhcacheUtils.STATIC_FTP, ftp);
	}
	
	public static void setRoleList(List<Role> role) {
		getSession().put(ROLELIST, role);
	}

	public static Role getRoleList() {
		return (Role) getSession().get(ROLELIST);
	}

	public static void removerRoleList() {
		getSession().remove(ROLELIST);
	}

	public static void removeUser() {
		getSession().remove(USER);
	}

	public static void setSite(Site site) {
		getSession().put(SITE, site);
	}

	public static Site getSite() {
		return (Site) getSession().get(SITE);
	}

	public static void removeSite() {
		getSession().get(SITE);
	}

	public static void setSubstation(Substation substation) {
		getSession().put(SUBSTATUS, substation);
	}

	public static Substation getSubstation() {
		return (Substation) getSession().get(SUBSTATUS);
	}
	
	public static Substation getSubstation(HttpServletRequest request) {
		return (Substation) getSessionDWR(request).getAttribute(SUBSTATUS);
	}

	public static void removeSubstation() {
		getSession().get(SUBSTATUS);
	}

	public static void setHospital(Hospital hospital) {
		getSession().put(HOSPITAL, hospital);
	}

	public static void setCompany(Company company) {
		getSession().put(COMPANY, company);
	}
	
	public static Hospital getHospital() {
		return (Hospital) getSession().get(HOSPITAL);
	}
	
	public static Company getCompany() {
		return (Company) getSession().get(COMPANY);
	}
	
	public static Hospital getHospital(HttpServletRequest request) {
		return (Hospital) getSessionDWR(request).getAttribute(HOSPITAL);
	}

	public static void removeHospital() {
		getSession().get(HOSPITAL);
	}

	public static void setActionSet(Set<String> actionSet) {
		getSession().put(ACTION_SET, actionSet);
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getActionSet() {
		return (Set<String>) getSession().get(ACTION_SET);
	}

	public static String getAuditor() {
		// String auditor=getUser().getAccount();
		String extName = null != getUser().getRealName() ? getUser()
				.getRealName() : "";
		// if(StringUtils.isNotBlank(extName))
		return extName;// auditor;//+"["+extName+"]";
		// else
		// return auditor;
	}

	public static void removeActionSet() {
		getSession().remove(ACTION_SET);
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getActionSet(HttpServletRequest request) {
		return (Set<String>) getSessionDWR(request).getAttribute(ACTION_SET);
	}

	public static HttpSession getSessionDWR(HttpServletRequest request) {
		return request.getSession();
	}

	public static Site getSiteDWR(HttpServletRequest request) {
		return (Site) getSessionDWR(request).getAttribute(SITE);
	}

	public static String getTempFolderPath(HttpServletRequest request) {
		return getSiteRoot(request) + "temp";
	}

	public static String getSiteRoot(HttpServletRequest request) {
		return getSessionDWR(request).getServletContext().getRealPath("\\");
	}

	public static String getSiteRoot() {
		return getRequest().getSession().getServletContext().getRealPath("\\");
	}

	public static String getLocalTempRoot(HttpServletRequest request) {
		if (request.getLocalAddr().equals("0.0.0.0"))
			return "http://localhost:" + request.getLocalPort()
					+ request.getContextPath() + "/temp/";
		return "http://" + request.getLocalAddr() + ":"
				+ request.getLocalPort() + request.getContextPath() + "/temp/";
	}

	/**
	 * 是否总站
	 */
	public static boolean isMainStation() {
		return ElianUtils.COMP_TYPE_MAIN.equals(getSite().getComType());
	}
	
	public static boolean isMainStation(HttpServletRequest request) {
		return ElianUtils.COMP_TYPE_MAIN.equals(getSiteDWR(request).getComType());
	}

	/**
	 * 是否分站
	 */
	public static boolean isSubStation() {
		return ElianUtils.COMP_TYPE_SUBS.equals(getSite().getComType());
	}
	public static boolean isSubStation(HttpServletRequest request) {
		return ElianUtils.COMP_TYPE_SUBS.equals(getSiteDWR(request).getComType());
	}
	/**是否医院*/
	public static boolean isHosp() {
		return ElianUtils.COMP_TYPE_HOSP.equals(getSite().getComType());
	}
	public static boolean isHosp(HttpServletRequest request) {
		return ElianUtils.COMP_TYPE_HOSP.equals(getSiteDWR(request).getComType());
	}
	

	/**
	 * 绝对路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path, HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(path);
	}
	
	/**
	 *项目路径
	 * @return
	 */
	public static String getPath(HttpServletRequest request) {
		return request.getSession().getAttribute(PATH).toString();
	}
	/**
	 *项目路径
	 * @return
	 */
	public static String getPath() {
		return getSession().get(PATH).toString();
	}

	/**
	 * 在验证码创建时，已加入到Session中
	 */
	public static String getCaptcha() {
		return (String) getSession().get(CAPTCHA);
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			}
			else {
				return ip;
			}
		}
		else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 获取服务器跳转URL
	 */
	public static String getUrl() {
		String url = (String) getRequest().getAttribute(
				FORWARD_REQUEST_URI_ATTRIBUTE);
		if (url == null) {
			url = getRequest().getRequestURI();
		}
		return url;
	}

	/**
	 * 获得绝对路径
	 * 
	 * @param path
	 */
	public static String getRealPath(HttpServletRequest request, String path) {
		if (request == null)
			request = getRequest();
		return request.getSession().getServletContext().getRealPath(path);
	}

	/**
	 * 异步交互的获取列表传递Id
	 */
	public static Integer getAjaxId() {
		String id = getRequest().getParameter(ID);
		if (StringUtils.isNotBlank(id)) {
			return Integer.valueOf(id);
		}
		return null;
	}
	
	/**
	 * 异步交互获取表单值
	 */
	public static String getAjax(String key) {
		return getRequest().getParameter(key);
	}


	/**
	 * 异步交互的获取列表页选中的所有Id
	 */
	public static List<Integer> getAjaxIds() {
		String ids = getRequest().getParameter(IDS);
		List<Integer> idList = null;
		if (StringUtils.isNotBlank(ids)) {
			idList = new ArrayList<Integer>();
			String[] items = ids.split(",");
			for (String id : items) {
				if (StringUtils.isNotBlank(id))
					idList.add(Integer.valueOf(id));
			}
		}
		return idList;
	}

	/**
	 * 异步交互的获取列表中是否启用的boolean字符串
	 */
	public static Boolean getAjaxDisable() {
		String disable = getRequest().getParameter(DISABLE);
		if (StringUtils.isBlank(disable))
			return null;
		return Boolean.valueOf(disable);
	}

	/**
	 * 异步交互的获取列表中状态的字符串
	 */
	public static int getAjaxStatus() {
		String status = getRequest().getParameter(STATUS);
		if (StringUtils.isBlank(status))
			return -1;
		return Integer.valueOf(status);
	}

	/**
	 * 异步交互的获取列表传递Id
	 */
	public static Integer getAjaxSort() {
		String sort = getRequest().getParameter(SORT);
		if (StringUtils.isNotBlank(sort)) {
			return Integer.valueOf(sort);
		}
		return null;
	}

	/**
	 * 下拉列表选中的结果
	 */
	public static String getAjaxSelectedValue() {
		return getRequest().getParameter(SELECTED_VALUE);
	}

	/**
	 * 用于向客户端发送内容
	 * 
	 * @param content
	 */
	public static void sendJsonArray(List<?> list) {
		JSONArray array = JSONArray.fromObject(list);
		sendJsonStr(array.toString());
	}

	/**
	 * 用于向客户端发送跨域内容
	 * 
	 * @param content
	 */
	public static void sendJsonpObj(JSONObject obj){
		StringBuffer sb = new StringBuffer();
		String cb = ApplicationUtils.getRequest().getParameter("callback");
		if (cb != null) {// 如果是跨域
			sb.append(cb);
			sb.append("(");
			sb.append(obj.toString());
			sb.append(")");
		}else {
			sb.append(obj.toString());
		}
		ApplicationUtils.sendJsonStr(sb.toString());
	}
	/**
	 * 发送跨域jsonp串
	 * 
	 * @param content
	 */
	public static void sendJsonpString(String content){
		StringBuffer sb = new StringBuffer();
		String cb = ApplicationUtils.getRequest().getParameter("callback");
		if (cb != null) {// 如果是跨域
			sb.append(cb);
			sb.append("(");
			sb.append(content);
			sb.append(")");
		}else {
			sb.append(content);
		}
		ApplicationUtils.sendJsonStr(sb.toString());
	}
	/**
	 * 发送跨域list
	 * 
	 * @param content
	 */
	public static void sendJsonpList(List<?> list){
		if(null==list){
			sendJsonpString(null);
			return;
		}
		JSONArray array = JSONArray.fromObject(list);
		sendJsonpString(array.toString());
	}
	/**
	 * 用于向客户端发送内容
	 * 
	 * @param content
	 */
	public static void sendJsonStr(String content) {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(content);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 向前台发送消息
	 * @param msg
	 */
	public static void sendMsg(String msg){
		JSONObject josn=new JSONObject();
		josn.put("MSG", msg);
		ApplicationUtils.sendJsonStr(josn.toString());	
	}

	public static boolean isCompany() {
		return getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP);
	}
	
	public static String json(Object obj,String... filed) {
		if(filed==null) return null;
		StringBuffer json=new StringBuffer();
		try{
			json.append("{");
			for(int i=0;i<filed.length;i++){
				PropertyDescriptor property = new PropertyDescriptor(filed[i],obj.getClass());
				Method  getMethod= property.getReadMethod();
				json.append("\""+filed[i]+"\"").append(":").append("\""+getMethod.invoke(obj)+"\"");
				if(i+1!=filed.length)
					json.append(",");
			}
			json.append("}");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static String jsonList(List<?> list,String... filed){
		if(CollectionUtils.isEmpty(list)) return null;
		StringBuffer json=new StringBuffer();
		Iterator<?> it= list.iterator();
		json.append("[");
		while (it.hasNext()) {
			Object obj= it.next();
			json.append(json(obj,filed));
			if(it.hasNext())
				json.append(",");
		}
		json.append("]");
		return json.toString();
	}
	
	public static String getSitePath(String domain) {
		return ApplicationUtils.getRequest().getScheme()+"://"+domain+ApplicationUtils.getRequest().getContextPath()+"/";
	}
	
	/*public static void main(String[] args) {
		List list=new ArrayList();
		list.add(new Test("tanghaiyang", 10, new Date()));
		list.add(new Test("tanghaiyang", 10, new Date()));
		list.add(new Test("tanghaiyang", 10, new Date()));
		list.add(new Test("tanghaiyang", 10, new Date()));
		System.out.println(ApplicationUtils.jsona(list,"name","age"));
	}*/
}
