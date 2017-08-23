package com.elian.cms.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 医生排班设置
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_DOCTOR_REGISTER_SET")
public class DoctorRegisterSet implements PersistentLogInterface {
	private static final long serialVersionUID = -509105287638761521L;
	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 医生 */
	private Doctor doctor;
	/** 星期 */
	private int weeks;
	/** 班次 */
	private int rank;
	/** 开始时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 频率 */
	private int step;
	/** 金额 */
	private int amount;
	/** 是否循环 */
	private int cycle;
	/** 截止时间 */
	private Date closeTime;
	/** 门诊类型 */
	private int regType;
	/** 号源 */
	private int noSource;
	/** 创建人 */
	private User creater;
	/** 创建日期 */
	private Date createTime;

	@Id
	@Column(name = "scheduling_set_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doct_id")
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Column(name = "weeks")
	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	@Column(name = "rank")
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Column(name = "start_time")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "step_value")
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Column(name = "amount")
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name = "is_cycle")
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	@Column(name = "generate_time")
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	@Column(name = "reg_type")
	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	@Column(name = "no_source")
	public int getNoSource() {
		return noSource;
	}

	public void setNoSource(int noSource) {
		this.noSource = noSource;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creater_ID")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医生排班", "id=" + id };
	}
}
