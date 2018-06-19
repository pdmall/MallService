package com.yang.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.utils.MoneyRandom;
import com.yang.mapper.PutForwardRecordMapper;
import com.yang.model.AppUser;
import com.yang.model.PutForwardRecord;
import com.yang.model.ServiceCharge;

/**
 * @author 00
 * 提现消息记录
 */
@Service
public class PutForwardRecordService extends BaseService<PutForwardRecord>{
	
	@Autowired
	private AppUserService appUserService;
	@Autowired
	
	private PutForwardRecordMapper puMapper;
	
	@Autowired
	private ServiceChargeService charegservice;
	public List<PutForwardRecord> getPutForwardRecord(AppUser user) {

		PutForwardRecord  cr=new PutForwardRecord();
		String uId=user.getUserId();
		Example example = new Example(cr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("userId", uId);
		List<PutForwardRecord> list=this.puMapper.selectByExample(example);
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return new ArrayList<>();
	}
   
	/**
	 * 提现 表
	 * @param user
	 * @param jine
	 * @param servicePrice
	 * @param money
	 * @return
	 */

	public Integer getTiXian(AppUser user, double jine) {
		if(user!=null) {
			ServiceCharge sc=this.charegservice.queryById(1l);
			double rate=sc.getRate();//服务费率
			double money =jine-(jine*rate);
			double money1=MoneyRandom.m1(money);
			String  money2=String.valueOf(money1);
			BigDecimal b= new BigDecimal(money2);//实际到账	
			BigDecimal a=user.getMoney();//余额
			double servi=jine*rate;//服务费
			BigDecimal c=new BigDecimal(jine);//提现金额
			BigDecimal e= new BigDecimal(String.valueOf(servi));//服务费
			if(a.compareTo(c)!=-1) {
				String day =DateUtil.getTime();
				PutForwardRecord pr=new PutForwardRecord();
				pr.setUserId(user.getUserId());//用户id
				pr.setRealJine(c);//提现金额
				pr.setMoney(b);//实际到账
				pr.setPutForwardTime(day);//发起提现时间
				pr.setPutForwardStatus(1);//提现 订单提交    
				pr.setUserName(user.getName());
				pr.setExamineStatu(0);//等待审核
				pr.setServiceCharge(e);//手续费
				
				super.saveSelective(pr);
			
				AppUser u=new AppUser();
				u.setUserId(user.getUserId());
				u.setMoney(c);
				this.appUserService.updateSelective(u);
				
				return  1;
				
		}
			return 2;
		}
        return null;
		
		
	}
      
}
