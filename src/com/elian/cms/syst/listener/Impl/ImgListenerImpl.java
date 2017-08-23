package com.elian.cms.syst.listener.Impl;

import com.elian.cms.syst.model.ImgInterface;

/**
 * 特殊内容的处理方式
 * 
 * @author Joe
 * 
 */
public class ImgListenerImpl<T extends ImgInterface> implements
		ImgListener<ImgInterface> {

	public void get(ImgInterface t) {
		t.imgs();
	}

	public void save(ImgInterface t) {
		t.descImgs();
	}
}
