package com.yang.service;

import org.springframework.stereotype.Service;

import com.yang.model.OrderCommentLabel;
/**
 * 订单评论标签服务
 * @author Administrator
 *
 */
@Service
public class OrderCommentLabelService extends BaseService<OrderCommentLabel>{
	/**
	 * 根据店铺id查询评论标签词
	 * @param sId
	 * @return
	 */
	public OrderCommentLabel getCommentLabelById(Long sId) {
		OrderCommentLabel otl=new OrderCommentLabel();
		otl.setoStoreId(sId);
		OrderCommentLabel c=super.queryOne(otl);
		return c!=null?c:null;
	
	}
}
