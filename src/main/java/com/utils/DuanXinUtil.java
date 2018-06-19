package com.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class DuanXinUtil {
	 /**********需要准备的参数**************/  
		public static final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		public static final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	    public static final String accessKey="LTAIC1Sp3IuyvlUz";//需要修改  
	    public static final String accessSecret="WH7zDYfG1hSI6GAJnfiE2LBV7ewmav";//需要修改  
	   // public static final String MODEL1="SMS_135825035";//用户注册  
	   // public static final String MODEL2="SMS_135825034";//修改密码
	    public static final String MODEL3="SMS_135805235";//用户提现申请 
	   // public static final String MODEL4="SMS_135790263";//提现到账提醒 
	    public static final String signName="阿里云短信测试专用";//需要修改 
	   
	    /**********************************/ 
	      
	    public static void main(String[] args) {     
	        String phone="13540031779"; 
	       
	        //根据自己定义的短信模板，修改  
	        test(phone);  
	  
	    }  
	       public static Integer test(String phone) {  
	    	   
	    	   //可自助调整超时时间
	           System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	           System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	           try {  
	        	   int code=(int)((Math.random()*9+1)*100000);
	        	 
	        	   String jsonStr="{\"name\":\""+phone+"\",\"code\":\""+code+"\"}";  
	        	 //初始化ascClient,暂时不支持多region（请勿修改）
	        	   IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey,accessSecret);
	        	   DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	        	   IAcsClient acsClient = new DefaultAcsClient(profile);
	        	   //组装请求对象
	               SendSmsRequest request = new SendSmsRequest(); 
	               //post提交
	               request.setMethod(MethodType.POST);
	                //管理控制台中配置的短信签名（状态必须是验证通过）  
	                request.setSignName(signName);  
	                //管理控制台中配置的审核通过的短信模板的模板CODE（状态必须是验证通过）  
	                	request.setTemplateCode(MODEL3);
//	              短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。  
//	               例如:短信模板为：“接受短信验证码${no}”,此参数传递{“no”:”123456”}，用户将接收到[短信签名]接受短信验证码123456  
	                 request.setTemplateParam(jsonStr);
	                //目标手机号，多个手机号可以逗号分隔  
	                 request.setPhoneNumbers(phone);
	             // request.setVersion(version);  
	                SendSmsResponse httpResponse = acsClient.getAcsResponse(request);  
	                String requestId = httpResponse.getRequestId(); 
	                System.out.println(httpResponse.getCode());
	                System.err.println("requestId:"+requestId); 
	                return code;
	            } catch (ServerException e) {  
	                e.printStackTrace();  
	            }  
	            catch (ClientException e) {  
	                e.printStackTrace();  
	            }  
	           return null;
	        }  
	         
	         
	        /** 
	         * 使用毫秒转换为中文日期 
	         * @param tmpDateInt 
	         * @return 
	         */ 
	        public static String getChinaDateByMM(long time){  
	            String ret_date = "";  
	            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy年MM月dd日");  
	            ret_date = formatter.format(time);  
	            return ret_date;  
	        } 
}
