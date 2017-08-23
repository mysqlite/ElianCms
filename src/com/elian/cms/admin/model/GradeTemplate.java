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

import com.elian.cms.syst.model.PersistentLogInterface;

/**
 * 等级模板模型
 * 
 * @author Joe
 */
@javax.persistence.Entity
@Table(name = "RE_GRADE_TEMP")
public class GradeTemplate implements PersistentLogInterface{
	private static final long serialVersionUID = 727679257761534176L;
	
	private Integer id;
	private Grade grade;
//	private String templateUrl;
	private Template template;

	@Id
	@Column(name = "grade_temp_id")
	// @SequenceGenerator(name = "gradeTemplateGenerator", sequenceName =
	// "S_RE_ROLE_ACTION", allocationSize = 1)
	// @GeneratedValue(generator = "gradeTemplateGenerator", strategy =
	// GenerationType.SEQUENCE)
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

//	@Column(name = "temp_url")
//	public String getTemplateUrl() {
//		return templateUrl;
//	}
//
//	public void setTemplateUrl(String templateUrl) {
//		this.templateUrl = templateUrl;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEMP_ID")
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "等级模板", "id=" + id };
	}
}