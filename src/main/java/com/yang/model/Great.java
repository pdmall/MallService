package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author YJG 点赞表
 */
@Table(name = "great")
public class Great extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 评论id
	 * 
	 */
	private Long commentId;

	/**
	 * 点赞用户id
	 */
	private String gUserId;

	/**
	 *该组图片的id
	 */
	private Long pictuerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getgUserId() {
		return gUserId;
	}

	public void setgUserId(String gUserId) {
		this.gUserId = gUserId;
	}

	public Long getPictuerId() {
		return pictuerId;
	}

	public void setPictuerId(Long pictuerId) {
		this.pictuerId = pictuerId;
	}

	


}
