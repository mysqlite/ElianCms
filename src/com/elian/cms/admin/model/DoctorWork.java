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

import org.apache.commons.beanutils.BeanUtils;

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 医生排班设置
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_DOCTOR_WORK")
public class DoctorWork implements PersistentLogInterface,EagerLoading {
	private static final long serialVersionUID = -509105287638761521L;
	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 医生 */
	private Doctor doctor;
	/** 班次 */
	private int rank;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 金额 */
	private int amount;
	/** 门诊类型 */
	private int regType;
	/** 号源 */
	private int noSource;
	/** 创建人 */
	private User creater;
	/** 创建日期 */
	private Date createTime;
	/** 停诊 */
	private boolean isStopWork;

	@Id
	@Column(name = "scheduling_id")
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

	@Column(name = "rank")
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "amount")
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
	@JoinColumn(name = "create_ID")
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
	
	@Column(name = "is_work")
	public boolean isStopWork() {
		return isStopWork;
	}

	public void setStopWork(boolean isStopWork) {
		this.isStopWork = isStopWork;
	}

	@Transient
	public void getLazyObject() {
		getDoctor().getLazyObject();
		if(getCreater()!=null)
			getCreater().getLazyObject();
		
		Doctor d=new Doctor();
		User u=new User();
		try {
			BeanUtils.copyProperties(d, getDoctor());
			if(getCreater()!=null)
				BeanUtils.copyProperties(u, getCreater());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setDoctor(d);
		if(getCreater()!=null)
			this.setCreater(u);
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医生排班", "id=" + id };
	}
}
