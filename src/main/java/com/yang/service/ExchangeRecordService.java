package com.yang.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.utils.MoneyRandom;
import com.yang.mapper.ExchangeRecordMapper;
import com.yang.model.AppUser;
import com.yang.model.ExchangeRecord;
import com.yang.model.ServiceCharge;

@Service
public class ExchangeRecordService extends BaseService<ExchangeRecord>{
	
	
	@Autowired
	
	private AppUserService aService;
	@Autowired
	
	private ExchangeRecordMapper exrMapper;
	
	@Autowired
	private ServiceChargeService charegservice;
	/**
	 * 个人中心查询个人兑换记录
	 * @param user
	 * @return
	 */
	public List<ExchangeRecord> getExchangeRecord(AppUser user) {

		ExchangeRecord  cr=new ExchangeRecord();
		String uId=user.getUserId();
		Example example = new Example(cr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("userId", uId);
		List<ExchangeRecord> list=this.exrMapper.selectByExample(example);
		if(list!=null&&!list.isEmpty()) {
			return list;
		}
		return new ArrayList<>();
	}
	/**
	 * 兑换服务接口       1，余额兑换金豆  2.金豆兑换余额
	 * @param user
	 * @param duihuan		兑换金额
	 * @param money			实际到账
	 * @param type          服务类型
	 * @param servicePrice 手续费
	 * @return
	 */
	public Integer getExchangeGold(AppUser user, String duihuan, Integer type) {
		BigDecimal m=user.getMoney();//账户余额
		Integer su=user.getUserIntegral();//剩余金豆
		String uId=user.getUserId();
		String day=DateUtil.getTime();//兑换时间
		BigDecimal duihuan1=new BigDecimal(duihuan);
		AppUser u=new AppUser();
		ExchangeRecord eg=new ExchangeRecord();//兑换记录对象
		if(type==1) {//余额兑换金豆

			if(m.compareTo(duihuan1)!=-1) {//账户余额大于兑换金额的前提下
				

				ServiceCharge sc=this.charegservice.queryById(3l);
				double rate=sc.getRate();//服务费率
				double jine=Double.parseDouble(duihuan);
				double money =jine-(jine*rate);
				double money1=MoneyRandom.m1(money);
				Integer gold=(int)(money1*100);//实际到账金豆
				double servi=jine*rate;//服务费
				double servi1=MoneyRandom.m1(servi);

				BigDecimal e= new BigDecimal(String.valueOf(servi1));//服务费
				
				
			int mo = (new Double(money)).intValue();//实际到账金豆
			u.setUserId(uId);
			u.setMoney(m.subtract(duihuan1));//刷新余额
			u.setUserIntegral(su+mo);	
			this.aService.updateSelective(u);
			
			//存入兑换记录表
			int g = Integer.parseInt(duihuan);
			eg.setUserId(uId);
			eg.setExchangeTime(day);
			eg.setExchangeStatus(1);//兑换状态成功
			eg.setExchangeType(1);//1：余额兑换金豆
			eg.setGold(g*100);//兑换的金豆
			eg.setJine(duihuan1);//兑换的金额
			eg.setMoney(new BigDecimal(gold));//实际到账金豆
			eg.setServiceCharge(e);//手续费
			super.saveSelective(eg);
			
				
			 return 1;
				
			}
			
			return  2;
			
		}else if(type==2) {//金豆兑换金额
			
			Integer duihuan2=Integer.parseInt(duihuan);
			if(su>=duihuan2) {//账户剩余金豆大于兑换金豆
				

				ServiceCharge sc=this.charegservice.queryById(2l);
				double rate=sc.getRate();//服务费率
				Integer jine=Integer.parseInt(duihuan);//兑换的金豆
				double gold1 =jine-(jine*rate);
				double money=gold1/100;
				String money1=String.valueOf(money);
				BigDecimal money2=new BigDecimal(money1);//实际到账金额
				double servi=jine*rate;//服务费
				double servi1=MoneyRandom.m1(servi);
				BigDecimal e= new BigDecimal(String.valueOf(servi1));//服务费
				
				int mo = Integer.parseInt(duihuan);//兑换的金豆
				String mm=String.valueOf(mo/100);
				u.setUserId(uId);
				u.setMoney(m.add(money2));//刷新余额（实际到账）
				u.setUserIntegral(su-mo);	
				this.aService.updateSelective(u);
				
				//存入兑换记录表
				eg.setUserId(uId);
				eg.setExchangeTime(day);
				eg.setExchangeStatus(1);//兑换状态成功
				eg.setExchangeType(2);//2：金豆兑换余额
				eg.setGold(mo);//兑换的金豆
				eg.setJine(new BigDecimal(mm));//兑换的金额
				eg.setMoney(money2);//实际到账
				eg.setServiceCharge(e);//手续费
				super.saveSelective(eg);
				
				 return 1;
				
			}
			
			return 2;
			
		}
		
		System.out.println("type类型出错了");
		return null;
		
	}

}
