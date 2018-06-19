package com.yang.controller;

import java.math.BigDecimal;
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
import com.utils.HttpUtil;
import com.utils.PayCommonUtil;
import com.utils.XMLUtil;
import com.yang.model.DakaRule;
import com.yang.model.Dakaleixing;
import com.yang.model.Rules;
import com.yang.model.AppUser;
import com.yang.service.AppUserService;
import com.yang.service.DakaService;
import com.yang.service.DakaleixingService;
import com.yang.service.RedisService;
import com.yang.service.RulesService;



/**
 * @author YJG
 *
 * 1.通过code获取微信个人信息；
 *  2.点击早起打卡模块，判断当天是否参与了打卡挑战 
 *    3.查询所有的规则描述
 *      4.查询挑战区域类型列表(显示区域金额)
 *        5.判断打卡是否成功，并修改打卡时间
 *		    6.参与挑战打卡
 *		      7.参与打卡微信支付
 *              9.早起挑战打卡结束后，选出最佳手气者
 *		          10.早起挑战打卡结束后，选出早起之星
 *		             11.早起挑战打卡结束后，筛选出连续打卡次数最多者
 *		                13.查询账户余额（金豆）
 *		                  14.今日参与打卡人数和今日成功打卡人数
 *		                    15.明日参与挑战打卡人数和挑战金奖池
 *		                      17.今日参与打卡人数和今日成功打卡人总人数
 *
 *
 */			
@CrossOrigin
@Controller
@RequestMapping(value = "qian/user/")
public class DakaController{

