package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.elian.cms.syst.model.EagerLoading;
import com.elian.cms.syst.model.PersistentLogInterface;


/**
 * 医院类型 @author Gechuanyi
 */
@javax.persistence.Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="T_TYPE")
public class Type  implements PersistentLogInterface,EagerLoading{
	private static final long serialVersionUID = -2056400553644871627L;
	private Integer id;
     private String typeName;
     private String typeDesc;
     private Integer typeSort;
     private boolean disable;
     private String typeClass;
     private Integer version;
    
	@Id
	@Column(name = "TYPE_ID")
	// @SequenceGenerator(name = "typeGenerator", sequenceName = "S_T_TYPE",
	// allocationSize = 1)
	// @GeneratedValue(generator = "typeGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}
    
    public void setId(Integer id) {
        this.id = id;
    }
    
	@Column(name="TYPE_CLASS")
    public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	@Column(name="TYPE_NAME")
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @Column(name="TYPE_DESC", length=510)

    public String getTypeDesc() {
        return this.typeDesc;
    }
    
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
    
    @Column(name="TYPE_SORT", nullable=false, precision=22, scale=0)

    public Integer getTypeSort() {
        return this.typeSort;
    }
    
    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }
    
    @Column(name="IS_DISABLE", nullable=false, precision=1, scale=0)
    public boolean isDisable() {
		return disable;
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
	public void getLazyObject() {
		
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "医院类型", "typeName=" + typeName };
	}
}