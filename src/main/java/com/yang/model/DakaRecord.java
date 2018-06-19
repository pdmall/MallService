package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 00 各种消息记录表
 */
@Table(name = "daka_record")
public class DakaRecord extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;// 用户id

	private Integer dakaStatus;// 打卡状态（成功或者失败）

	private String dakaTime;// 打卡时间
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDakaStatus() {
		return dakaStatus;
	}

	public void setDakaStatus(Integer dakaStatus) {
		this.dakaStatus = dakaStatus;
	}

	public String getDakaTime() {
		return dakaTime;
	}

	public void setDakaTime(String dakaTime) {
		this.dakaTime = dakaTime;
	}

}
