/**
 * 
 */
package com.elian.cms.alipay.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.alipay.model.Alipay;

public class MD5Signature {
	public static String sign(String content, String key) throws Exception {
		String tosign = (content == null ? "" : content) + key;
		System.out.println("signdata:" + tosign);
		try {
			return DigestUtils.md5Hex(getContentBytes(tosign, "utf-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new SignatureException("MD5签名[content = " + content+ "; charset = utf-8" + "]发生异常!", e);
		}
	}

	public static boolean verify(Alipay alipay)throws Exception {
		String tosign = (alipay.getVerifyData() == null ? "" : alipay.getVerifyData()) + alipay.getKey();
		try {
			String mySign = DigestUtils.md5Hex(getContentBytes(tosign, "utf-8"));
			return StringUtils.equals(mySign, alipay.getSign()) ? true : false;
		}
		catch (UnsupportedEncodingException e) {
			throw new SignatureException("MD5验证签名[content = " + alipay.getVerifyData()+ "; charset =utf-8 " + "; signature = " +alipay.getSign() + "]发生异常!",e);
		}
	}

	public static boolean verify(String content, String sign, String key,
			String charset) throws Exception {
		String tosign = (content == null ? "" : content) + key;
		try {
			String mySign = DigestUtils.md5Hex(getContentBytes(tosign, charset));
			return StringUtils.equals(mySign, sign) ? true : false;
		}
		catch (UnsupportedEncodingException e) {
			throw new SignatureException("MD5验证签名[content = " + content+ "; charset =" + charset + "; signature = " + sign+ "]发生异常!", e);
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	protected static byte[] getContentBytes(String content, String charset)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(charset)) {
			return content.getBytes();
		}
		return content.getBytes(charset);
	}
}
