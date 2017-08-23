package com.elian.cms.reg.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.DoctCommentUser;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.admin.service.DoctCommentUserService;
import com.elian.cms.admin.service.DoctorCommentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.DoubleUtlis;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.HibernateEagerLoadingUtil;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;
/**
 * 
 * @author thy
 *
 */
@Component
@Scope("prototype")
public class RegDoctorAction extends BaseAction{
	private static final long serialVersionUID = -9059495469917566571L;

	private Integer subAreaCode;
	
	private Integer hospId=0;
	private Integer deptId=null;
	private Integer docId=null;
	
	//以下是搜索条件
	private String doctorName="";
	private String doctorSpeciality="";
	private Integer searchHospId=null;
	private Integer searchDeptId=null;
	private String searchDeptName="";
	private Integer day=0;
	private Integer regType=null;
	private Integer hosptialRank=null;
	
	//标识是不是快速挂号(不提供get，set方法)
	private boolean isReg=false;
	
	private List<Hospital> hospList=null;
	
	private Hospital hospital=null;
	private Doctor doctor=null;
	private DoctorComment docComment;
	private Integer[] score;
	/** 治疗效果评分 */
	private double cureScore=0;
	/** 服务态度评分 */
	private double serveScore=0;
	/** 医德评分 */
	private double ethiceScore=0;
	/** 综合评分 */
	private double aveScore=0;
	private Pagination<DoctorComment> paginationComment = new Pagination<DoctorComment>("paginationComment");
	private HospitalService hospitalService=null;
	private DepartmentService departmentService=null;
	private DoctorService doctorService=null;
	private TypeService typeService=null;
	private DoctorCommentService doctorCommentService;
	private UserService userService;
	private RegSideBarData regSideBarData;
	private UserRegisterService userRegisterService;
	private DoctCommentUserService doctCommentUserService;
	
	private UserRegister userRegister;
	private List<HospitalNoScore> noScoreList;
	private List<Doctor> docList=null;
	private List<Department> deptList=null;
	private List<SelectItem> scoreList;
	private Pagination<Doctor> pagination=new Pagination<Doctor>();
	private final String SEARCH="search";
	private final String REGSEARCH="regSearch";
	private List<SelectItem> sexList=null;	
	//停诊通知数据
	private List<DoctorWork> doctorWorkList=null;
	private Integer areaId= (Integer) ApplicationUtils.getSession().get("areaId");
	private Integer regId;
	
	public String search(){
		String criteria=createCriteria();
		//if(StringUtils.isBlank(criteria)) return SEARCH;
		doctorService.search(criteria,pagination);
		List<DoctorComment> docCommenList=null;
		if(!CollectionUtils.isEmpty(pagination.getList())){
			for (Doctor d : pagination.getList()) {
				aveScore=0;
				docCommenList=doctorCommentService.findByPageOrAll(null, d.getId(), null,null);
				if(!CollectionUtils.isEmpty(docCommenList)) {
					for (DoctorComment dc : docCommenList) {
						aveScore=dc.getAveScore()*2+aveScore;
					}
					aveScore=DoubleUtlis.div((double)aveScore,(double)docCommenList.size(),1) ;
				}
				d.setAvgScore(aveScore);
			}
		}
		return SEARCH;
	}
	
	public String regSearch(){
		isReg=true;
		search();
		return REGSEARCH;
	}
	
