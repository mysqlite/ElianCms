package com.elian.cms.syst.model;

import java.io.Serializable;

/**
 * 选中模型
 * 
 * @author Joe
 * 
 */
public class SelectModel implements Serializable {
	private static final long serialVersionUID = -2714085270727872414L;

	/** 是否选中 */
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
