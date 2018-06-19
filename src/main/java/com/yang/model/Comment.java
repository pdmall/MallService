package com.yang.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YJG
 *  评论列表实体类
 */
@Table(name = "comment")
public class Comment extends BasePojo{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
	 * 主键id
	 */
   private Long id;
    /**
   	 * 评论内容
   	 */
      private String content;
      /**
  	 * 点赞次数
  	 */
     private Integer cNum;
     /**
 	 * 图片id
 	 */
    private Long cPictureId;
    /**
 	 * 评论时间
 	 */
    private String cTime;
    /**
   	 * 判断自己是否点赞 1：该条评论自己点了赞  2：自己没有点赞
   	 */
      private Integer flag;
     
	/**
   	 * 评论用户ID
   	 */
      private String cUserId;
      /**
  	 * 评论用户昵称
  	 */
     private String cUserName;
     /**
   	 * 评论用户头像
   	 */
      private String cUserPhoto;
    
     /**
 	 * 评论审核状态
 	 */
    private Integer cStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getcNum() {
		return cNum;
	}

	public void setcNum(Integer cNum) {
		this.cNum = cNum;
	}

	public Long getcPictureId() {
		return cPictureId;
	}

	public void setcPictureId(Long cPictureId) {
		this.cPictureId = cPictureId;
	}

	public String getcUserId() {
		return cUserId;
	}

	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}

	public String getcUserName() {
		return cUserName;
	}

	public void setcUserName(String cUserName) {
		this.cUserName = cUserName;
	}

	public String getcUserPhoto() {
		return cUserPhoto;
	}

	public void setcUserPhoto(String cUserPhoto) {
		this.cUserPhoto = cUserPhoto;
	}

	public Integer getcStatus() {
		return cStatus;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setcStatus(Integer cStatus) {
		this.cStatus = cStatus;
	}

	
}
