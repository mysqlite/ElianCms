package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 企业药品Action
 * 
 * @author Gechuanyi
 * @version {0.1,2013-04-17} 企业药品基本CURD
 */
@Component
@Scope("prototype")
public class MedicineAction extends BaseAction {
	private static final long serialVersionUID = 5899143465462000669L;
	private Pagination<Medicine> pagination = new Pagination<Medicine>(
			SearchParamUtils.getMedicineConditionMap());
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
	/** 是否ztree展示 */
	private boolean ztree;
	/**多图片数组*/
	private String[] medImgs;
	
	private Medicine medicine;
	private MedicineService medicineService;
	private TypeService typeService;
	private SiteFileService siteFileService;
	private CompanyService companyService;

	private List<Type> typeList;
	private Integer compId;

	public String trees() {
		if (ApplicationUtils.isCompany()) {
			return list();
		}
		return "tree";
	}

	public String edit() {
		typeList = typeService.findByType(true, "medicine_type");
		if (id != null && id > 0) {
			medicine =new Medicine();
			BeanUtils.copyProperties(medicineService.get(id),medicine);
			medicine.setDescription(FilePathUtils.setEditorOutPath(medicine.getDescription()));
		}
		else {
			createMedicine();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			medicine =new Medicine();
			BeanUtils.copyProperties(medicineService.get(id),medicine);
			medicine.setDescription(FilePathUtils.setEditorOutPath(medicine.getDescription()));
			typeList = typeService.findByType(true, "medicine_type");
		}
		return SHOW;
	}

	private Medicine createMedicine() {
		medicine = new Medicine();
		medicine.setSort(99);
		medicine.setDiscountedPrice(false);
		medicine.setDisable(false);
		medicine.setCreateTime(new Date());
		medicine.setUser(ApplicationUtils.getUser());
		medicine.setCompany(ApplicationUtils.getCompany() != null ? ApplicationUtils.getCompany()
						: companyService.get(compId));
		return medicine;
	}

	public String list() {
		medicineService.findByAll(
				ApplicationUtils.getCompany() != null ? ApplicationUtils
						.getCompany().getId() : compId, pagination);
		return LIST;
	}

