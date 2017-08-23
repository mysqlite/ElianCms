package com.elian.cms.syst.model;

/**
 * @author Joe
 * 
 * 用于切换类似是否启用功能Model
 */
public class TrueFalseItem {
	private String trueStr;
	private String falseStr;
	private String trueDescription;
	private String falseDescription;

	public TrueFalseItem() {
	}

	public TrueFalseItem(String trueStr, String falseStr) {
		this.trueStr = trueStr;
		this.falseStr = falseStr;
	}
	
	public TrueFalseItem(String trueStr, String trueDescription,
			String falseStr, String falseDescription) {
		this.trueStr = trueStr;
		this.trueDescription = trueDescription;
		this.falseStr = falseStr;
		this.falseDescription = falseDescription;
	}

	public String getTrueStr() {
		return trueStr;
	}

	public void setTrueStr(String trueStr) {
		this.trueStr = trueStr;
	}

	public String getFalseStr() {
		return falseStr;
	}

	public void setFalseStr(String falseStr) {
		this.falseStr = falseStr;
	}

	public String getTrueDescription() {
		return trueDescription;
	}

	public void setTrueDescription(String trueDescription) {
		this.trueDescription = trueDescription;
	}

	public String getFalseDescription() {
		return falseDescription;
	}

	public void setFalseDescription(String falseDescription) {
		this.falseDescription = falseDescription;
	}
}
