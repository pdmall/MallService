package com.yang.test;


//import static org.quartz.JobBuilder.newJob;
//import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
//import static org.quartz.TriggerBuilder.newTrigger;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.Trigger;
//import org.quartz.impl.StdSchedulerFactory;
//
//import com.utils.QuartzManager;

public class test1 {
	
	 public static void main(String[] args) {
	       double money=0.34; 
	     //  BigDecimal s=new BigDecimal(0);
//	       BigDecimal ss=new BigDecimal(money);
	       Integer gold=(int) Math.round(money);
	       	System.out.println(gold);
	}
}
		







//提现成功调用的接口。
//Map<String,String> map=this.service.tixian(wu.getOpenId(), wu.getMoney());
//if (StringUtils.equals(map.get("state"), "SUCCESS")) {
//	w.setState(1);//成功就设置为已到账
//	w.setReason(map.get("payment_no"));//设置显示为订单号	
//} else {
//	w.setState(2);//失败设置为为到账
//	w.setReason(map.get("err_code_des"));//设置为微信返回的状态信息
//}