	public void validateSave() {
		if (!ValidateUtils.strLenthAll(medicine.getCnName(), 1, 100)) {
			this.addFieldError("cnName", "中文名称长度为1-100字");
		}
		if (ValidateUtils.isLengOut(medicine.getEnName(),  100)) {
			this.addFieldError("enName", "英文名称长度为1-100字");
		}
		if (!ValidateUtils.isMedicineNo(medicine.getApprovalNumber())) {
			this.addFieldError("approvalNumber", "批准文号不符合规范");
		}
		if (ValidateUtils.isLengOut(medicine.getSpecification(), 255)) {
			this.addFieldError("specification", "规格长度在255字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getComposition(), 500)) {
			this.addFieldError("composition", "主要成分在500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getAttendingFunctions(), 7500)) {
			this.addFieldError("attendingFunctions", "功能主治长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getAdverseReaction(), 7500)) {
			this.addFieldError("adverseReaction", "不良反应长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getContraindication(), 7500)) {
			this.addFieldError("contraindication", "禁忌长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getUsageDosage(), 7500)) {
			this.addFieldError("usageDosage", "用法用量长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getStorageMethod(), 100)) {
			this.addFieldError("storageMethod", "贮藏方法长度在100字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getAttentions(), 7500)) {
			this.addFieldError("attentions", "注意事项长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getEffectCategory(), 500)) {
			this.addFieldError("effectCategory", "作用类别长度在500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getDrugInteractions(), 7500)) {
			this.addFieldError("drugInteractions", "药物相互作用长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getPharmacologicalEffects(), 7500)) {
			this.addFieldError("pharmacologicalEffects", "药理作用长度在7500字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getMedicineImg(), 255)) {
			this.addFieldError("medicineImg", "药品图片长度在255字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getAlias(), 100)) {
			this.addFieldError("alias", "别名长度在100字以内");
		}
		if (!ValidateUtils.isINTEGER_NEGATIVE(medicine.getPrice())) {
			this.addFieldError("pricePany", "请输入有效正数");
		}
		if (medicine.isDiscountedPrice()
				|| medicine.getDiscountedPrices() != null) {
			if (!ValidateUtils.isINTEGER_NEGATIVE(medicine
					.getDiscountedPrices())) {
				this.addFieldError("discountedPricePany", "请输入有效正数");
			}
		}
		if (!ValidateUtils.isINTEGER_NEGATIVE(medicine.getSort())) {
			this.addFieldError("sort", "请输入正整数");
		}
		if (medicine.getType() == null || medicine.getType().getId() == null) {
			this.addFieldError("type", "请选择药品类型");
		}
		if (!ValidateUtils.strLenthAll(medicine.getDosage(), 1, 30)) {
			this.addFieldError("dosage", "请输入剂型，30字以内");
		}
		if (!ValidateUtils.strLenthAll(medicine.getMedicineUnit(), 1, 10)) {
			this.addFieldError("medicineUnit", "请输入单位，10字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getSummary(), 255)) {
			this.addFieldError("summary", "摘要内容在255字以内");
		}
		if (ValidateUtils.isLengOut(medicine.getDescription(), 7800)) {
			this.addFieldError("discription", "描述内容在7800字以内");
		}
		if (this.hasErrors()) {
			typeList = typeService.findByType(true, "medicine_type");
			medicine.setUser(ApplicationUtils.getUser());
		}

	}

	public String save() {
		medicine.setUser(ApplicationUtils.getUser());
		medicine.setType(typeService.get(medicine.getType().getId()));
		medicine.setDescription(FilePathUtils.getConContext(medicine.getDescription()));
		if (compId != null) {
			medicine.setCompany(companyService.get(compId));
		}
		else if (medicine.getCompany().getId() != null) {
			medicine.setCompany(companyService.get(medicine.getCompany().getId()));
		}
		medicine.setMedicineImg(FilePathUtils.strArrayToStr(medImgs));
		if(isPublish()) {
			medicineService.save(medicine,isEdit,isPublish());
		}
		else {
			medicineService.save(medicine,isEdit);
		}
		siteFileService.saveConContext(medicine,getEditorPrevImg(), medicine.getDescription());
		siteFileService.saveFileToFtp(medicine,getPrevFile(), medImgs);
		return SAVE;
	}

	/**
	 * 内容界面delete方法，删除数据的时候只删除内容表的数据
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		medicineService.delete(null, idList);
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			medicine = medicineService.get(id);
			if (medicine == null)
				return NONE;
			medicine.setDisable(!disable);
			medicineService.save(medicine);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			medicine = medicineService.get(id);
			if (medicine == null)
				return NONE;
			medicine.setSort(sort);
			medicineService.save(medicine);
		}
		return NONE;
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				medicine = medicineService.get(id);
				medicine.setDisable(!medicine.isDisable());
				medicineService.save(medicine);
			}
	}

	public void deleteMedicine() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		Integer ids = ApplicationUtils.getAjaxId();
		if (!CollectionUtils.isEmpty(idList)) {
			for (Integer id : idList) {
				medicine = medicineService.get(id);
				medicineService.delete(medicine, null);
			}
		}
		else if (ids != null && ids > 0) {
			medicine = medicineService.get(id);
			medicineService.delete(medicine, null);
		}
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Pagination<Medicine> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Medicine> pagination) {
		this.pagination = pagination;
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

	public boolean isZtree() {
		return ztree;
	}

	public void setZtree(boolean ztree) {
		this.ztree = ztree;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public Integer getCompId() {
		return compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	public String[] getMedImgs() {
		return medImgs;
	}

	public void setMedImgs(String[] medImgs) {
		this.medImgs = medImgs;
	}
}
