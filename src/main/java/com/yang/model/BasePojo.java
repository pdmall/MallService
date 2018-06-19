package com.yang.model;

import java.io.Serializable;
import java.util.Date;

public abstract class BasePojo implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Date created;// 创建时间
	private Date updated;// 修改时间

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
