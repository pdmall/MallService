package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG 商铺的评论表
 *
 */
@Table(name="store_comment")
public class StoreComment extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;// 评论id;主键id

	private String userId;// 评论人id
	
	private String userName;// 用户昵称

	private String userPhoto;// 用户的头像

	private String content;// 评论内容

	private Integer allScore;// 总体评分

	private Integer tasteScore;// 口味评论分

	private String dateConmmet;// 评论的时间

	private Integer enviroScore;// 环境评分

	private Integer serviceScore;// 价格评分
	
	private String   picture;//用户上传照片
	
	private Integer status ;//评论审核状态
	
	private Long storeId ;//店铺id

	private String commentReply;//商家回复
	
	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAllScore() {
		return allScore;
	}

	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}

	

	public String getDateConmmet() {
		return dateConmmet;
	}

	public void setDateConmmet(String dateConmmet) {
		this.dateConmmet = dateConmmet;
	}

	public Integer getServiceScore() {
		return serviceScore;
	}


	public String getPicture() {
		return picture;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}

	public Integer getTasteScore() {
		return tasteScore;
	}

	public void setTasteScore(Integer tasteScore) {
		this.tasteScore = tasteScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Integer getEnviroScore() {
		return enviroScore;
	}

	public void setEnviroScore(Integer enviroScore) {
		this.enviroScore = enviroScore;
	}

	public String getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(String commentReply) {
		this.commentReply = commentReply;
	}

}