	@Autowired
	private AppUserService service;// 用户信息
	@Autowired
	private DakaService dService;// 打卡表
	@Autowired
	private DakaleixingService dlService;// 打卡区域类型
	@Autowired
	private RulesService rulService;// 打卡规则描述
	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;
//	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;
	/**
	 * 2.判断当天是否参与打卡挑战
	 * 
	 * @return 状态码
	 */
	@RequestMapping(value = "getDaka", method = RequestMethod.GET)
	public ResponseEntity<Integer> getDaka(@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// token不正确 返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);
			if(user==null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
			}
			Integer i = this.dService.getweidaka(user);
				return ResponseEntity.status(HttpStatus.CREATED).body(i);// 已参加 返回201
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 3：查询所有的规则描述
	 * 
	 * @return list列表
	 */
	@RequestMapping("getRules")
	public ResponseEntity<List<Rules>> getRules() {
		try {
			List<Rules> list = this.rulService.queryAll();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 4.查询挑战区域类型列表(显示区域金额)
	 * 
	 * @return list
	 */
	@RequestMapping(value = "getdakaleixing", method = RequestMethod.GET)
	public ResponseEntity<List<Dakaleixing>> getdakaleixing(@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String date = this.rService.get(key);
			if (date == null) {
				// token不正确 返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(date);
			AppUser user = this.service.queryOne(us);
			List<Dakaleixing> q = this.dlService.getAll(user);
			if(q!=null) {
				return ResponseEntity.ok(q);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 5.判断打卡是否成功，并修改打卡时间
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "updatedakaByuser", method = RequestMethod.GET)
	public ResponseEntity<Void> updatedakaByuser(@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String date = this.rService.get(key);
			if (date == null) {
				// token不正确 返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(date);
			AppUser user = this.service.queryOne(us);
			Integer s = this.dService.selectStatus(user);
			if (s == 1) {
				return ResponseEntity.status(HttpStatus.CREATED).build();// 打卡成功 返回201
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();// 打卡失败 返回202
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 6.参与挑战打卡
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "joinDaka", method = RequestMethod.POST)
	public ResponseEntity<Void> joinDaka(@RequestParam("token") String token, @RequestParam("type_id") Long id2,
			@RequestParam("jine") String  jine, @RequestParam("fangshi") Integer fangShi) {

		try {
			String key = "TOKEN_" + token;
			String date = this.rService.get(key);
			if (date == null) {
				// token不正确 返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(date);
			AppUser user = this.service.queryOne(us);
			int a = dService.injinDaka(user, id2, jine, fangShi);
			if (a == 1) {
				return ResponseEntity.status(HttpStatus.CREATED).build();// 参与挑战成功 返回201
			}else if(a==2) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();// 参与挑战失败 返回202
			}
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();//支付失败 返回424
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 7.微信支付
	 * 
	 * @param token
	 * @param money
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "weixinpay", method = RequestMethod.POST)
	public ResponseEntity<String> weixinpay(@RequestParam("token") String token, HttpServletRequest request,
			@RequestParam("money") Integer money,@RequestParam("openid") String openid) {
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
			AppUser user = this.service.queryOne(us);
			if(user==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			this.rService.expire(key, REDIS_TIME);
			String times = System.currentTimeMillis() + "";// 生成时间戳
			// 订单标题
			String title = "小众点评-参与挑战";
			String appids = "wx9d82360ba0304046";
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
			packageParams.put("notify_url", "/qian/user/paynotify");// 支付返回地址，
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
			String prepay_id = (String) map.get("prepay_id");
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
	 * 微信支付成功后调用接口
	 * @return
	 */
	@RequestMapping("paynotify")
	public String paynotify() {
		       return "<xml><return_code><![CDATA[ SUCCESS ]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
	
	/**
	 * 9.早起挑战打卡结束后，选出最佳手气者
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "goldHigh", method = RequestMethod.GET)
	public ResponseEntity<List<DakaRule>> goldHigh(@RequestParam("typeId") Long typeId) {
		try {
			List<DakaRule> list = dService.goldHighOne(typeId);
			if (list!=null&&!list.isEmpty()) {
				
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 10.早起挑战打卡结束后，选出早起之星
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getup", method = RequestMethod.GET)
	public ResponseEntity<List<DakaRule>> getup(@RequestParam("typeId") Long typeId) {
		try {
			List<DakaRule> list = dService.getUpEarly(typeId);
			
			if (list!=null&&!list.isEmpty()) {
				
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 11.早起挑战打卡结束后，筛选出连续打卡次数最多者
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "lianXuDaka", method = RequestMethod.GET)
	public ResponseEntity<List<DakaRule>> lianXuDaka(@RequestParam("typeId") Long typeId) {
		try {
			List<DakaRule> list = dService.lianXuDakaNum(typeId);
			if (list!=null&&!list.isEmpty()) {
				
				return ResponseEntity.ok(list);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 13.查询账户余额（金豆）
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getUserPice", method = RequestMethod.GET)
	public ResponseEntity<Map<String, BigDecimal>> getUserPice(@RequestParam("token") String token) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		String key = "TOKEN_" + token;
		String date = this.rService.get(key);
		if (date == null) {
			// token不正确 返回204
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		try {
			
			AppUser user = this.service.getusers(date);
			if(user==null) {
				
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);// 返回map集合
				}
			Integer gral=user.getUserIntegral();
			BigDecimal money1= user.getMoney();
			if(gral==null&&money1==null) {
				BigDecimal c=new BigDecimal("0");
				map.put("gold", c);
				map.put("money",c);
				return ResponseEntity.ok(map);// 返回map集合
			}else if(money1==null) {
				BigDecimal d=new BigDecimal("0");
				BigDecimal dd=new BigDecimal(String.valueOf(gral));
				map.put("gold", dd);
				map.put("money", d);
				return ResponseEntity.ok(map);// 返回map集合
			}
			BigDecimal dd=new BigDecimal(String.valueOf(gral));
			map.put("gold", dd);
			map.put("money", money1);
			return ResponseEntity.ok(map);// 返回map集合
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 14.今日参与打卡人数和今日成功打卡人数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getDkNum", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Integer>> getDkNum(@RequestParam("typeId") Long id,
			@RequestParam("jine") Integer gold) {

		try {
			Map<String, Integer> map = dService.getDkToday(id, gold);
			if (map == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 *
	 * 15.明日参与挑战打卡人数和挑战金奖池
	 * 
	 * @param
	 * @return
	 *
	 */
	@RequestMapping(value = "getAfterDaka", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Integer>> getAfterDaka(@RequestParam("typeId") Long id,
			@RequestParam("jine") Integer gold) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			map = this.dService.getAfterDk(id, gold);
			if (map == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();// map为空，返回204
			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	/**
	 * 17.今日参与打卡人数和今日成功打卡人总人数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getDkZongNum", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Integer>> getDkZongNum() {
		try {
			Map<String, Integer> map = dService.getDkTodayZongNum();
			if (map == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}
	
	/***
	 * 
	 * @return
	 */
	
	/*@RequestMapping(value = "getFenPei", method = RequestMethod.GET)
	public ResponseEntity<Integer> getFenPei() {
	
		Integer list =this.dService.getdakaGold();
		
			return ResponseEntity.status(HttpStatus.CREATED).body(list);
		
		
	 }
	*/
	}
