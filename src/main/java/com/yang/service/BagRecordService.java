package com.yang.service;

import org.springframework.stereotype.Service;
import com.yang.model.BagRecord;
/**
 * 用户抢福袋记录表
 * @author YJG
 *
 */
@Service
public class BagRecordService extends BaseService<BagRecord>{
	
		/**
		 * 判断该用户是否已经抢过改商户发布的福袋
		 * @param mUserId
		 * @param id
		 * @return
		 */
	public boolean getRob(String mUserId, String id) {
		
	    BagRecord bgr=new BagRecord();
	    bgr.setbUserId(mUserId);
	    bgr.setTheBagId(id);
	    bgr.setStatus(0);//显示状态，如果该商户这次红包被抢完后，所有记录状态都将改为1；
	    //将不再作为查询条件
	    BagRecord ba= super.queryOne(bgr);
	    if(ba!=null) {
	    	
	    	return true;
	    }
		
		return false;
	}

}
