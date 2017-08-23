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
 * 用户挂号表
 * 
 * @author Joe
 */
@Entity
@Table(name = "T_USER_REGISTER")
public class UserRegister implements PersistentLogInterface,EagerLoading {
	private static final long serialVersionUID = -509105287638761521L;
	/** ID */
	private Integer id;
	/** 版本 */
	private Integer version;
	/** 单号 */
	private String registerCode;
	/** 排班ID */
	private DoctorWork schedulingId;
	/** 创建人 */
	private User user;
	/** 金额 */
	private int amount;
	/** 支付方式 */
	private int payType;
	/** 支付状态 */
	private int payStatus;
	/** 报道时间 */
	private Date registerTime;
	/** 状态 */
	private int status;
	/** 号源类型 */
	private int noSourceType;
	/** 创建日期 */
	private Date createTime;

	@Id
	@Column(name = "reg_id")
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

	@Column(name = "amount")
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scheduling_id")
	public DoctorWork getSchedulingId() {
		return schedulingId;
	}

	public void setSchedulingId(DoctorWork schedulingId) {
		this.schedulingId = schedulingId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "register_code")
	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	@Column(name = "pay_type")
	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@Column(name = "pay_status")
	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "register_time")
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "no_source_type")
	public int getNoSourceType() {
		return noSourceType;
	}

	public void setNoSourceType(int noSourceType) {
		this.noSourceType = noSourceType;
	}

	@Transient
	public void getLazyObject() {
		getSchedulingId().getLazyObject();
		getUser().getLazyObject();
		
		DoctorWork dw=new DoctorWork();
		User u=new User();
		try {
			BeanUtils.copyProperties(dw, getSchedulingId());
			BeanUtils.copyProperties(u, getUser());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setSchedulingId(dw);
		this.setUser(u);
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "用户挂号", "id=" + id };
	}
}
