package com.yang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.abel533.entity.Example;
import com.yang.mapper.OrderMapper;
import com.yang.model.Order;
import com.yang.model.Store;


@Service
public class OrderService extends BaseService<Order>{
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private StoreService storeService;
	
	/**
	 * 获取评论列表
	 * @param 
	 */
	public List<Order> getStoreCommentList(Long storeId) {
		Store st=this.storeService.queryById(storeId);
		String sellId=st.getShopUserId();
		Order or=new Order();
		Example example = new Example(or.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("selleruserid", sellId);
		List<Order> list = this.orderMapper.selectByExample(example);
		Integer size=list.size();
		List<Order> lis=new ArrayList<>();
		if(list!=null&&!list.isEmpty()) {

			for(int i=0;i<size;i++) {
				String text=list.get(i).getmTxx1();
				if(!"".equals(text)) {
					
					lis.add(list.get(i));
				}
			}
			
			return lis;
		}
		  return new ArrayList<>();
	}
	
}
