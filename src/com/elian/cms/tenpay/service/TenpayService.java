package com.elian.cms.tenpay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.elian.cms.tenpay.model.Tenpay;

public interface TenpayService {
	public String tenpay(HttpServletRequest request,HttpServletResponse response,Tenpay ten);
	public Tenpay returnTenpay(HttpServletRequest request,HttpServletResponse response,Tenpay ten);
	public Tenpay notifys(HttpServletRequest request,HttpServletResponse response,Tenpay ten);
}
