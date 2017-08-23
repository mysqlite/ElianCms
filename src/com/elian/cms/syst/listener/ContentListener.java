package com.elian.cms.syst.listener;

import com.elian.cms.syst.model.BaseContent;

/**
 * 内容监听器
 * 
 * @author Joe
 * 
 */
public interface ContentListener {

	/**
	 * 保存之后执行
	 * 
	 * @param T
	 */
	public void afterSave(BaseContent bc);
	public Integer afterSave(BaseContent bc,boolean publish);
	/**
	 * 修改之后执行
	 * 
	 * @param T
	 */
	public void afterUpdate(BaseContent bc);
	public void afterUpdate(BaseContent bc,boolean publish);

	/**
	 * 删除之后执行
	 * 
	 * @param T
	 */
	public void afterDelete(BaseContent bc);
}
