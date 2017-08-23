package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.ZmNews;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 资讯
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class InformationAction extends BaseAction {
	private static final long serialVersionUID = 2610310520170069L;

	private Information information;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;

	/** 树节点传递过来的是否叶子节点 */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId;
	/** 树节点传递过来的action名称 */
	private String action;
	/** 内容状态 */
	private Integer status = Integer.valueOf(1);

	private InformationService informationService;
	private UserService userService;
	private SiteFileService siteFileService;
	/*@Autowired
	private TabNewsService tabNewsService;
	@Autowired
	private ImagesDao imagesDao;
	@Autowired
	private ZmNewsService zmNewsService;*/
	@Autowired
	private ContentService contentService;
	
	/*
	public void importNews() {//导入清远数据
		List<TabNews> tn=tabNewsService.findByAll();
		List<QyfyImg> qyimg=new ArrayList<QyfyImg>();
		String content="";
		String dis="";
		String[] ss=null;
		Information i=null;
		QyfyImg qy=null;
		List<QyfyImgs> imgsq=null;
		for (TabNews t : tn) {
			content=HtmlRegexpUtil.qj2bj(t.getRemark());
			String[] dd=FilePathUtils.getContentFile(content);
			qy=new QyfyImg();
			if(dd!=null&&dd.length>0) {
    		    ss=new String[dd.length];
    		    imgsq=new ArrayList<QyfyImgs>();
    			for (int j = 0; j < dd.length; j++) {
					ss[j]=UploadUtils.generateFilenameQyfy("/hosp/27",FilenameUtils.getExtension(dd[j]));
					content=StringUtils.replaceStr(content, ss[j], dd[j]);
					imgsq.add(new QyfyImgs(ss[j],dd[j]));
				}
			}
			dis=HtmlRegexpUtil.filterHtml(content);
			if(!StringUtils.isBlank(dis)) {
				if(dis.length()>=255)
					dis=dis.substring(0, 255);
			}
			i=new Information();
			i.setActionName("information_c");
			if(t.getClassName().equals("医院动态")||t.getClassName().equals("工作落实年正风在行动")||t.getClassName().equals("医疗信息")) {
				i.setChannelId(291);
			}else if (t.getClassName().equals("医疗法规")) {
				i.setChannelId(12245);
			}
			i.setAuthor("清远妇幼");
			i.setContent(content);
			i.setContentStatus(3);
			i.setCreater(ApplicationUtils.getUser());
			i.setCreateTime(t.getAddDate());
			i.setDescription(dis);
			i.setDisable(true);
		    i.setHasImg(0);
			i.setKeywords(t.getTitle());
			i.setSource(false);
			i.setTitle(t.getTitle());
			i.setPublishTime(new Date());
			i.setCreateDate(t.getAddDate());
			i.setHitss(t.getReadCount());
			informationService.save(i, false);
			if(qy!=null&&dd!=null&&dd.length>0) {
				qy.setEntityId(i.getId());
				qy.setEntityName(getEN(i));
				qy.setPath(imgsq);
				qyimg.add(qy);
			}
		}
		List<Images> images=new ArrayList<Images>();
		Images img=null;
		for (QyfyImg q : qyimg) {
			for (QyfyImgs qyfyImgs : q.getPath()) {
				img=new Images();
				img.setEntityName(entityName);
				img.setCreateTime(new Date());
				img.setEditor(true);
				img.setEntityId(id);
				img.setImagesPath(qyfyImgs.getNewUrl());
				img.setImportImgpath(qyfyImgs.getSourUrl());
				img.setSiteId(ApplicationUtils.getSite().getId());
				img.setImagesName(FilenameUtils.getName(qyfyImgs.getNewUrl()));
				img.setFileSize(FtpToolUtils.getFileSize(qyfyImgs.getSourUrl(), FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP)),true));
				images.add(img);
			}
		}
		imagesDao.saveQYFYToFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP), images);
	}
	*/
	
	private Integer checkChannel(ZmNews t) {
		/*if(t.getChannelName().equals("医院新闻")||t.getChannelName().equals("医院资讯"))
			return 12387;
		else if(t.getChannelName().equals("护理动态"))
			return 12748;
		else if(t.getChannelName().equals("天使情怀"))
			return 12749;
		else if(t.getChannelName().equals("优质护理"))
			return 12750;
		else if(t.getChannelName().equals("生活浪花"))
			return 12751;
		else if(t.getChannelName().equals("健康教育"))
			return 12752;
		else if(t.getChannelName().equals("健康宣教"))
			return 12390;
		else if(t.getChannelName().equals("医疗常识"))
			return 12746;
		else if(t.getChannelName().equals("保健知识"))
			return 12747;
		else if(t.getChannelName().equals("学术动态"))
			return 12388;
		else if(t.getChannelName().equals("医学进展"))
			return 12745;*/
		if(t.getChannelName().equals("护理动态"))
			return 12389;
		else if(t.getChannelName().equals("天使情怀"))
			return 12741;
		else if(t.getChannelName().equals("优质护理"))
			return 12742;
		else if(t.getChannelName().equals("生活浪花"))
			return 12743;
		else
			return null;
	}
	
	
	/*public void importXQNews() {//导入西樵数据
		List<ZmNews> tn=zmNewsService.findByAll(null);
		List<QyfyImg> qyimg=new ArrayList<QyfyImg>();
		List<Information> infosall=new ArrayList<Information>();
		String content="";
		String dis="";
		String[] ss=null;
		Information i=null;
		QyfyImg qy=null;
		List<QyfyImgs> imgsq=null;
		for (ZmNews t : tn) {
			if(checkChannel(t)==null)continue;
			
			content=HtmlRegexpUtil.qj2bj(t.getContent());
			String[] dd=FilePathUtils.getContentFile(content);
			qy=new QyfyImg();
			if(dd!=null&&dd.length>0) {
    		    ss=new String[dd.length];
    		    imgsq=new ArrayList<QyfyImgs>();
    			for (int j = 0; j < dd.length; j++) {
					ss[j]=UploadUtils.generateFilenameQyfy("/hosp/378",FilenameUtils.getExtension(dd[j]));
					content=StringUtils.replaceStr(content, ss[j], dd[j]);
					imgsq.add(new QyfyImgs(ss[j],dd[j],true));
				}
			}
			String infoImg="";
			if(!StringUtils.isBlank(t.getImg())) {
				infoImg=UploadUtils.generateFilenameQyfy("/hosp/378",FilenameUtils.getExtension(t.getImg()));
				boolean move=true;
				for (int j = 0; j < dd.length; j++) {
					if(dd[j].equals(t.getImg())) {
						infoImg=ss[j];
						move=false;
					}
				}
				if(move)
					imgsq.add(new QyfyImgs(infoImg,t.getImg(),false));
			}
			
			dis=HtmlRegexpUtil.filterHtml(content);
			if(!StringUtils.isBlank(dis)) {
				if(dis.length()>=255)
					dis=dis.substring(0, 255);
			}
			i=new Information();
			i.setActionName("information_c");
			i.setChannelId(checkChannel(t));//获取栏目ID
			i.setInfoImg(infoImg);
			
			i.setAuthor("南海区第四人民医院");
			i.setContent(content);
			i.setContentStatus(3);
			i.setCreater(ApplicationUtils.getUser());
			i.setCreateTime(t.getAddDate());
			i.setDescription(dis);
			i.setDisable(true);
		    i.setHasImg(0);
			i.setKeywords(t.getTitle());
			i.setSource(false);
			i.setTitle(t.getTitle());
			i.setPublishTime(t.getAddDate());
			i.setCreateDate(t.getAddDate());
			informationService.save(i, false);
			infosall.add(i);
			if(qy!=null&&dd!=null&&dd.length>0) {
				qy.setEntityId(i.getId());
				qy.setEntityName(getEN(i));
				qy.setPath(imgsq);
				qyimg.add(qy);
			}
		}
		List<Images> images=new ArrayList<Images>();
		Images img=null;
		for (QyfyImg q : qyimg) {
			for (QyfyImgs qyfyImgs : q.getPath()) {
				img=new Images();
				img.setEntityName(entityName);
				img.setCreateTime(new Date());
				img.setEditor(qyfyImgs.isEditer());
				img.setEntityId(q.getEntityId());
				img.setImagesPath(qyfyImgs.getNewUrl());
				img.setImportImgpath(qyfyImgs.getSourUrl());
				img.setSiteId(ApplicationUtils.getSite().getId());
				img.setImagesName(FilenameUtils.getName(qyfyImgs.getNewUrl()));
				img.setFileSize(FtpToolUtils.getFileSize(qyfyImgs.getSourUrl(), FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP)),true));
				images.add(img);
			}
		}
		imagesDao.saveQYFYToFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP), images);
		
		String logs="共导入数据【"+infosall.size()+"】条数据，导入图片【"+images.size()+"】张\r\n";
		for (Information ii : infosall) {
			logs+="标题【"+ ii.getTitle()+"】\r\n";
		}
		logs+="图片路径记录";
		for (Images im : images) {
			logs+="ID【"+im.getEntityId()+"】,新路径【"+im.getImagesPath()+"】,原路径【"+im.getImportImgpath()+ "】\r\n";
		}
		logs(logs);
	}
	
	private void logs(String a) {
		try {
			FileReader reader = new FileReader("E:\\2012医联网\\西樵医院\\ImportLog.txt");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			String realString = "";
			while ((s = br.readLine()) != null) {
				realString += s.toString() + "\r\n";
			}
			br.close();
			reader.close();
			FileWriter fileWriter = new FileWriter("E:\\2012医联网\\西樵医院\\ImportLog.txt");
			fileWriter.write(realString + a);
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	public String edit() {
		if (isEdit && id > 0) {
			information =new Information();
			BeanUtils.copyProperties(informationService.get(id),information);
			information.setContent(FilePathUtils.setEditorOutPath(information.getContent()));
		}
		else {
			information = createInformation();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			information =new Information();
			BeanUtils.copyProperties(informationService.get(id),information);
			information.setContent(FilePathUtils.setEditorOutPath(information.getContent()));
		}
		return SHOW;
	}

	public Information createInformation() {
		Information info = new Information();
		info.setDisable(true);
		info.setCreateTime(new Date());
		info.setPublishTime(info.getCreateTime());
		info.setCreater(ApplicationUtils.getUser());
		info.setTitleColor(ElianCodes.COLOR_BLACK);
		return info;
	}
	
	public void validateSave() {
		if (ValidateUtils.isBlank(information.getTitle()))
			this.addFieldError("information.title", "标题不能为空");
		else if (ValidateUtils.isLengOut(information.getTitle(), 50))
			this.addFieldError("information.title", "标题必须在50字以内");
		if (ValidateUtils.isLengOut(information.getSubTitle(), 100))
			this.addFieldError("information.subTitle", "副标题必须在100字以内");
		if (ValidateUtils.isLengOut(information.getKeywords(), 100))
			this.addFieldError("information.keywords", "关键字必须在100字以内");
		if (ValidateUtils.isLengOut(information.getDescription(), 255))
			this.addFieldError("information.description", "摘要必须在255字以内");
		if (ValidateUtils.isLengOut(information.getSourceName(), 50))
			this.addFieldError("information.sourceName", "来源名称必须在50字以内");
		if (ValidateUtils.isLengOut(information.getSourceUrl(), 255))
			this.addFieldError("information.sourceUrl", "来源URL必须在255字以内");
		if (ValidateUtils.isLengOut(information.getAuthor(), 50))
			this.addFieldError("information.author", "作者必须在50字以内");
		if (ValidateUtils.isBlank(information.getContent()))
			this.addFieldError("information.content", "内容不能为空");
		else if (ValidateUtils.isLengOut(information.getContent(), 7800))
			this.addFieldError("information.content", "内容必须在7800字以内");
	}

	public String save() {
		if (information.getCreater().getId() != null) {
			information.setCreater(userService.get(information.getCreater().getId()));
		}
		setDefaultDescription(information);
		information.setActionName(action);
		information.setChannelId(channelId);
		information.setContentStatus(status);
		information.setContent(FilePathUtils.getConContext(information.getContent()));
		Integer controlId=0;
		if(!isPublish())
		  informationService.save(information, isEdit);
		else
			controlId= informationService.save(information, isEdit, isPublish());
		if(controlId!=0)
		  setControlId(controlId);
		siteFileService.saveConContext(information, getEditorPrevImg(), information.getContent());
		siteFileService.saveFileToFtp(information, getPrevFile(), information.getInfoImg(),information.getFilePath());
		return SAVE;
	}
	
	public void setDefaultDescription(Information information) {
		if (StringUtils.isBlank(information.getDescription())) {
			String content = information.getContent();
			String str = StringUtils.replaceHtml(content);
			if (str.length() > 255) {
				information.setDescription(str.substring(0, 255));
			}
			else {
				information.setDescription(str);
			}
		}
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<Information> infoList = informationService.findByContentId(idList,ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(infoList)) {
			List<Content> contents=contentService.get(idList);
			if(!CollectionUtils.isEmpty(contents)) {
				contentService.delete(contents);
			}
			return;
		}
		for (Information information : infoList) {
			informationService.delete(information);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}
	
    @Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setInformationService(InformationService informationService) {
		this.informationService = informationService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
