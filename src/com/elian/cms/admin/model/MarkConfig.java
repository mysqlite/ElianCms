package com.elian.cms.admin.model;

import java.io.Serializable;

/**
 * 图片配置
 * 
 * @author Gechuanyi
 * 
 */
public class MarkConfig implements Serializable {
	private static final long serialVersionUID = -8723239030216245609L;
	private Boolean on;
	private Integer minWidth;
	private Integer minHeight;
	private String imagePath;
	private String content;
	private Integer size;
	private String color;
	private Integer alpha;
	private Integer pos;
	private Integer offsetX;
	private Integer offsetY;

	public Boolean getOn() {
		return on;
	}

	public void setOn(Boolean on) {
		this.on = on;
	}

	public Integer getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getAlpha() {
		return alpha;
	}

	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	public Integer getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}
}