	public void searchJson(){
		isReg=true;
		if(StringUtils.isNotBlank(doctorName)){
			try {
				doctorName=URLDecoder.decode(doctorName, "utf-8");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		String criteria=createCriteria();
		doctorService.search(criteria,pagination);
		List<DoctorComment> docCommenList=null;
		if(!CollectionUtils.isEmpty(pagination.getList())){
			for (Doctor d : pagination.getList()) {
				aveScore=0;
				docCommenList=doctorCommentService.findByPageOrAll(null, d.getId(),null, null);
				if(!CollectionUtils.isEmpty(docCommenList)) {
					for (DoctorComment dc : docCommenList) {
						aveScore=dc.getAveScore()*2+aveScore;
					}
					aveScore=DoubleUtlis.div((double)aveScore,(double)docCommenList.size(),1) ;
				}
				d.setAvgScore(aveScore);
			}
		}
		HibernateEagerLoadingUtil.eagerLoadFiled(pagination.getList());
		JSONObject obj = new JSONObject();
		obj.put("pagi", pagination);
		obj.put("img_ftp", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	private String createCriteria() {
		StringBuilder criteria=new StringBuilder();
		if(StringUtils.isNotBlank(doctorName)){
			criteria .append(" AND D.DOCT_NAME LIKE ('%").append(doctorName).append("%')");
		}
		if(StringUtils.isNotBlank(doctorSpeciality)){
			criteria .append(" AND D.SPECIALITY LIKE ('%").append(doctorSpeciality).append("%')");
		}
		if(isReg){
			if(searchDeptId!=null){
				criteria.append("  AND D.DEPA_ID = ").append(searchDeptId);
			}else if(searchDeptName!=null){
				criteria.append("  AND EXISTS (SELECT 1 FROM T_HOSP_DEPARTMENT" +
						" DEPT WHERE DEPT.DEPA_ID=D.DEPA_ID AND DEPT.DEPA_NAME LIKE ('").append(searchDeptName).append("%'))");
			}else if(searchHospId!=null){
				criteria.append("  AND EXISTS (SELECT 1 FROM T_HOSP_DEPARTMENT" +
						" DEPT WHERE DEPT.DEPA_ID=D.DEPA_ID AND DEPT.HOSP_ID="+searchHospId+")");
			}else{
				Iterator<Hospital> it=getHospList().iterator();
				String ids="";
				while (it.hasNext()) {
					Hospital h= it.next();
					ids+=h.getId();
					if(it.hasNext())
						ids+=",";
				}
				if(StringUtils.isNotBlank(ids))
					criteria.append("  AND EXISTS (SELECT 1 FROM T_HOSP_DEPARTMENT" +
						" DEPT WHERE DEPT.DEPA_ID=D.DEPA_ID AND DEPT.HOSP_ID IN (" +ids+"))");
				else
					return " AND 1!=1 ";
			}
			if(day!=null){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
				Calendar beginDate=Calendar.getInstance();
				Calendar endDate=Calendar.getInstance();
				beginDate.set(beginDate.get(Calendar.YEAR), beginDate.get(Calendar.MONTH)
						,beginDate.get(Calendar.DAY_OF_MONTH)+(day==0?0:day-1), 0, 0, 0);
				endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)
						,endDate.get(Calendar.DAY_OF_MONTH)+(day==0?6:day-1)+1, 0, 0, 0);
				criteria.append(" AND EXISTS ( SELECT 1 FROM T_DOCTOR_WORK D_W WHERE D_W.DOCT_ID =D.DOCT_ID");
				criteria.append("		AND D_W.NO_SOURCE > 0  AND D_W.IS_WORK = 0 ");
				criteria.append(" 		AND CONVERT (VARCHAR (19),  D_W.START_TIME, 120) >='").append(df.format(beginDate.getTime())).append("'");
				criteria.append(" 		AND CONVERT (VARCHAR (19),  D_W.START_TIME, 120) <='").append(df.format(endDate.getTime())).append("'");
				if(regType!=null){
					criteria.append("		AND D_W.REG_TYPE=").append(regType);
				}
				criteria.append("	)");
			}
/*			if(regType!=null){
				criteria.append(" AND EXISTS ( SELECT 1 FROM T_DOCTOR_WORK D_W WHERE D_W.DOCT_ID =D.DOCT_ID");
				criteria.append("		AND D_W.NO_SOURCE > 0  AND D_W.IS_WORK = 0 ");
				criteria.append("		AND D_W.REG_TYPE=").append(regType);
				criteria.append("	)");
			}
*/		}
		return criteria.toString();
	}

	/**
	 * 根据科室搜索医生
	 * @return
	 */
	public String searchByDept(){
		return LIST;
	}
	
	public void searchByDeptJson(){
		JSONObject obj = new JSONObject();
		HibernateEagerLoadingUtil.eagerLoadFiled(getDeptList());
		obj.put("dept_list", getDeptList());
//		obj.put("img_ftp", ApplicationUtils.getImgFtp().getFtpUrl());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void getByDeptJson(){
		JSONObject obj = new JSONObject();
		doctorService.findByPage(pagination, hospId, deptId);
		HibernateEagerLoadingUtil.eagerLoadFiled(pagination.getList());
		obj.put("pagi", pagination);
		obj.put("img_ftp", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl());
		ApplicationUtils.sendJsonpObj(obj);
		
	}
	
	public String detial(){
		if(docId!=null&&docId>0) {
			paginationComment.setRowSize(5);
			doctorCommentService.findByPageOrAll(null, docId,null, paginationComment);
			doctor=doctorService.get(docId);
			if(paginationComment.getList()!=null) {
			 List<DoctorComment> docComList=doctorCommentService.findByPageOrAll(null, docId,null, null);
			for (DoctorComment d : docComList) {
				cureScore=d.getCureScore()*2+cureScore;
				serveScore=d.getServeScore()*2+serveScore;
				ethiceScore=d.getEthiceScore()*2+ethiceScore;
				aveScore=d.getAveScore()*2+aveScore;
			}
			cureScore=DoubleUtlis.div((double)cureScore, (double)paginationComment.getRowCount(), 1);
			serveScore=DoubleUtlis.div((double)serveScore, (double)paginationComment.getRowCount(), 1);
			ethiceScore=DoubleUtlis.div((double)ethiceScore, (double)paginationComment.getRowCount(), 1);
			aveScore=DoubleUtlis.div((double)aveScore,(double)paginationComment.getRowCount(),1) ;
			}
		}
		if(ApplicationUtils.getUser()!=null)
			ApplicationUtils.removeForwardPage();
		return SHOW;
	}
	
	public void jsonDetial(){
		if(docId!=null&&docId>0) {
			doctor=doctorService.get(docId);
			JSONObject obj = new JSONObject();
			HibernateEagerLoadingUtil.eagerLoadFiled(doctor);
			obj.put("doct", doctor);
			obj.put("img_ftp", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl());
			ApplicationUtils.sendJsonpObj(obj);	
		}
	}
	
	public String goLogin() {
		ApplicationUtils.setForwardPage(getForwardPage());
		return "login";
	}
	
	public void saveGood() {
		int result=-1;
		Integer id=ApplicationUtils.getAjaxId();
		if(id!=null&&id>0) {
    		try {
    			List<DoctCommentUser> doctcommList=doctCommentUserService.findByAll(ApplicationUtils.getUser().getId(), id);
    			if(CollectionUtils.isEmpty(doctcommList)) {
    				DoctCommentUser doctCommUser=new DoctCommentUser();
    				doctCommUser.setComment(doctorCommentService.get(id));
    				doctCommUser.setUser(ApplicationUtils.getUser());
    				doctCommentUserService.save(doctCommUser);
        			docComment=doctorCommentService.get(id);
        			docComment.setGood((docComment.getGood()!=null?docComment.getGood():0)+1);
        			doctorCommentService.save(docComment);
        			result=0;
    			}else {
    				result=1;
    			}
    		}
    		catch (Exception e) {
    			result=-1;
    		 }
    		JSONObject obj=new JSONObject();
    		obj.put("ok", result);
    		ApplicationUtils.sendJsonpObj(obj);
		}
	}
	
	public void savePoor() {
		int result=-1;
		Integer id=ApplicationUtils.getAjaxId();
		if(id!=null&&id>0) {
    		try {
    			List<DoctCommentUser> doctcommList=doctCommentUserService.findByAll(ApplicationUtils.getUser().getId(), id);
    			if(CollectionUtils.isEmpty(doctcommList)) {
    				DoctCommentUser doctCommUser=new DoctCommentUser();
    				doctCommUser.setComment(doctorCommentService.get(id));
    				doctCommUser.setUser(ApplicationUtils.getUser());
    				doctCommentUserService.save(doctCommUser);
    				docComment=doctorCommentService.get(id);
    				docComment.setPoor((docComment.getPoor()!=null?docComment.getPoor():0)+1);
    				doctorCommentService.save(docComment);
    				result=0;
    			}else {
    				result=1;
    			}
			}
			catch (Exception e) {
				result=1;
			}
			JSONObject obj=new JSONObject();
    		obj.put("ok", result);
    		ApplicationUtils.sendJsonpObj(obj);
		}
	}
	
	public String list() {
		return LIST;
	}
	
	public String save() {
	   doctorCommentService.save(createComment(docComment));
		return SAVE;
	}
	
	public void  validateSave() {
    	 if(docComment==null) {
    		 this.addFieldError("userMsg", "请填写评论内容");
    		 return;
    	 }
    	 if(docId==null) {
    		 this.addFieldError("userMsg", "当前没有选择医生");
    		 return;
    	 }
		if(StringUtils.isNotBlank(docComment.getIllness())) { 
			if(ValidateUtils.isLengOut(docComment.getIllness(), 100))
				this.addFieldError("docComment.illness", "就诊疾病名称不能超过100字");
		}
		else
			this.addFieldError("docComment.illness", "请填写就诊疾病名称");
		if(score[0]==null|score[1]==null|score[2]==null) {
			this.addFieldError("docComment.score", "请对该医生的诊疗效果、服务态度、医德进行评分");
		}
		if(ValidateUtils.isLengOut(docComment.getLeaveWords(), 2000)) {
			this.addFieldError("docComment.leaveWords", "评论内容不得超过2000字");
		}
		boolean isCommon=false;
	  gotoSave:
		if(regId==null) {
    		goSave:
    			if(ApplicationUtils.getUser()==null) {
        			String loginURL=ApplicationUtils.getPath()+"/reg/login.jsp";
        			this.addFieldError("userMsg", "要对医生进行评论，<a href='"+loginURL+"'>请点此登录</a>");
        			ApplicationUtils.setForwardPage(getForwardPage());
        		}else {
        			ApplicationUtils.removeForwardPage();
        		    List<UserRegister>	userRegisters=userRegisterService.findByDoctorId(docId, null,ApplicationUtils.getUser().getId());
                    for (UserRegister ur : userRegisters) {
                    	List<DoctorComment> doctorComments=doctorCommentService.findByPageOrAll(ApplicationUtils.getUser().getId(), docId,ur.getId(), null);
                    	if(CollectionUtils.isEmpty(doctorComments)) {
                    		regId=ur.getId();
                    		isCommon=true;
                    		break goSave;
                    	}
                    }
        		}
		}else {
			List<DoctorComment> doctorComments=doctorCommentService.findByPageOrAll(ApplicationUtils.getUser().getId(), docId,regId, null);
        	if(CollectionUtils.isEmpty(doctorComments)) {
        		isCommon=true;
        		break gotoSave;
        	}
		}
		if(!isCommon) {
			this.addFieldError("userMsg", "一次挂号一次评论，您已经对该医生本次挂号进行过评论了，或者您尚未挂号!");
		}
		if(this.hasErrors()) {
			detial();
		}
	}
	
	private DoctorComment createComment(DoctorComment comment) {
		comment.setCureScore(score[0]);
		comment.setServeScore(score[1]);
		comment.setEthiceScore(score[2]);
		if(comment.getCureScore()>0&&comment.getServeScore()>0&&comment.getEthiceScore()>0) {
    		comment.setAveScore(DoubleUtlis.divInteger(comment.getCureScore()+comment.getServeScore()+comment.getEthiceScore(), 3,0));
    		comment.setScore(true);
    		comment.setScoreTime(new Date());
	    }else {
	    	comment.setAveScore(0);
	    }
		comment.setUser(userService.get(ApplicationUtils.getUser().getId()));
		comment.setDoctor(doctorService.get(docId));
		comment.setLevaWordTime(StringUtils.isNotBlank(comment.getLeaveWords())?new Date():null);
		comment.setLeaveWord(StringUtils.isNotBlank(comment.getLeaveWords())?true:false);
		comment.setCureTime(null);//就诊时间应根据患者对应订单号去查询
		comment.setCreateTime(new Date());
		comment.setTop(false);
		comment.setRecom(false);
		comment.setCommSort(9999);
		comment.setDisable(true);
		comment.setRegister(userRegisterService.get(regId));//此处订单号应由挂号记录中查询
		return comment;
	}
	
	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSpeciality() {
		return doctorSpeciality;
	}

	public void setDoctorSpeciality(String doctorSpeciality) {
		this.doctorSpeciality = doctorSpeciality;
	}

	public Pagination<Doctor> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Doctor> pagination) {
		this.pagination = pagination;
	}

	public Integer getSearchHospId() {
		return searchHospId;
	}

	public void setSearchHospId(Integer searchHospId) {
		this.searchHospId = searchHospId;
	}

	public Integer getSearchDeptId() {
		return searchDeptId;
	}

	public void setSearchDeptId(Integer searchDeptId) {
		this.searchDeptId = searchDeptId;
	}
	
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public void setScoreList(List<SelectItem> scoreList) {
		this.scoreList = scoreList;
	}

	public DoctorComment getDocComment() {
		return docComment;
	}

	public void setDocComment(DoctorComment docComment) {
		this.docComment = docComment;
	}

	public Integer[] getScore() {
		return score;
	}

	public void setScore(Integer[] score) {
		this.score = score;
	}

	public double getCureScore() {
		return cureScore;
	}

	public void setCureScore(double cureScore) {
		this.cureScore = cureScore;
	}

	public double getServeScore() {
		return serveScore;
	}

	public void setServeScore(double serveScore) {
		this.serveScore = serveScore;
	}

	public double getEthiceScore() {
		return ethiceScore;
	}

	public void setEthiceScore(double ethiceScore) {
		this.ethiceScore = ethiceScore;
	}

	public double getAveScore() {
		return aveScore;
	}

	public void setAveScore(double aveScore) {
		this.aveScore = aveScore;
	}

	public Integer getSubAreaCode() {
		return subAreaCode;
	}

	public void setSubAreaCode(Integer subAreaCode) {
		this.subAreaCode = subAreaCode;
	}

	public Integer getHosptialRank() {
		return hosptialRank;
	}

	public void setHosptialRank(Integer hosptialRank) {
		this.hosptialRank = hosptialRank;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@Resource	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
		
	@Resource
	public void setRegSideBarData(RegSideBarData regSideBarData) {
		this.regSideBarData = regSideBarData;
	}

	@Resource
	public void setDoctorCommentService(DoctorCommentService doctorCommentService) {
		this.doctorCommentService = doctorCommentService;
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
	
	
	@Resource
	public void setDoctCommentUserService(
			DoctCommentUserService doctCommentUserService) {
		this.doctCommentUserService = doctCommentUserService;
	}

	public UserRegister getUserRegister() {
		return userRegister;
	}

	public void setUserRegister(UserRegister userRegister) {
		this.userRegister = userRegister;
	}

	public Hospital getHospital(){
		if(hospital!=null) return hospital;
		if(hospId!=null)	
			return hospitalService.get(hospId);
		return null;
	}

	public Type getHospRank(){
		if(hospital!=null)
			return typeService.get(hospital.getRank());
		if(hospId!=null)	
			return typeService.get(hospitalService.get(hospId).getRank());
		return null;
	}

	public Type getHospType(){
		if(hospital!=null)
			return typeService.get(hospital.getHospType());
		if(hospId!=null)	
			return typeService.get(hospitalService.get(hospId).getHospType());
		return null;
	}

	public List<Department> getDeptList(){
		if(deptList==null)
			deptList=departmentService.findByAll(hospId, null);
		return deptList;
	}
	
	public List<Doctor> getDocList() {
		if(docList==null) {
			docList=doctorService.findByPage(null, hospId, deptId);
			List<DoctorComment> docCommenList=null;
			if(!CollectionUtils.isEmpty(docList)){
				for (Doctor d : docList) {
					aveScore=0;
					docCommenList=doctorCommentService.findByPageOrAll(null, d.getId(),null, null);
					if(!CollectionUtils.isEmpty(docCommenList)) {
						for (DoctorComment dc : docCommenList) {
							aveScore=dc.getAveScore()*2+aveScore;
						}
						aveScore=DoubleUtlis.div((double)aveScore,(double)docCommenList.size(),1) ;
					}
					d.setAvgScore(aveScore);
				}
			}
		}
		return docList;
	}
	
	public Doctor getDoctor() {
		if(null==doctor)
			doctor=doctorService.get(docId);
		return doctor;
	}
	
	public Pagination<DoctorComment> getPaginationComment() {
		return paginationComment;
	}

	public void setPaginationComment(Pagination<DoctorComment> paginationComment) {
		this.paginationComment = paginationComment;
	}

	public List<SelectItem> getScoreList() {
		if(scoreList==null)
			scoreList=ElianUtils.getDoctorScore();
		return scoreList;
	}

	public List<SelectItem> getDayList(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM月dd日(EEEE)");
		List<SelectItem> dayList=new ArrayList<SelectItem>();
		for(int i=1;i<=7;i++){
			 if(i!=1)
				calendar.add(Calendar.DAY_OF_WEEK, 1);
			 String s = df.format(calendar.getTime()).replace("星期", "周");
			 dayList.add(new SelectItem(i, s));			 
		}
		return dayList;
	}
	
	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}
	
	public List<Hospital> getHospList(){
		if(hospList==null){
			Integer aId=(Integer)ApplicationUtils.getSession().get("areaId");
			if(subAreaCode!=null) aId=subAreaCode;
			hospList=hospitalService.findRegHospital(aId,hosptialRank);
		}
		return hospList;
	}
	
	public List<Department> getRegDeptList(){
		if(deptList==null && searchHospId!=null){
			deptList=departmentService.findPageByHosp(null, searchHospId);
		}
		return deptList;
	}

	public List<HospitalNoScore> getNoScoreList() {
		if(noScoreList==null)
			noScoreList=regSideBarData.getHospNoScores();
		return noScoreList;
	}
	
	public List<DoctorWork> getDoctorWorkList() {
		if(doctorWorkList==null){
			doctorWorkList=regSideBarData.getTingZhenList(areaId);
		}
		return doctorWorkList;
	}
	
	public List<SelectItem> getSexList(){
		if(sexList==null)
				sexList=ElianUtils.getSexList();
		return sexList;
	}

	public Integer getRegId() {
		return regId;
	}

	public void setRegId(Integer regId) {
		this.regId = regId;
	}

	public String getSearchDeptName() {
		return searchDeptName;
	}

	public void setSearchDeptName(String searchDeptName) {
		this.searchDeptName = searchDeptName;
	}
}
