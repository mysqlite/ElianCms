package com.elian.cms.admin.model;

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

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * FTP实体
 * 
 * @author CZH
 */
@javax.persistence.Entity
@Table(name = "T_FTP")
// @BatchSize(size=20)//根据分页界面显示的行数,配置批量操作
public class Ftp implements PersistentLogInterface {
	private static final long serialVersionUID = 4868275371845418277L;
	
	private Integer id;
	/** 版本 */
	private Integer version;
	/** ftp名称 */
	private String ftpName;
	/** 服务器IP */
	private String serverIp;
	/** FTP端口号，默认21 */
	private Integer ftpPort;
	/** FTP用户名 */
	private String account;
	/** FTP密码 */
	private String password;
	/** FTP编码 */
	private String encoding;
	/** 传输超时时间 */
	private Integer timeout;
	/** FTP路径 */
	private String ftpPath;
	/** FTP排序 */
	private Integer ftpSort;
	/** FTP访问地址 */
	private String ftpUrl;
	/** 是否启用 */
	private boolean isDisable;
	/**是否默认*/
	private boolean isDefault;
	/**FTP类型*/
	private Type type;

	@Id
	@Column(name = "ftp_id")
	// @SequenceGenerator(name = "ftpGenerator", sequenceName = "S_T_FTP",
	// allocationSize = 1)
	// @GeneratedValue(generator = "ftpGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
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

	@Column(name = "ftp_name")
	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	@Column(name = "ip")
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Column(name = "port")
	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	@Column(name = "account")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "encoding")
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Column(name = "timeout")
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Column(name = "ftp_path")
	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	@Column(name = "ftp_sort")
	public Integer getFtpSort() {
		return ftpSort;
	}

	public void setFtpSort(Integer ftpSort) {
		this.ftpSort = ftpSort;
	}

	@Column(name = "url")
	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	@Column(name = "is_disable")
	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	@Column(name = "is_default")
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof Ftp)) {
			return false;
		}
		return id == ((Ftp) o).getId();
	}

	public int hashCode() {
		return id;
	}

	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]" + ",[version="
				+ version + "]";
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "Ftp实体", "ftpName=" + ftpName };
	}

}
