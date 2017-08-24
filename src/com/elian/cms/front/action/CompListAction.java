package com.elian.cms.front.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;
import com.elian.cms.syst.util.StringUtils;

/**
 * 栏目功能
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class CompListAction {
	private Pagination<Content> pagination = new Pagination<Content>();
	private Pagination<Instrument> paginationInstrument=new Pagination<Instrument>("paginationInstrument");
	private Pagination<Medicine> paginationMedicine=new Pagination<Medicine>("paginationMedicine");
	/** 每页行数 */
	private int rowSize = 10;
	/** 当前页码 */
	private int pageNo = 1;

	private Integer channelId;
	private Integer siteId;
	private String path;
	private Integer typeId;
	private ContentService contentService;
	private MedicineService medicineService;
	private InstrumentService instrumentService;
	private TypeService typeService;
	private DeptTypeService deptTypeService;
	private CompanyService companyService;
	private SiteService siteService;
	
	public void list() {
		setPagination();
		JSONObject obj = new JSONObject();
		obj.put("data", ApplicationUtils.jsonList(pagination.getList(), "id",
				"title", "createTime"));
		obj.put("page", pagination.getPageInfo());
		StringBuffer sb = new StringBuffer();
		String cb = ApplicationUtils.getRequest().getParameter("callback");
		if (cb != null) {// 如果是跨域
			sb.append(cb);
			sb.append("(");
			sb.append(obj.toString());
			sb.append(")");
		}
		else {
			sb.append(obj.toString());
		}
		ApplicationUtils.sendJsonStr(sb.toString());
	}

	public void medicine() {
		setPagination();
		JSONObject obj = new JSONObject();
		List<Content> list = null;
		if ((list = pagination.getList()) != null) {
			obj.put("content", ApplicationUtils.jsonList(pagination.getList(),
					"id", "title"));
			Iterator<Content> its = list.iterator();
			Content con = null;
			Medicine medicine = null;
			List<Medicine> medList = new ArrayList<Medicine>();
			while (its.hasNext()) {
				con = its.next();
				medicine = new Medicine();
				BeanUtils.copyProperties(medicineService.get(con.getEntityId()), medicine);
				medicine.setStaticPath(path + ElianCodes.SPRIT + con.getId()+ ElianCodes.SUFFIX_SHTML);
				medList.add(medicine);
			}
			HibernateEagerLoadingUtil.eagerLoadFiled(medList);
			obj.put("medList", medList);
			obj.put("page", pagination.getPageInfo());
		}
		ApplicationUtils.sendJsonpObj(obj);
	}

	public void medicineByType() {
		paginationMedicine.setRowSize(rowSize);
		paginationMedicine.setPageNo(pageNo);
		Company comp=null;
		Site site=null;
		if(siteId!=null)
		   site=siteService.get(siteId);
		if(site!=null) {
			 comp=companyService.get(site.getComId());
		}
		List<Medicine> medicneList=medicineService.findByAll(comp!=null?comp.getId():0,typeId, paginationMedicine);
		List<Content> contentList=null;
		String paths="";
		Content content=null;
		if(!CollectionUtils.isEmpty(medicneList)) {
    		for (Medicine med : medicneList) {
    			contentList=contentService.findByIdAndAction(med.getId(), StringUtils.getENL(med)+"_c",siteId);
    			if(!CollectionUtils.isEmpty(contentList)) {
    				content=contentList.get(0);
    				if(content!=null&&siteId!=null) {
    					paths= ElianCodes.SPRIT+siteId+content.getChannel().getPath()+ ElianCodes.SPRIT+content.getId()+ElianCodes.SUFFIX_SHTML;
    				}
    			}
    			med.setStaticPath(paths);
    		}
		}
		JSONObject obj = new JSONObject();
		HibernateEagerLoadingUtil.eagerLoadFiled(medicneList);
		obj.put("medList", medicneList);
		obj.put("page", paginationMedicine.getPageInfo());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	/**
	 * 获取所有药品
	 */
	public void getAllMedicine() {
		this.paginationMedicine.setRowSize(this.rowSize);
		this.paginationMedicine.setPageNo(this.pageNo);
		
		List<Medicine> medicneList = medicineService.findByAll(null, null, paginationMedicine);

		if (!CollectionUtils.isEmpty(medicneList)) {
			for (Medicine med : medicneList) {
				Company comp = med.getCompany();
				Site site = null;
				String paths = "";
				if ( null != comp && null != comp.getId()) {
					Company tempComp = companyService.get(comp.getId());
					site = (Site) this.siteService.findByByComp(tempComp.getType(), tempComp.getId());
				}
				if (null != site && null != site.getId()) {
					List<Content> contentList = contentService.findByIdAndAction(med.getId(), StringUtils.getENL(med) + "_c", site.getId());
					if (!CollectionUtils.isEmpty(contentList)) {
						Content content = (Content) contentList.get(0);
						if (content != null) {
							paths = "/" + site.getId() + content.getChannel().getPath() + "/" + content.getId() + ".shtml";
						}
					}
					med.setStaticPath(paths);
					
					ApplicationUtils.setSite(site);
					med.setFrontDesc(med.getFrontDesc());

					Company company = new Company();
					comp.setFrontIntroduce(comp.getFrontIntroduce());
					try {
						org.apache.commons.beanutils.BeanUtils.copyProperties(company, comp);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					company.setFrontIntroduce(company.getFrontIntroduce());
					med.setCompany(company);
					
					User user = new User();
					try {
						org.apache.commons.beanutils.BeanUtils.copyProperties(user, med.getUser());
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					med.setUser(user);
					
					Type type = new Type();
					try {
						org.apache.commons.beanutils.BeanUtils.copyProperties(type, med.getType());
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					med.setType(type);
				}
				/*med.setCompany(null);
				med.setUser(null);
				med.setType(null);*/
			}
		}
		

		JSONObject obj = new JSONObject();
		// HibernateEagerLoadingUtil.eagerLoadFiled(medicneList);
		obj.put("medList", medicneList);
		// obj.put("page", this.paginationMedicine.getPageInfo());
		ApplicationUtils.sendJsonpObj(obj);
	}

	public void medicineType() {
		Company comp=null;
		Site site=null;
		if(siteId!=null)
		   site=siteService.get(siteId);
		if(site!=null) {
			 comp=companyService.get(site.getComId());
		}
		List<Type> typeList=typeService.findMedicineType("medicine_type", comp!=null?comp.getId():null);
		JSONObject obj = new JSONObject();
		obj.put("type", typeList);
		ApplicationUtils.sendJsonpObj(obj);
	}

	public void instrument() {
		setPagination();
		JSONObject obj = new JSONObject();
		List<Content> list = null;
		if ((list = pagination.getList()) != null) {
			obj.put("content", ApplicationUtils.jsonList(pagination.getList(),
					"id", "title"));
			Iterator<Content> its = list.iterator();
			Content con = null;
			Instrument instrument = null;
			List<Instrument> insList = new ArrayList<Instrument>();
			while (its.hasNext()) {
				con = its.next();
				instrument = new Instrument();
				BeanUtils.copyProperties(instrumentService.get(con
						.getEntityId()), instrument);
				instrument.setStaticPath(path + ElianCodes.SPRIT + con.getId()
						+ ElianCodes.SUFFIX_SHTML);
				insList.add(instrument);
			}
			HibernateEagerLoadingUtil.eagerLoadFiled(insList);
			obj.put("insList", insList);
			obj.put("page", pagination.getPageInfo());
		}
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void instrumentByType() {
		paginationInstrument.setRowSize(rowSize);
		paginationInstrument.setPageNo(pageNo);
		Company comp=null;
		Site site=null;
		if(siteId!=null)
		   site=siteService.get(siteId);
		if(site!=null) {
			 comp=companyService.get(site.getComId());
		}
		List<Instrument> instrumentList=instrumentService.findByAll(comp!=null?comp.getId():0,typeId, paginationInstrument);
		List<Content> contentList=null;
		String paths="";
		Content content=null;
		if(!CollectionUtils.isEmpty(instrumentList)) {
    		for (Instrument ins : instrumentList) {
    			contentList=contentService.findByIdAndAction(ins.getId(), StringUtils.getENL(ins)+"_c",siteId);
    			if(!CollectionUtils.isEmpty(contentList)) {
    				content=contentList.get(0);
    				if(content!=null&&siteId!=null) {
    					paths= ElianCodes.SPRIT+siteId+content.getChannel().getPath()+ ElianCodes.SPRIT+content.getId()+ElianCodes.SUFFIX_SHTML;
    				}
    			}
    			ins.setStaticPath(paths);
    		}
		}
		JSONObject obj = new JSONObject();
		HibernateEagerLoadingUtil.eagerLoadFiled(instrumentList);
		obj.put("insList", instrumentList);
		obj.put("page", paginationInstrument.getPageInfo());
		ApplicationUtils.sendJsonpObj(obj);
	}

	public void instrumentType() {
		Company comp=null;
		Site site=null;
		if(siteId!=null)
		   site=siteService.get(siteId);
		if(site!=null) {
			 comp=companyService.get(site.getComId());
		}
		List<DeptType> typeList=deptTypeService.findInstrumentType("instruType", comp!=null?comp.getId():null);
		JSONObject obj = new JSONObject();
		obj.put("type", typeList);
		ApplicationUtils.sendJsonpObj(obj);
	}

	private void setPagination() {
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		contentService.findForListStaticPage(channelId, siteId, pagination);
	}

	public void information() {
		list();
	}

	public void invitation() {
		list();
	}

	public void bidding() {
		list();
	}

	public void job() {
		list();
	}

	public void purchase() {
		list();
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Pagination<Content> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Content> pagination) {
		this.pagination = pagination;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public Pagination<Instrument> getPaginationInstrument() {
		return paginationInstrument;
	}

	public void setPaginationInstrument(Pagination<Instrument> paginationInstrument) {
		this.paginationInstrument = paginationInstrument;
	}
	
	public Pagination<Medicine> getPaginationMedicine() {
		return paginationMedicine;
	}

	public void setPaginationMedicine(Pagination<Medicine> paginationMedicine) {
		this.paginationMedicine = paginationMedicine;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
