package com.elian.cms.admin.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 默认医院类型 @author Gechuanyi
 */
@Entity
@Table(name = "T_HOSP_DEPA_TYPE", schema = "ELIANADMIN")
public class HospDepaType implements PersistentLogInterface {
	private static final long serialVersionUID = 5709349883108515987L;
	private Integer depaTypeId;
	private Integer pareatId;
	private String typeName;
	private String typeDesc;
	private Integer typeSort;
	private boolean disable;
	private Integer version;

	@Id
	@Column(name = "DEPA_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	// @SequenceGenerator(name = "hospDepaTypeGenerator", sequenceName =
	// "S_T_HOSP_DEPA_TYPE", allocationSize = 1)
	// @GeneratedValue(generator = "hospDepaTypeGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getDepaTypeId() {
		return this.depaTypeId;
	}

	public void setDepaTypeId(Integer depaTypeId) {
		this.depaTypeId = depaTypeId;
	}

	@Column(name = "PAREAT_ID", nullable = false, precision = 22, scale = 0)
	public Integer getPareatId() {
		return this.pareatId;
	}

	public void setPareatId(Integer pareatId) {
		this.pareatId = pareatId;
	}

	@Column(name = "TYPE_NAME", nullable = false, length = 200)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "TYPE_DESC", length = 510)
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@Column(name = "TYPE_SORT", nullable = false, precision = 22, scale = 0)
	public Integer getTypeSort() {
		return this.typeSort;
	}

	public void setTypeSort(Integer typeSort) {
		this.typeSort = typeSort;
	}

	@Column(name = "IS_DISABLE", nullable = false, precision = 1, scale = 0)
	public boolean getDisable() {
		return this.disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@Transient
	public Integer getId() {
		return depaTypeId;
	}

	@Transient
	public String[] getSysLog() {
		return new String[] { "默认医院类型", "typeName=" + typeName };
	}
}