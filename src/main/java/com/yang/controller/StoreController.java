package com.yang.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.utils.HttpUtil;
import com.utils.PayCommonUtil;
import com.utils.XMLUtil;
import com.yang.model.AppUser;
import com.yang.model.Order;
import com.yang.model.OrderCommentLabel;
import com.yang.model.RobVoucher;
import com.yang.model.Store;
import com.yang.model.StoreComment;
import com.yang.model.Volume;
import com.yang.service.AppUserService;
import com.yang.service.MemberUserService;
import com.yang.service.OrderCommentLabelService;
import com.yang.service.OrderService;
import com.yang.service.RedisService;
import com.yang.service.StoreCommentService;
import com.yang.service.StoreService;
import com.yang.service.VolumeService;
import com.yang.service.VoucherService;

/**   美食        
 * @author YJG
 * 1.通过code获取微信个人信息；
 *   2.获取主页面信息展示
 *    3.根据店铺id查看店铺详情
 *     4.根据店铺id 对商家评论
 *      5.根据消息ID查询评论加回复列表
 * 		 6.根据评论ID，商家回复评论
 *        7.会员抢券
 *       000*.成为超级会员
 *           8.查询抵用券活动列表
 *            9.根据id查询使用规则
 *            10*.微信支付完成后调用方法
 *              11.微信支付    money:余额支付的 RMB  gold:支付的金豆    vocherId:抵用券的id jine:微信支付的 RMB
 *               0101. 微信支付成功后，修改订单状态。
 *                 12.继续支付
 *                  13.根据用户Id和订单状态查询订单列表
 *                    14.根据订单查询订单详情
 * 
 * 
 * 
 */
@CrossOrigin
@Controller
@RequestMapping(value = "qian/store/")
public class StoreController {
	@Autowired
	private VoucherService voucherService;//代金券
	@Autowired
	private AppUserService service;// 用户信息
	@Autowired
	private StoreService storeService;// 商店信息
	@Autowired
	private StoreCommentService storeCommetnService;// 商户评论信息
	@Autowired
	private MemberUserService memberUserService;// 会员用户信息
	@Autowired
	private VolumeService volumeService;// 团购券/现金券
	
	@Autowired
	private OrderCommentLabelService orlaService;//便签词
	
	@Autowired
	private OrderService orderService;//訂單
	
	//private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;

