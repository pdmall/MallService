package com.yang.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.utils.DuanXinUtil;
import com.utils.HttpUtil;
import com.utils.PayCommonUtil;
import com.utils.XMLUtil;
import com.yang.model.AppUser;
import com.yang.model.ConsumeRecord;
import com.yang.model.DakaRecord;
import com.yang.model.ExchangeRecord;
import com.yang.model.Gameplay;
import com.yang.model.PutForwardRecord;
import com.yang.model.RechargeRecord;
import com.yang.model.ServiceCharge;
import com.yang.model.Voucher;
import com.yang.service.AppUserService;
import com.yang.service.ConsumeRecordService;
import com.yang.service.DakaRecordService;
import com.yang.service.ExchangeRecordService;
import com.yang.service.FeedbackService;
import com.yang.service.FenXiangService;
import com.yang.service.GameplayService;
import com.yang.service.PutForwardRecordService;
import com.yang.service.RechargeRecordService;
import com.yang.service.RedisService;
import com.yang.service.ServiceChargeService;
import com.yang.service.VoucherService;
/**
 * @author 00
 *    个人中心 接口类
 *    
 *   2.通过token获取个人消费记录表  
 *     3.通过token获取个人打卡记录表 
 *       4.通过token获取个人充值记录表
 *         5.通过token获取个人兑换记录表
 *           6.通过token获取个人提现记录表
 *             7.通过token获取个人现金券表
 *    		     8.通过token 反馈意见
 *                 9.现金钱包接口
 *    				10.查询不同类型的手续费率
 *    					11.账户充值接口
 *    					 12.账户金额兑换接口
 *    						 13.现金提现(后台审核后，提现成功)
 *    							14.提现时，短信验证
 *    							15.各种玩法介绍
 */					

@CrossOrigin
@Controller
@RequestMapping(value = "qian/personal/")
public class PersonalCenterController {

	@Autowired
	private AppUserService aService;// 用户表

	@Autowired
	private ConsumeRecordService consumeRecordService;// 个人消费记录表
	@Autowired
	private DakaRecordService drecordService;// 打卡记录表

	@Autowired
	private ExchangeRecordService exchangeRecordService;// 兑换记录表

	@Autowired
	private PutForwardRecordService putForwardRecordService;// 提现记录表

	@Autowired
	private RechargeRecordService rechargeRecordService;// 充值记录表

	@Autowired
	private VoucherService voService;// 我的现金券表
	
	@Autowired
	private FenXiangService fenxiangService;// 我的现金券表

	@Autowired
	private FeedbackService feService;// 用户反馈建议

	@Autowired
	private ServiceChargeService sChargeService;// 手续费率表
	
	@Autowired
	private AppUserService uService;//用户
	
	@Autowired
	private GameplayService gameplayService;// 个人中心玩法介绍
	
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;
	// private static final ObjectMapper MAPPER = new ObjectMapper();
	// private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

