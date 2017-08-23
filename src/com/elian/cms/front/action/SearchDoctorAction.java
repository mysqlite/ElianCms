package com.elian.cms.front.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

/**
 * 搜索资讯Action
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SearchDoctorAction extends BaseAction implements Serializable {
	private static final long serialVersionUID = -1665395475626644764L;
	/** 搜索条件 */
	private String type;// 搜索选项
	private String keyword;// 关键字
	private Integer siteId=null;//站点id
	private Pagination<Doctor> pagination=new Pagination<Doctor>();
	private List<String> pathList=new ArrayList<String>(10);
	private List<Integer> siteIdList=new ArrayList<Integer>(10);
	
	private DoctorService doctorService=null;
	private ContentService contentService=null;
	private SiteService siteService=null;
	
	public String list() {
		return LIST;
	}
	
	public String search() {
		String criteria=createCriteria();
		doctorService.search(criteria,pagination);
		if(!CollectionUtils.isEmpty(pagination.getList())){
			for(Doctor d:pagination.getList()){
				Site s=siteService.findByByComp(ElianUtils.COMP_TYPE_HOSP, d.getDept().getHospital().getId());
				Content content=contentService.getByEntityId(s, Doctor.class, d.getId());
				if(content!=null){
					Channel channel=content.getChannel();
					String path="http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT+s.getId()+
					channel.getPath()+ElianCodes.SPRIT+content.getId()+ElianCodes.SUFFIX_SHTML;
					pathList.add(path);
				}else
					pathList.add("#");
				siteIdList.add(s.getId());
			}
		}
		return LIST;
	}

	private String createCriteria() {
		StringBuilder criteria=new StringBuilder();
		if(StringUtils.isBlank(keyword)) return "and 1=0";
		if(StringUtils.isNotBlank(type)){
			if(type.equals("doctName"))
				criteria .append(" AND D.DOCT_NAME LIKE ('%").append(keyword).append("%')");
			else if(type.equals("speciality"))
				criteria .append(" AND D.SPECIALITY LIKE ('%").append(keyword).append("%')");
		}else{
			criteria .append(" AND (D.DOCT_NAME LIKE ('%").append(keyword).append("%')");
			criteria .append(" OR D.SPECIALITY LIKE ('%").append(keyword).append("%'))");
		}
		criteria.append(" and exists( select 1 from t_control c  where c.table_key_id= d.doct_id and ");
		criteria.append(" 	((c.status = 3 and c.static_status =1) or (c.status=2 and c.static_status=2))");
		criteria.append("and exists ");
		criteria.append("              (SELECT 1 ");
		criteria.append("                 FROM T_channel t where  C.SITE_ID = t.SITE_ID and c.channel_id=t.channel_id and t.static_path is not NULL) ");
		criteria.append("AND EXISTS ");
		if(siteId==null){
			criteria.append("              (SELECT 1 ");
			criteria.append("                 FROM T_SITE S ");
			criteria.append("                WHERE     C.SITE_ID = S.SITE_ID ");
			criteria.append("                      AND COM_TYPE = 'hospital' ");
			criteria.append("                      AND EXISTS ");
			criteria.append("                             (SELECT 1 ");
			criteria.append("                                FROM T_HOSPITAL H ");
			criteria.append("                                     INNER JOIN T_HOSP_DEPARTMENT HD ");
			criteria.append("                                        ON HD.HOSP_ID = H.HOSP_ID ");
			criteria.append("                                     INNER JOIN T_DOCTOR D1 ");
			criteria.append("                                        ON HD.DEPA_ID = D1.DEPA_ID ");
			criteria.append("                               WHERE     S.COM_ID = H.HOSP_ID ");
			criteria.append("                                     AND D1.DOCT_id=d.DOCT_id)) ");
		}else{
			criteria.append("              (SELECT 1 ");
			criteria.append("                 FROM T_SITE S ");
			criteria.append("                WHERE     C.SITE_ID = S.SITE_ID ");
			criteria.append("                      AND S.SITE_ID="+siteId+")");
		}
		criteria.append("	)");
		return criteria.toString();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Pagination<Doctor> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Doctor> pagination) {
		this.pagination = pagination;
	}
	
	public List<String> getPathList() {
		return pathList;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

	public List<Integer> getSiteIdList() {
		return siteIdList;
	}

	public void setSiteIdList(List<Integer> siteIdList) {
		this.siteIdList = siteIdList;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public String getBasePath(){
		return "http://hosp"+FreemarkerCodes.DOMAIN_SUFFIX+ElianCodes.SPRIT;
	}
}