	/**
	 *
	 * 2.获取主页面信息展示
	 * 
	 * @param mapX,纬度
	 *            mapY;和经度
	 * @return
	 */
	@RequestMapping(value = "getStore", method = RequestMethod.POST)
	public ResponseEntity<List<Store>> getStore(@RequestParam("mapX") double mapX, @RequestParam("mapY") double mapY,
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "distance", defaultValue = "0") Integer distance,
			@RequestParam(value = "T", defaultValue = "5") Integer T) {
		try {
			List<Store> s = this.storeService.selectStore(mapX, mapY, type, distance, T);
			if (s != null&&!s.isEmpty()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(s);// 返回201 ,列表
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();// 神马也米有！返回202
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 3根据店铺id查看店铺详情
	 * 
	 * @param sId
	 * @return
	 */
	@RequestMapping(value = "getStoreDetailById", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getStoreDetailById(@RequestParam("sId") Long sId) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map = this.storeService.getShopDetailById(sId);
			if(map.isEmpty()) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 4.根据店铺id 对商家评论
	 * 
	 * @param sId
	 * @return
	 */
	@RequestMapping(value = "commentStoreById", method = RequestMethod.POST)
	public ResponseEntity<Void> commentStoreById(StoreComment sc) {

		try {
			boolean store = this.storeCommetnService.getcommentStoreById(sc);
			if (store) {
				return ResponseEntity.status(HttpStatus.CREATED).build();// 201 评论成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();// 202 评论不成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 5.根据商铺id 加载评论列表
	 * 
	 * @param id
	 * @return list
	 *
	 */
	@RequestMapping(value = "getStoreCommentList", method = RequestMethod.POST)
	public ResponseEntity<List<Order>> getStoreCommentList(@RequestParam("storeId") Long storeId) {
		try {
			
			List<Order> oder =this.orderService.getStoreCommentList(storeId);
			 if(oder!=null&&!oder.isEmpty()) {
				 
				return ResponseEntity.ok(oder);
			 }
			 return ResponseEntity.status(HttpStatus.ACCEPTED).body(oder);// 202 没有评论

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 错误
	}
	
	
	
	
	
//  ** ********** --------------------------------            会员抢券                                     ------------------------------------*
	/**
	 * 000*.成为超级会员
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "becomeMember", method = RequestMethod.GET)
	public ResponseEntity<Void> becomeMember(@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser user = this.service.getusers(data);
			boolean d = this.memberUserService.getBecomeMember(user);
			if (d) {

				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	/**
	 * 成为会员支付接口调用
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
			AppUser user = this.service.queryOne(us);
			this.rService.expire(key, REDIS_TIME);
			if(user==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			String times = System.currentTimeMillis() + "";// 生成时间戳
			// 订单标题
			String title = "美食";
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
			System.out.println("json>>>>:"+json);
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	@RequestMapping("paynotify")
	public String paynotify() {
		       return "<xml><return_code><![CDATA[ SUCCESS ]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}
	
	/**
	 * 7.会员抢券
	 * 
	 * 
	 * @param (现金券，每天发布五张价值十元的现金券，供会员用户抢券抢券
，没有抢到就送一百金豆)
	 */
	@RequestMapping(value = "getVocher", method = RequestMethod.POST)
	public ResponseEntity<Integer> getVocher(@RequestParam("token") String token,
			@RequestParam(value = "uptype", defaultValue = "0") Integer uptype,@RequestParam("id") Long id) {
		try {
			String key = "TOKEN_" + token;
			String date = this.rService.get(key);
			if (date == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser user = this.service.getusers(date);
			Integer d = this.voucherService.getXianVocher(user, uptype,id);
			// 1 恭喜抢券成功
		   // 2  谢谢参与，返回值 202,
			
		  // 208 ,3,那么券已抢完，改天来
		  // 会员已经过期，状态码，100  返回 4 ,重新购买
		
			return ResponseEntity.ok(d);// 返回 5，你还不是会员，请购买会员参与抢券。
			// 返回 6，你已经抢过了，请改天再来，可以在个人中心查看券
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 8.查询抵用券活动列表
	 * 
	 * @param
	 */
	@RequestMapping(value = "getVocherList", method = RequestMethod.GET)
	public ResponseEntity<List<RobVoucher>> getVocherList(@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String date = this.rService.get(key);
			if (date == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser user = this.service.getusers(date);
			List<RobVoucher> d = this.voucherService.getVocherList(user);
			if (d != null && !d.isEmpty()) {

				return ResponseEntity.status(HttpStatus.CREATED).body(d);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 9.根据id查询使用规则
	 * 
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "getUseRule", method = RequestMethod.GET)
	public String getUseRule(@RequestParam("id") Long id, HttpServletResponse re) {

		re.setCharacterEncoding("utf-8");
		re.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			String d = this.voucherService.getUseRule(id);
			re.getWriter().print(d);
			re.getWriter().flush();
			re.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//    ** ********** ------------------------        订单       支付接口                     ------------------------------------*
	
	/**
	 * 10.查询团购券/或者现金券的详情
	 * @param id
	 * @param typeId// 1团购 2现金
	 * @return
	 */
	@RequestMapping(value = "getVolumeById", method = RequestMethod.GET)
	public ResponseEntity<Volume> getVolumeById(@RequestParam("id") String id,@RequestParam("typeId") Integer typeId) {
		try {
			Volume a = this.volumeService.getVolumeById(id,typeId);
			if (a != null) {

				return ResponseEntity.ok(a);// 成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);//202,没存在
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	/**
	 * 11.用户下单
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "postUserOrderByToken", method = RequestMethod.POST)
	public ResponseEntity<String> postUserOrderByToken(@RequestParam("token") String token, Order oder) {
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser us = new AppUser();
			us.setUnionId(data);
			
			AppUser user = this.service.queryOne(us);
			String no=this.service.postUserOrderByToken(user,oder);
			if(!"".equals(no)) {
				return ResponseEntity.ok(no);//201 成功、
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);//202 失败
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	} 
	/**
	 * 10,微信支付接口
	 * @param token
	 * @param oderNO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "weixinpay", method = RequestMethod.POST)
	public ResponseEntity<Map<Object,Object>> weixinpay(@RequestParam("token") String token,@RequestParam("oderNO") String oderNO, @RequestParam("openid") String openid,HttpServletRequest request) {
				Map<Object,Object> map=new HashMap<Object,Object>();
				try {
					
					map = this.storeService.storeWeixinPay(token,oderNO,openid,request);
					
				if(map!=null&&!map.isEmpty()) {
					
					return ResponseEntity.ok(map);
				}
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 11*.微信支付完成后调用方法
	 * 
	 * @param token
	 * @param money
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "weixin")
	public ResponseEntity<Void> weixin() {
		try {

			System.out.println("-------------支付成功-------------");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 10,微信支付后存入购买信息
	 * @param token
	 * @param oderNO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addOrderList", method = RequestMethod.POST)
	public ResponseEntity<Void> addOrderList(@RequestParam("token") String token,@RequestParam("oderNO") String oderNO) {
				try {
					String key = "TOKEN_" + token;
					String data = this.rService.get(key);
					if (data == null) {
						return null;
					}
					//查出交易用户
					AppUser us = new AppUser();
					us.setUnionId(data);
					AppUser user =this.service.queryOne(us);
					if(user==null) {return null;}//没有该用户
					String uId=user.getUserId();
					//根据订单号查找到订单详情
				    Order or = new Order();
				    or.setOrderno(oderNO);//订单编号
				    or.setOrderuserid(uId);//买家id
					Order o=this.orderService.queryOne(or);
					if(o==null) {
						
						 ResponseEntity.status(HttpStatus.ACCEPTED).build();
						}
					
					BigDecimal money=o.getPrice();//使用的余额
					Integer gold=o.getGold();//使用的金豆
					String vocherid=o.getVoucherId();
					
					BigDecimal jine=o.getmFinalmoney();//微信支付金额
					Integer a=this.service.addOrderAfterPlay(oderNO,user, money, gold, vocherid, jine);
					
					if(a!=1) {
						 ResponseEntity.status(HttpStatus.ACCEPTED).build();;
					}
				
				  return ResponseEntity.status(HttpStatus.CREATED).body(null);
				
				}catch(Exception e) {
					e.printStackTrace();
					}

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 12.根据用户id及订单状态查询订单记录
	 */
	@RequestMapping(value="getUserOrderByState",method=RequestMethod.POST)
	public ResponseEntity<List<Order>> getUserBespokeByState(
			@RequestParam("token")String token,
			@RequestParam("state")Integer state){
		try {
		   String key = "TOKEN_" + token;
		   String data = this.rService.get(key);
		   if (data == null) {
			// 未登录 返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		   }
		   AppUser us = new AppUser();
			us.setUnionId(data);
			AppUser user = this.service.queryOne(us);
		    List<Order> list = this.service.getUserOrderListByState(user,state);
		    if(list!=null&&!list.isEmpty()) {

				return ResponseEntity.ok(list);
		    }
			
		    return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	

	/**
	 * 13.根据订单编号查询订单详情
	 * 
	 * 
	 */
	@RequestMapping(value = "getOrderDetailByOrderId", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrderDetailByOrderId(@RequestParam("order") String order) {
		try {
			Order a = this.service.getOrderDetailByOrderId(order);
			if (a != null) {

				return ResponseEntity.status(HttpStatus.CREATED).body(a);// 成功
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 14.根据用户id查询返回用户订单列表
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "getUserOrderListByToken", method = RequestMethod.GET)
	public ResponseEntity<List<Order>> getUserOrderListByToken(
			@RequestParam("token") String token) {
		try {
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			AppUser use = new AppUser();
			use.setUnionId(data);
			AppUser u = this.service.queryOne(use);
			List<Order> us = this.service.getUserOrderListByToken(u.getUserId());
			if(us!=null&&!us.isEmpty()) {
				return ResponseEntity.ok(us);
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(us);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 出错500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

	/**
	 * 15.根据订单id删除该订单
	 * 
	 * 
	 */
	@RequestMapping(value = "deteleorderByid", method = RequestMethod.GET)
	public ResponseEntity<Void> deteleorderByid(@RequestParam("id") Long id) {
		try {
			 this.service.deteleorderByid(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 16.订单申请退款
	 * 
	 * 
	 */
	@RequestMapping(value = "applicationForRefund", method = RequestMethod.POST)
	public ResponseEntity<Void> applicationForRefund(@RequestParam("token") String token,@RequestParam("id") String id,
			@RequestParam("resaon") String resaon,@RequestParam("content") String content) {
		try {
			 
			String key = "TOKEN_" + token;
			String data = this.rService.get(key);
			if (data == null) {
				// 不正确 返回204
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			 AppUser us = new AppUser();
				us.setUnionId(data);
				AppUser user = this.service.queryOne(us);
				
			Integer a=this.service.applicationForRefundById(user,id, resaon, content);
			if(a==1) {
				
				return ResponseEntity.status(HttpStatus.CREATED).body(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 17.订单完成后 评论订单
	 * 
	 * 
	 */
	@RequestMapping(value = "oderCommentByToken", method = RequestMethod.POST)
	public ResponseEntity<Void> oderCommentByToken(Order order,OrderCommentLabel orlabel) {
		try {
			 
			Integer a=this.service.oderCommentByToken(order,orlabel);
			if(a==1) {
				
				return ResponseEntity.status(HttpStatus.CREATED).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * 18.根据商铺id,查询标签词库
	 * 
	 * 
	 */
	@RequestMapping(value = "getCommentLabel", method = RequestMethod.GET)
	public ResponseEntity<OrderCommentLabel> getCommentLabel(@RequestParam("sId") Long sId) {
		try {
			 
			OrderCommentLabel ora=this.orlaService.getCommentLabelById(sId);
			if(ora!=null) {
				
				return ResponseEntity.status(HttpStatus.CREATED).body(ora);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	
	
	
}