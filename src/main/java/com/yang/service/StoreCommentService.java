package com.yang.service;
import org.springframework.stereotype.Service;
import com.utils.DateUtil;
import com.utils.StringUtils;
import com.yang.model.AppUser;
import com.yang.model.StoreComment;

/**
 * @author 00
 *
 */
@Service
public class StoreCommentService extends BaseService<StoreComment> {
    
	private boolean flag = false;
    private Integer fa=0;
	/**
	 * @author 00 发布评论
	 */
	public boolean getcommentStoreById(StoreComment sc) {
		StoreComment storeComment = new StoreComment();
		String day = DateUtil.getTime();
		Integer all = sc.getAllScore();// 总分
		sc.getServiceScore();// 服务分
		String content = StringUtils.replaceBlank(sc.getContent());// 评论内容
		if (!"".equals(content)) {
			storeComment.setAllScore(all);// 总分
			storeComment.setContent(content);
			storeComment.setEnviroScore(sc.getEnviroScore());// 环境分
			storeComment.setTasteScore(sc.getTasteScore());// 口味分
			storeComment.setPicture(sc.getPicture());// 图片地址
			storeComment.setUserId(sc.getUserId());// 评论用户
			storeComment.setUserName(sc.getUserName());// 用户名
			storeComment.setUserPhoto(sc.getUserPhoto());// 头像
			storeComment.setDateConmmet(day);// 评论的时间
			super.saveSelective(storeComment);
			flag = true;
		}

		return flag;
	}

	/**
	 * @author 00 获取评论列表
	 *    商家回复评论
	 */
	public Integer  storeReplyComment(AppUser user, Long cId,String content) {
		StoreComment sc = new StoreComment();
		int a=user.getFalg();//是否是商家
	     if(a==1) {
	    	 sc.setCommentId(cId);
	    	 StoreComment sm=super.queryOne(sc);
	    	 if(sm!=null) {
	    		  
	    		 fa=4;
	    	 }
	    	 if(!"".equals(content)) {
	    		 String contentq = StringUtils.replaceBlank(content);// 评论内容 
	    		 
	    		 sc.setContent(contentq);
	    		 super.saveSelective(sc);
	    		 fa=1;
	    	 }else {
	    		 
	    		 fa=0;
	    	 }
	    	 
	     }
	      fa=3;
		return fa;
		
	}

}
