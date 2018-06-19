package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * @author YJG	
 * 投票表实体类
 */
import javax.persistence.Table;

@Table(name = "vote")
public class Vote extends BasePojo {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 序列化
	 */
	private Long id;
	/**
	 * 图片类型id
	 */
	private Integer vTypeId;
	/**
	 * 投票用户ID
	 */
	private String vUserId;
	/**
	 * 消息id
	 */
	private Long vPId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getvTypeId() {
		return vTypeId;
	}

	public void setvTypeId(Integer vTypeId) {
		this.vTypeId = vTypeId;
	}

	public String getvUserId() {
		return vUserId;
	}

	public void setvUserId(String vUserId) {
		this.vUserId = vUserId;
	}

	public Long getvPId() {
		return vPId;
	}

	public void setvPId(Long vPId) {
		this.vPId = vPId;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", vTypeId=" + vTypeId + ", vUserId=" + vUserId + ", vPId=" + vPId + "]";
	}

}