	/**
	 *
	 * 2.通过token获取个人消费记录表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getConsumeRecord", method = RequestMethod.GET)
	public ResponseEntity<List<ConsumeRecord>> getConsumeRecord(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<ConsumeRecord> list = this.consumeRecordService.getConsumeRecord(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 3.通过token获取个人打卡记录表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getDakaRecord", method = RequestMethod.GET)
	public ResponseEntity<List<DakaRecord>> getDakaRecord(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<DakaRecord> list = this.drecordService.getDakaRecord(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 4.通过token获取个人充值记录表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getRechargeRecord", method = RequestMethod.GET)
	public ResponseEntity<List<RechargeRecord>> getRechargeRecord(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<RechargeRecord> list = this.rechargeRecordService.getRechargeRecord(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 5.通过token获取个人兑换记录表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getExchangeRecord", method = RequestMethod.GET)
	public ResponseEntity<List<ExchangeRecord>> getExchangeRecord(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<ExchangeRecord> list = this.exchangeRecordService.getExchangeRecord(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 6.通过token获取个人提现记录表
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getPutForwardRecord", method = RequestMethod.GET)
	public ResponseEntity<List<PutForwardRecord>> getPutForwardRecord(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<PutForwardRecord> list = this.putForwardRecordService.getPutForwardRecord(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 7.通过token获取个人现金券表
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "getVoucherByUserId", method = RequestMethod.GET)
	public ResponseEntity<List<Voucher>> getVoucherByUserId(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			List<Voucher> list = this.voService.getVoucherByUserId(user);
			if (list != null && !list.isEmpty()) {

				return ResponseEntity.ok(list);

			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 9.现金钱包接口
	 * 
	 * @param token
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getCashPurse", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Double>> getCashPurse(@RequestParam("token") String token) {

		try {
			Map<String, Double> map = new HashMap<String, Double>();
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			AppUser au=this.aService.getuser(user.getUserId());
			double q = au.getMoney().doubleValue();// 余额
			double a = (double) au.getUserIntegral();// 剩余金豆

			map.put("money", q);
			map.put("UserIntegral", a);

			return ResponseEntity.status(HttpStatus.CREATED).body(map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 *
	 * 10.查询不同类型的手续费率
	 * 
	 * @param
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "getInsertRate", method = RequestMethod.GET)
	public ResponseEntity<ServiceCharge> getInsertRate(@RequestParam("type") String type) {
		ServiceCharge a = new ServiceCharge();
		try {
			if("tx".equals(type)) {
				
				 a = this.sChargeService.queryById(1l);
				 
			}else if("jindou".equals(type)) {
				
				 a = this.sChargeService.queryById(2l);
			}else if("money".equals(type)){
				
				 a = this.sChargeService.queryById(3l);
			}else {
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
			}
			if(a!=null) {
				return ResponseEntity.ok(a);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 *
	 * 11.账户充值接口
	 * 
	 * @param token
	 * @param money
	 *            实际到账
	 * @return
	 */
	@RequestMapping(value = "rechargeBalance", method = RequestMethod.GET)
	public ResponseEntity<Void> rechargeBalance(@RequestParam("token") String token,
			@RequestParam("money") String money) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			Integer q = this.rechargeRecordService.rechargeBalance(user, money);
			if (q == 1) {

				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 *
	 * 12.账户金额兑换接口 两种方式 1，余额兑换金豆 2.金豆兑换余额
	 * 
	 * @param token
	 * @param duihuan
	 *            兑换金额
	 * @param money
	 *            实际到账
	 * @param type
	 *            兑换类型 1/2
	 * @param servicePrice
	 *            手续费
	 * 
	 * @return
	 */
	@RequestMapping(value = "getExchangeGold", method = RequestMethod.POST)
	public ResponseEntity<Void> getExchangeGold(@RequestParam("token") String token,
			@RequestParam("duihuan") String duihuan,@RequestParam("type") Integer type) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			Integer q = this.exchangeRecordService.getExchangeGold(user, duihuan, type);
			if (q == 1) {

				return ResponseEntity.status(HttpStatus.CREATED).build();// 兑换成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();// 输错了

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 *
	 * 13.现金提现
	 * 
	 * @param token
	 * @param jine
	 *            提现金额
	 * @param money
	 *            实际到账
	 * @param servicePrice
	 *            手续费
	 * 
	 * 
	 */

	@RequestMapping(value = "getTiXian", method = RequestMethod.POST)
	public ResponseEntity<Void> getTiXian(@RequestParam("token") String token, @RequestParam("jine") double jine) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			Integer q = this.putForwardRecordService.getTiXian(user, jine);
			if (q == 1) {

				return ResponseEntity.status(HttpStatus.CREATED).build();// 提现请求提交成功，两至三个工作日内到账
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();// no!

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 
	 * 14.获取验证码
	 * 
	 * @param uPhone
	 * @return
	 */
	@RequestMapping(value = "getAuthCode", method = RequestMethod.GET)
	public ResponseEntity<String> getAuthCode(@RequestParam("uPhone") String uPhone) {
		try {
			Integer i = DuanXinUtil.test(uPhone);
			// 发送短信
			// Alidayu.getAuthCode(uPhone, no);
			// 成功201
			System.out.println("你的验证码是：" + "\t" + i);
			return ResponseEntity.ok(i + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
/*************************************帮助中心***************************************************************/	

	/**
	 *
	 * 8.通过token 反馈意见
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "addFeedback", method = RequestMethod.POST)
	public ResponseEntity<Void> addFeedback(@RequestParam("token") String token,
			@RequestParam("content") String content) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			Integer q = this.feService.addFeedback(user, content);
			if (q == 1) {

				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 15.玩法介绍
	 * 
	 * 1:共享福袋玩法介绍
	 * 2:天天抢券玩法介绍
	 * 3:金豆和金额转换说明
	 * 
	 * @param uPhone
	 * @return
	 */
	@RequestMapping(value = "getQangQuanPlay", method = RequestMethod.GET)
	public ResponseEntity<Gameplay> getQangQuanPlay(@RequestParam("type") Long type) {
		try {
			Gameplay game=this.gameplayService.getQangQuanPlay(type);
			if(game!=null) {
				
				return ResponseEntity.ok(game);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	
	/*****************************************************分销达人**************************************************************/
	/**
	 * 购买合伙人权限（分销达人）
	 * @param request
	 * @param token
	 * @param money
	 * @return
	 */
	@RequestMapping(value = "becomeMemberPay",method = RequestMethod.POST)
	public ResponseEntity<String> becomeMemberPay(HttpServletRequest request,@RequestParam("token")String token,@RequestParam("money")Integer money
			,@RequestParam("openid")String openid){
		try {

			SimpleDateFormat df = new SimpleDateFormat("hhmmss");
			String dd = df.format(new Date());
			int c = (int) ((Math.random() * 9 + 1) * 10000);
			String ss = "xzdpdk" + dd + c;
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			String ipAddress = null;
			if (request.getHeader("x-forwarded-for") == null) {
				ipAddress = request.getRemoteAddr();

			} else {
				if (request.getHeader("x-forwarded-for").length() > 15) {
					String[] aStr = request.getHeader("x-forwarded-for").split(",");
					ipAddress = aStr[0];
				} else {
					ipAddress = request.getHeader("x-forwarded-for");
				}
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.uService.queryOne(us);
			this.rService.expire(key, REDIS_TIME);
			if(user==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			String times = System.currentTimeMillis() + "";// 生成时间戳
			// 订单标题
			String title = "分销达人-成为分销合伙人";
			String appids = "wx903822fb7d2f3961";
			// Onlyyou521314Onlyyou21314Onlyyou
			// 秘钥：9fbd99835218c118659f5abcb5fc657b

			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			packageParams.put("appid", appids);
			packageParams.put("mch_id", "1504111201");
			packageParams.put("nonce_str", times);// 时间戳
			packageParams.put("body", title);// 支付主体
			packageParams.put("out_trade_no", ss);// 编号
			packageParams.put("total_fee", money);// 价格
			packageParams.put("spbill_create_ip", ipAddress);// 这里之前加了ip，但是总是获取sign失败，原因不明，之后就注释掉了
			packageParams.put("notify_url", "/qian/store/paynotify");// 支付返回地址，
			packageParams.put("trade_type", "JSAPI");// 这个api有，固定的
			packageParams.put("openid", openid);// openid

			String stringSignTemp = "QjW0RiqXwtca3TUkr4SS5yAhijKaKf3r";

			String sign = PayCommonUtil.createSign("UTF-8", packageParams, stringSignTemp);
			packageParams.put("sign", sign);
			// 转成xml字符串
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
			@SuppressWarnings("rawtypes")
			Map map = XMLUtil.doXMLParse(resXml);
			System.out.println("map:>>>>>"+map);
			String prepay_id = (String) map.get("prepay_id");
			if(prepay_id==null) {
				
				String openid1=user.getOpenId();
				SortedMap<Object, Object> packageParams1 = new TreeMap<Object, Object>();
				packageParams1.put("appid", appids);
				packageParams1.put("mch_id", "1504111201");
				packageParams1.put("nonce_str", times);// 时间戳
				packageParams1.put("body", title);// 支付主体
				packageParams1.put("out_trade_no", ss);// 编号
				packageParams1.put("total_fee", money);// 价格
				packageParams1.put("spbill_create_ip", ipAddress);// 这里之前加了ip，但是总是获取sign失败，原因不明，之后就注释掉了
				packageParams1.put("notify_url", "/qian/store/paynotify");// 支付返回地址，
				packageParams1.put("trade_type", "JSAPI");// 这个api有，固定的
				packageParams1.put("openid", openid1);// openid

				String stringSignTemp1 = "QjW0RiqXwtca3TUkr4SS5yAhijKaKf3r";

				String sign1 = PayCommonUtil.createSign("UTF-8", packageParams1, stringSignTemp1);
				packageParams.put("sign", sign1);
				// 转成xml字符串
				String requestXML1 = PayCommonUtil.getRequestXml(packageParams1);
				String resXml1 = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML1);
				@SuppressWarnings("rawtypes")
				Map map1 = XMLUtil.doXMLParse(resXml1);
				prepay_id = (String) map1.get("prepay_id");
				
				
			}
			SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();

			packageP.put("appId", appids);// ！！！注意，这里是appId,上面是appid，
			packageP.put("nonceStr", times);// 时间戳
			packageP.put("package", "prepay_id=" + prepay_id);// 必须把package写成 "prepay_id="+prepay_id这种形式
			packageP.put("signType", "MD5");// paySign加密
			packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
			// 得到paySign
			String paySign = PayCommonUtil.createSign("UTF-8", packageP, stringSignTemp);
			packageP.put("paySign", paySign);
			// 将packageP数据返回给小程序
			Gson gson = new Gson();
			
			String json = gson.toJson(packageP);
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 *
	 * 分销达人 返现
	 * 
	 * @param token
	 * @return   该用户id
	 */
	@RequestMapping(value = "backMoney", method = RequestMethod.GET)
	public ResponseEntity<String> backMoney(@RequestParam("token") String token) {

		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.aService.queryOne(us);// 查询出用户a
			Integer q = this.fenxiangService.backMoney(user);
			String uId=user.getUserId();
			if (q == 1) {

				return ResponseEntity.status(HttpStatus.CREATED).body(uId);//返现成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

	
	
}
