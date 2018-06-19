package com.yang.model;

import java.io.Serializable;

/**
 * 微信接口实体类
 * 
 * @author a55660000
 *
 */
public class CodeOpenid implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private int expires_in;
	private String refresh_token;
	/**
	 * 微信的openID
	 */
	private String openid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	private String scope;
	private String access_token;

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
