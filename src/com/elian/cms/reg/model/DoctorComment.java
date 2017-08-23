package com.elian.cms.reg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.PersistentLogInterface;
import com.elian.cms.syst.model.SeoInterface;
@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "T_DOCT_COMMENT")
public class DoctorComment extends BaseContent implements SeoInterface,
		Serializable,PersistentLogInterface {
	private static final long serialVersionUID = 6191600483146525897L;
	/** 主键 */
	private Integer id;
	/** 医生id */
	private Doctor doctor;
	/** 评价用户id */
	private User user;
	/** 疾病名称 */
	private String illness;
	/** 治疗效果评分 */
	private Integer cureScore;
	/** 服务态度评分 */
	private Integer serveScore;
	/** 医德评分 */
	private Integer ethiceScore;
	/** 综合评分 */
	private Integer aveScore;
	/** 是否评分 */
	private boolean score;
	/** 评分时间 */
	private Date scoreTime;
	/** 评论 */
	private String leaveWords;
	/** 评论时间 */
	private Date levaWordTime;
	/** 是否评论 */
	private boolean leaveWord;
	/** 就诊时间 */
	private Date cureTime;
	/** 创建时间 */
	private Date createTime;
	/** 是否置顶 */
	private boolean top;
	/** 是否推荐 */
	private boolean recom;
	/** 排序 */
	private Integer commSort;
	/** 是否启用 */
	private boolean disable;
	/** 版本号 */
	private Integer version;
	/**好评*/
	private Integer good;
	/**差评*/
	private Integer poor;
	/** 用户挂号表 */
	private UserRegister register;

	@Id
	@Column(name = "comm_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doct_id")
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "illness")
	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	@Column(name = "cure_score")
	public Integer getCureScore() {
		return cureScore;
	}

	public void setCureScore(Integer cureScore) {
		this.cureScore = cureScore;
	}

	@Column(name = "serve_score")
	public Integer getServeScore() {
		return serveScore;
	}

	public void setServeScore(Integer serveScore) {
		this.serveScore = serveScore;
	}

	@Column(name = "ethice_score")
	public Integer getEthiceScore() {
		return ethiceScore;
	}

	public void setEthiceScore(Integer ethiceScore) {
		this.ethiceScore = ethiceScore;
	}

	@Column(name = "ave_score")
	public Integer getAveScore() {
		return aveScore;
	}

	public void setAveScore(Integer aveScore) {
		this.aveScore = aveScore;
	}

	@Column(name = "is_score")
	public boolean isScore() {
		return score;
	}

	public void setScore(boolean score) {
		this.score = score;
	}

	@Column(name = "score_time")
	public Date getScoreTime() {
		return scoreTime;
	}

	public void setScoreTime(Date scoreTime) {
		this.scoreTime = scoreTime;
	}

	@Column(name = "leave_word")
	public String getLeaveWords() {
		return leaveWords;
	}

	public void setLeaveWords(String leaveWords) {
		this.leaveWords = leaveWords;
	}

	@Column(name = "leave_word_time")
	public Date getLevaWordTime() {
		return levaWordTime;
	}

	public void setLevaWordTime(Date levaWordTime) {
		this.levaWordTime = levaWordTime;
	}

	@Column(name = "is_leave_word")
	public boolean isLeaveWord() {
		return leaveWord;
	}

	public void setLeaveWord(boolean leaveWord) {
		this.leaveWord = leaveWord;
	}

	@Column(name = "cure_time")
	public Date getCureTime() {
		return cureTime;
	}

	public void setCureTime(Date cureTime) {
		this.cureTime = cureTime;
	}

	@Column(name = "create_Time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_top")
	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	@Column(name = "is_recom")
	public boolean isRecom() {
		return recom;
	}

	public void setRecom(boolean recom) {
		this.recom = recom;
	}

	@Column(name = "comm_sort")
	public Integer getCommSort() {
		return commSort;
	}

	public void setCommSort(Integer commSort) {
		this.commSort = commSort;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "reg_id")
	public UserRegister getRegister() {
		return register;
	}

	public void setRegister(UserRegister register) {
		this.register = register;
	}
	
	@Column(name = "good")
	public Integer getGood() {
		return good;
	}

	public void setGood(Integer good) {
		this.good = good;
	}

	@Column(name = "poor")
	public Integer getPoor() {
		return poor;
	}

	public void setPoor(Integer poor) {
		this.poor = poor;
	}

	@Transient
	public String getTitle() {
		return null;
	}

	@Transient
	public String getSeoDescription() {
		return null;
	}

	@Transient
	public String getSeoKeywords() {
		return null;
	}

	@Transient
	public String getSeoTitle() {
		return null;
	}

	@Transient
	public String[] getSysLog() {
		return new String[]{"医生评价","id="+id};
	}
	
	
}
