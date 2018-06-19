package com.yang.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.utils.DateUtil;
import com.utils.MoneyRandom;
import com.yang.model.AppUser;
import com.yang.model.ConsumeRecord;
import com.yang.model.FenXiang;
@Service
public class FenXiangService extends BaseService<FenXiang>{
	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	@Autowired
	private AppUserService uService;

	public Integer backMoney(AppUser user) {
		FenXiang fe=new FenXiang();
		String uId=user.getUserId();
		String day1=DateUtil.getDay();
		BigDecimal s=new BigDecimal("9.9");
		fe.setLower(uId);
		FenXiang fe1=super.queryOne(fe);//当前用户作为下级查询
		if(fe1==null) {
			//如果为空，表示自己就是第一级分销合伙人
			
			 //更新消费记录表
            ConsumeRecord cu=new ConsumeRecord();
			List<ConsumeRecord> list = this.consumeRecordService.queryAll();
			long size = list.size();
			cu.setId(size + 1);
			cu.setMoney("-" + 9.9 + "元");// 增加金豆
			cu.setConsumeType("分销合伙人");// 消费类型
			String day=DateUtil.getTime();
			cu.setConsumeTime(day);// 消费时间
			cu.setConsumeStatus(1);// 成功
			cu.setUserId(uId);// 消费用户
			cu.setUnit("微信支付");
			cu.setDay(day1);
			cu.setXiaoFeiType(1);//钱支付
			cu.setConsumePrice(s);
			this.consumeRecordService.saveSelective(cu);
		}else {
			//如果不为空，表示不是第一级
			String upuserid=fe1.getUpper();
			fe.setLower(upuserid);
			FenXiang fe2=super.queryOne(fe);//当前用户作为下级查询
			
			if(fe2==null) {//z只有 A B 级
				
				 //更新消费记录表B
	            ConsumeRecord cu=new ConsumeRecord();
				
				cu.setMoney("-" + 9.9 + "元");// 增加
				cu.setConsumeType("分销合伙人");// 消费类型
				String day=DateUtil.getTime();
				cu.setConsumeTime(day);// 消费时间
				cu.setConsumeStatus(1);// 成功
				cu.setUserId(uId);// 消费用户
				cu.setUnit("微信支付");
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(s);
				this.consumeRecordService.saveSelective(cu);
			
				 ConsumeRecord cu1=new ConsumeRecord();
				cu1.setMoney("+" + 2 + "元");// 增加
				cu1.setConsumeType("合伙人返现");// 消费类型
				cu1.setUserId(upuserid);// 消费用户
				cu1.setUnit("平台");
				this.consumeRecordService.saveSelective(cu1);
				
				
				//更新账户余额 A
				AppUser qud =new AppUser();
				qud.setUserId(upuserid);
				Integer a=2;
				qud.setMoney(user.getMoney().add(new BigDecimal(a)));
				this.uService.updateSelective(qud);
				
				//更新分销记录
				fe.setGrant(1);//分享奖励已经发放
				fe.setGtime(day);//分发的
				super.updateSelective(fe);
				
			}else {//不为空，那么存在三级，A B  C
				String uperA=fe2.getUpper();//查到上级
				
				 //更新消费记录表 C
	            ConsumeRecord cu=new ConsumeRecord();
				
				cu.setMoney("-" + 9.9 + "元");
				cu.setConsumeType("分销合伙人");// 消费类型
				String day=DateUtil.getTime();
				cu.setConsumeTime(day);// 消费时间
				cu.setConsumeStatus(1);// 成功
				cu.setUserId(uId);// 消费用户
				cu.setUnit("微信支付");
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(s);
				this.consumeRecordService.saveSelective(cu);
			
				 ConsumeRecord cu1=new ConsumeRecord();
				cu1.setConsumeTime(day);// 消费时间
				cu1.setConsumeStatus(1);// 成功
				cu1.setMoney("+" + 2 + "元");// 增加
				cu1.setConsumeType("合伙人返现");// 消费类型
				cu1.setUserId(upuserid);// 消费用户
				cu1.setUnit("平台");
				this.consumeRecordService.saveSelective(cu1);
				
				
				//更新账户余额  B
				AppUser qud =new AppUser();
				qud.setUserId(upuserid);
				Integer a=2;
				qud.setMoney(user.getMoney().add(new BigDecimal(a)));
				this.uService.updateSelective(qud);
				
				cu1.setMoney("+" + 6 + "元");// 增加
				cu1.setConsumeType("合伙人返现");// 消费类型
				cu1.setUserId(uperA);// 消费用户
				cu1.setUnit("平台");
				this.consumeRecordService.saveSelective(cu1);
				
				
				//更新账户余额 	A
				qud.setUserId(uperA);
				Integer a1=6;
				qud.setMoney(user.getMoney().add(new BigDecimal(a1)));
				this.uService.updateSelective(qud);
				
				
				//更新分销记录表
				fe.setGrant(1);//分享奖励已经发放
				fe.setGtime(day);//分发的
				super.updateSelective(fe);
			}
			
		}
		return 1;
	}

	
	
	
	/**
	 * （定时调度，分销合伙人，C每消费一元，A返利1金豆）
	 */
	public Integer timingFanXian() {
		FenXiang fe=new FenXiang();
		List<FenXiang> list= super.queryAll();//首先查出所有的记录
		ArrayList<Integer> li=new ArrayList<>();
		if(list==null) { 
			
			System.out.println("暂时没有合伙人记录");
			return null;
		}//没有记录直接退出
		
		for(FenXiang fx:list) {//循环遍历
			
			String upper=fx.getUpper();//先用上级查询
			fe.setLower(upper);//作为下级查询
			FenXiang fe1=super.queryOne(fe);
			if(fe1!=null) {//表示有三级
				String day=DateUtil.getDay();//今天的消费情况
				ConsumeRecord con=new ConsumeRecord();//消费记录表
				con.setDay(day);
				con.setUserId(fe.getLower());//查询出C级合伙人
				List<ConsumeRecord> c1=this.consumeRecordService.queryListByWhere(con);
				if(c1!=null&&!c1.isEmpty()) {//查询出C级合伙人今天的所有消费
					int a=0;
					Integer result = 0;
					for(ConsumeRecord cs:c1) {
						
						if(cs.getXiaoFeiType()==1) {//用钱支付
							
							BigDecimal money=cs.getConsumePrice();
							double c=MoneyRandom.m1(money.doubleValue());
							Integer gold=(int) Math.round(c);//A用户返的金豆
							if(gold>0) {
								li.set(a, gold);
							}
						}else {//用金豆支付
							BigDecimal goldq=cs.getConsumePrice();//金豆
							BigDecimal bd6 = new BigDecimal("100");
							if(goldq.compareTo(bd6)==-1) {
								System.out.println("不做处理");
							}else {
								BigDecimal go=goldq.divide(bd6);//金豆/100
								double c=MoneyRandom.m1(go.doubleValue());
								Integer gold=(int) Math.round(c);//A用户返的金豆
								li.set(a, gold);
							}
						}
						
						a++;
						
					}
					for(Integer num:li){
						result +=num;
					}
					//更新用户剩余金豆
					AppUser ap=new AppUser();
					ap.setUserId(fe1.getUpper());//A级合伙人
					AppUser u=this.uService.queryOne(ap);
					Integer goo=u.getUserIntegral();
					ap.setUserIntegral(goo+result);//返现金豆
					this.uService.updateSelective(ap);
					

					 //更新消费记录表 C
		            ConsumeRecord cu=new ConsumeRecord();
					
					cu.setMoney("+" + result + "金豆");
					cu.setConsumeType("分销合伙人");// 消费类型
					cu.setConsumeTime(day);// 消费时间
					cu.setConsumeStatus(1);// 成功
					cu.setUserId(fe1.getUpper());// 消费用户
					cu.setUnit("合伙人消费返现");
					this.consumeRecordService.saveSelective(cu);
					
					
					
				}else {
					System.out.println("今天没有消费");
				}
				
				
			}
			
		}
		return null;
		
	}
}
