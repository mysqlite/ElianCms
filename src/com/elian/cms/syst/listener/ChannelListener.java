package com.elian.cms.syst.listener;

import com.elian.cms.admin.model.Channel;

/**
 * 栏目监听接口，用于删除栏目已生成的静态化文件,t_temp_config表数据,内容表及内容生成的静态文件,最后删除栏目本身
 * 
 * @author Joe
 * 
 */
public interface ChannelListener {
	/**
	 * 删除与栏目相关的信息
	 * 
	 * @param T
	 */
	public void delete(Channel channel);
}
