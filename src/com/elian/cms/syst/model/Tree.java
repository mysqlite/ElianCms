package com.elian.cms.syst.model;

import java.io.Serializable;

/**
 * 树结构模型
 * 
 * @author Joe
 * 
 */
public abstract class Tree extends SelectModel implements Serializable {
	private static final long serialVersionUID = 6333331664877309408L;

	/** 树的父级节点 */
	public abstract Integer getParentId();

	/** 树的深度 */
	public abstract int getDepth();
}
