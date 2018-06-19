package com.yang.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.utils.CompratorAverage;
import com.utils.CompratorDistanceBag;
import com.utils.CompratorLevel;
import com.utils.DateUtil;
import com.utils.Distance;
import com.utils.HttpUtil;
import com.utils.Httpseweixin;
import com.utils.MoneyRandom;
import com.utils.MyComprator;
import com.utils.PayCommonUtil;
import com.utils.StringUtils;
import com.utils.XMLUtil;
import com.yang.mapper.StoreMapper;
import com.yang.model.AppUser;
import com.yang.model.BagRecord;
import com.yang.model.ConsumeRecord;
import com.yang.model.Order;
import com.yang.model.Store;
import com.yang.model.TheBag;
import com.yang.model.Volume;
import com.yang.model.Voucher;

@Service
public class StoreService extends BaseService<Store> {
	Integer bian = 0;
	Boolean flag = false;
	@Autowired
	private StoreMapper storeMapper;//商铺
	@Autowired
	private RedisService rService;//Redis缓存
	@Autowired
	private AppUserService uService;//用户
	@Autowired
	private ConsumeRecordService consumeRecordService;//消费记录表
	@Autowired
	private AppUserService userService;//用户表
	@Autowired
	private OrderService orderService;//订单
	@Autowired
	private  VolumeService volumeService;//团购券或者代金券
	@Autowired
	private VoucherService voucherService;//抵用券
	@Autowired
	private TheBagService tbService;//福袋
	
	@Autowired
	private BagRecordService bagService;//红包记录
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

	/**
	 * 查询信息表根据三个条件，
	 *
	 */

	public List<Store> selectStore(double x1, double y1, String type, Integer distance, Integer T) {
		
		Store store = new Store();
		Example example = new Example(store.getClass());
		if (!"".equals(type)) {
			String type1=StringUtils.replaceBlank(type);
			example.createCriteria().andLike("type", "%" + type1 + "%");
		}
		if (distance!=0) {
			flag = true;
		}
		if (T!=5) {
			switch (T) {
			case 0:// 智能排序不做任何处理
				flag = true;
				break;
			case 1:// 理我距离最近
				flag = true;
				break;
			case 2:// 评价（等级最高）最好
				bian = 1;
				break;
			case 3:// 平均消费价位最高
				
				bian = 2;
				break;
			default:
				break;
			}
		}
		if (distance == 0 && T == 5&&"".equals(type)) {// 三个条件都为空时。
			List<Store> list = super.queryAll();
			List<Store> list1 = new ArrayList<Store>();
			if (list != null && !list.isEmpty()) {
				// 表示有数据，根据经纬度算出距离，放入集合
				for (Store shop : list) {
					double s = Distance.getDistance(y1, x1, Double.parseDouble(shop.getsLongitude()),
							Double.parseDouble(shop.getsLatitude()));
					// 将距离四舍五入存入shop
					shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
					list1.add(shop);
				}
				if(list1.size()==1) {
					return list1;
				}
				// 根据冒泡排序，将距离近的放在前面
				Collections.sort(list1, new MyComprator());
				return list1;	
			} else {
				return new ArrayList<>();
			}

		}
		if (flag) {// 表示满足三个或者两个条件
			List<Store> list = new ArrayList<Store>();
			if (!"".equals(type)) {

				list = this.storeMapper.selectByExample(example);
			} else {
				list = super.queryAll();
			}

			List<Store> list1 = new ArrayList<Store>();
			if (list != null && !list.isEmpty()) {
				// 表示有数据，根据经纬度算出距离，放入集合
				for (Store shop : list) {
					double s = Distance.getDistance(y1, x1, Double.parseDouble(shop.getsLongitude()),
							Double.parseDouble(shop.getsLatitude()));
					// 将距离四舍五入存入shop
					if (distance == 0) {
						shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
						list1.add(shop);
					}
					if (s < distance) {
						shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
						list1.add(shop);
					}
				}
				
				System.out.println(list1);
				if (bian == 1) {// 根据评价最高的排序
					if(list1.size()==1) {
						return list1;
					}else {
						Collections.sort(list1, new CompratorLevel());
						Collections.reverse(list1);
						return list1;
					}

				} else if (bian == 2) {
					if(list1.size()==1) {
						return list1;
					}else {
						// 根据冒泡排序，将平均消费最高的来排序，大到小
						Collections.sort(list1, new CompratorAverage());
						Collections.reverse(list1);
						return list1;
					}
					
				}
				if(list1.size()==1) {
					return list1;
				}else {
					Collections.sort(list1, new MyComprator());
					return list1;	
				}
				
			}
		}
		if (!flag && T == 5) {
			List<Store> list = new ArrayList<Store>();
  		if (!"".equals(type)) {

				list = this.storeMapper.selectByExample(example);
			} else {
				list = super.queryAll();
			}
			List<Store> list1 = new ArrayList<Store>();
			if (list != null && !list.isEmpty()) {
				// 表示有数据，根据经纬度算出距离，放入集合
				for (Store shop : list) {
					double s = Distance.getDistance(y1, x1, Double.parseDouble(shop.getsLongitude()),
							Double.parseDouble(shop.getsLatitude()));
					// 将距离四舍五入存入shop
					shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
					list1.add(shop);
				}
				if(list1.size()==1) {
					return list1;
				}
				Collections.sort(list1, new MyComprator());
				return list1;	
			}
		}
		return new ArrayList<>();

	}

	
	/**
	 * 根据id查询商铺的详情
	 *
	 */
	public Map<String,Object> getShopDetailById(Long sId) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 Volume vo=new Volume();
	     vo.setStoeid(String.valueOf(sId));
	     vo.setvType(1);//团购券
		 List<Volume> list=this.volumeService.queryListByWhere(vo);
		 vo.setvType(2);//现金券
		 List<Volume> list1=this.volumeService.queryListByWhere(vo);
		 Store s=super.queryById(sId);
		 if(list!=null) {
			 map.put("groupVoucher", list);
		 }
		 if(list1!=null) {
			 map.put("daijinVoucher", list1);
		 }
		 if(s!=null) {
			 map.put("store", s);
		 }
		 
		 
		return map;
	}

	/**
	 * 根据用户id查找店铺信息
	 *
	 */
	public Store getShopDetailByuId(String uId) {
		Store store = new Store();
		store.setShopUserId(uId);
		return super.queryOne(store);
	}

	/**
	 * 分页显示店铺信息
	 *
	 */
	public PageInfo<Store> getShopByPage(Integer page, Integer rows) {
		Store list = new Store();
		list.setCommentNum(rows);
		return super.queryPageListByWhere(page, rows, list);
	}

	/**
	 * 根据查询类容分页查询店铺信息
	 *
	 */

	public List<Store> getStore(double x1, double y1, String type) {

		if (type != null) {
			// 如果用户有搜索内容，
			Store so = new Store();
			Example example = new Example(so.getClass());
			example.setOrderByClause("created DESC ");
			example.createCriteria().andLike("type", "%" + type + "%");
			List<Store> list = this.storeMapper.selectByExample(example);
			List<Store> list1 = new ArrayList<Store>();
			if (list != null && !list.isEmpty()) {
				// 表示有数据，根据经纬度算出距离，放入集合
				for (Store shop : list) {
					double s = Distance.getDistance(y1, x1, Double.parseDouble(shop.getsLongitude()),
							Double.parseDouble(shop.getsLatitude()));
					// 将距离四舍五入存入shop
					shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
					list1.add(shop);
				}
				// 根据冒泡排序，将距离近的放在前面
				for (int i = 0; i < list1.size() - 1; i++) {
					flag = true;
					for (int j = 0; j < list1.size() - i - 1; j++)// j开始等于0，
					{
						if (list1.get(j).getsDistance() > list1.get(j + 1).getsDistance()) {
							Store st = list1.get(j);
							list1.set(j, list.get(j + 1));
							list1.set(j + 1, st);
							flag = false;
						}
					}
					if (flag) {
						break;
					}
				}
				return list1;
			} else {
				return null;
			}

		} else {
			// 如果用户没有搜索，那么默认搜索类型为空
			List<Store> list = super.queryAll();
			List<Store> list1 = new ArrayList<Store>();
			if (list != null && !list.isEmpty()) {
				// 表示有数据，根据经纬度算出距离，放入集合
				for (Store shop : list) {
					double s = Distance.getDistance(y1, x1, Double.parseDouble(shop.getsLongitude()),
							Double.parseDouble(shop.getsLatitude()));
					// 将距离四舍五入存入shop
					shop.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
					list1.add(shop);
				}
				// 根据冒泡排序，将距离近的放在前面
				for (int i = 0; i < list1.size() - 1; i++) {
					flag = true;
					for (int j = 0; j < list1.size() - i - 1; j++)// j开始等于0，
					{
						if (list1.get(j).getsDistance() > list1.get(j + 1).getsDistance()) {
							Store st = list1.get(j);
							list1.set(j, list.get(j + 1));
							list1.set(j + 1, st);
							flag = false;
						}
					}
					if (flag) {
						break;
					}
				}
				// PageHelper.startPage(1, 10);
				// PageInfo<Store> page=new PageInfo<Store>(list1);
				return list1;
			} else {
				return null;
			}

		}

	}
	/**
	 * 派对美食
	 * @param code
	 * @return
	 */
	public JSONObject gerJsonByMeiShi(String code) {
		
		 //小程序唯一标识   (在微信小程序管理后台获取)  
	    String wxspAppid = "wx903822fb7d2f3961";  
	    //小程序的 secret (在微信小程序管理后台获取)  
	    String wxspSecret = "b356856203c5bfa1278c98a54d5ea476";  
	    //授权（必填）  
	    String grant_type = "authorization_code";  
	    //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////  
	    //请求参数  
	    String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;  
	    //发送请求  
	    String sr = Httpseweixin.get("https://api.weixin.qq.com/sns/jscode2session", params);  
	    //解析相应内容（转换成json对象）  
	    JSONObject json = JSONObject.parseObject(sr);
		
	    return json;
	}
	/**
	 * 调用支付接口
	 * @param token
	 * @param order
	 * @param request
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */

	public Map<Object, Object> storeWeixinPay(String token,String order,String openid,HttpServletRequest request) throws JDOMException, IOException {
		 Map<Object, Object> map=new  HashMap<Object, Object>();
		
		String key = "TOKEN_" + token;
		String data = this.rService.get(key);
		if (data == null) {
			return null;
		}
		String ipAddress = null;
		if (request.getHeader("x-forwarded-for") == null) {//获取iP地址
			ipAddress = request.getRemoteAddr();
			System.out.println("iP地址1:"+ipAddress);

		} else {
			if (request.getHeader("x-forwarded-for").length() > 15) {
				String[] aStr = request.getHeader("x-forwarded-for").split(",");
				ipAddress = aStr[0];
				System.out.println("iP地址2:"+ipAddress);
			} else {
				ipAddress = request.getHeader("x-forwarded-for");
				System.out.println("iP地址3:"+ipAddress);
			}
		}
		//查出交易用户
		AppUser us = new AppUser();
		us.setUnionId(data);
		AppUser user =this.uService.queryOne(us);
		if(user==null) {return null;}//没有该用户
		String uId=user.getUserId();
		//根据订单号查找到订单详情
	    Order or = new Order();
	    or.setOrderno(order);//订单编号
	    or.setOrderuserid(uId);//买家id
		Order o=this.orderService.queryOne(or);
		if(o==null) {return map;}

		Integer fanshi=o.getZhiFuType();//支付方式
		String vocherCode=o.getVoucherId();//使用抵用券
		Integer gold1=o.getGold();//使用的金豆
		BigDecimal bbk=o.getPrice();//订单使用的余额
		Integer jindou=user.getUserIntegral();//账户剩余金豆
		BigDecimal mony=user.getMoney();//余额
		Integer	num=mony.compareTo(bbk);//判断余额是否够
		
		if("111".equals(vocherCode)) {
			if(jindou<gold1||num==-1) {
				
				//任意一个条件满足，那么就删除订单，重新生成
				this.orderService.deleteByWhere(or);//删除订单
				
				map.put("Reason", "失败原因：金豆不足/余额不足，重新下单");
				return map;
			}
			
		}else {
			Voucher vch = new Voucher();
			vch.setvUserId(uId);
			vch.setCouponCode(vocherCode);// 券码
			Voucher voc=this.voucherService.queryOne(vch);
			Integer status=voc.getvStatus();//是否过期（默认0：未过期，1：已过期）
			
			if(status==1||jindou<gold1||num==-1) {
				
				//任意一个条件满足，那么就删除订单，重新生成
				this.orderService.deleteByWhere(or);//删除订单
				
				map.put("Reason", "失败原因：抵用券过期/金豆不足/余额不足，重新下单");
				return map;
			}
		}
		BigDecimal money=o.getPrice();//使用的余额
		Integer gold=o.getGold();//使用的金豆
		String vocherid=o.getVoucherId();
		if (fanshi == 1) { // 余额支付
			AppUser a = this.uService.weixinpay( money, order,user, gold, vocherid);
			if (a == null) {
				return map;
			}
				this.rService.set("TOKEN_" + token, MAPPER.writeValueAsString(a), REDIS_TIME);
			map.put("success", "支付成功");
			map.put("orderNo", order);
			return map;
		} else if (fanshi == 2) { // 剩余金豆支付
			
			AppUser a = this.uService.weixinpayByGold(gold, order, user, vocherid);
			if (a == null) {
				return map;
			}
				this.rService.set("TOKEN_" + token, MAPPER.writeValueAsString(a), REDIS_TIME);
				map.put("success", "支付成功");
				map.put("orderNo", order);
				return map;
		}
		BigDecimal jine=o.getmFinalmoney();//微信支付金额
		this.rService.expire(key, REDIS_TIME);
		String times = System.currentTimeMillis() + "";// 生成时间戳
		// 订单标题
		String title = "小众点评-订单支付";
		String appids = "wx903822fb7d2f3961";
		// Onlyyou521314Onlyyou21314Onlyyou
		// 秘钥：9fbd99835218c118659f5abcb5fc657b

		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", appids);
		packageParams.put("mch_id", "1504111201");
		packageParams.put("nonce_str", times);// 时间戳
		packageParams.put("body", title);// 支付主体
		packageParams.put("out_trade_no", order);// 编号
		packageParams.put("total_fee", jine);// 价格
		packageParams.put("spbill_create_ip", ipAddress);// 这里之前加了IP，但是总是获取sign失败，原因不明，之后就注释掉了
		packageParams.put("notify_url", "/qian/store/weixin");// 支付返回地址，
		packageParams.put("trade_type", "JSAPI");// 这个API有，固定的
		packageParams.put("openid", openid);// openId

		String stringSignTemp = "QjW0RiqXwtca3TUkr4SS5yAhijKaKf3r";

		String sign = PayCommonUtil.createSign("UTF-8", packageParams, stringSignTemp);
		packageParams.put("sign", sign);
		// 转成xml字符串
		String requestXML = PayCommonUtil.getRequestXml(packageParams);
		String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);

		@SuppressWarnings("rawtypes")
		Map map1 = XMLUtil.doXMLParse(resXml);
		String prepay_id = (String) map1.get("prepay_id");
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
		packageParams.put("json", json);
	
		return packageParams;
	}
	/**
	 * 查找最近距離的发布了福袋的商家，并展示
	 * @param mapX
	 * @param mapY
	 * @param distance
	 * @return
	 */

	public List<TheBag> selectStoreBag(double mapX, double mapY) {
		List<TheBag> list = this.tbService.queryAll();
		List<TheBag> list1 = new ArrayList<TheBag>();
		if (list != null && !list.isEmpty()) {
			// 表示有数据，根据经纬度算出距离，放入集合
			TheBag tb=new TheBag();
			for (TheBag bag : list) {
				
				Long stId=bag.getStoreId();
				Store store=super.queryById(stId);
				double s = Distance.getDistance(mapY, mapX, Double.parseDouble(store.getsLongitude()),
						Double.parseDouble(store.getsLatitude()));
					String sid=bag.getUserid();
					tb.setStoreId(stId);
					tb.setUserid(sid);
					tb.setStatus(0);//没有抢完
					tb.setExamStatus(1);//发布福袋审核状态，0：不显示 1：显示
					TheBag lis= this.tbService.queryOne(tb);//查看是否发布福袋是否还有效
					if(lis!=null) {
						Integer distance=lis.getJuli();//每个福袋可见范围
						if(s<distance) {
							// 将距离四舍五入存入shop
							bag.setsDistance(Integer.parseInt(new java.text.DecimalFormat("0").format(s)));
							
							list1.add(bag);
						}
						
					}
				
			}
			if(list1.size()==1) {
				return list1;
			}
			// 根据冒泡排序，将距离近的放在前面
			Collections.sort(list1, new CompratorDistanceBag());
			return list1;	
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 抢福袋红包
	 * @param user
	 * @param id 福袋id 
	 * @return
	 */
	public synchronized  Map<String, Object> getBagGold(AppUser user, String id) throws Exception{
			Map<String, Object> map=new HashMap<String, Object>();
			String mUserId=user.getUserId();//抢福袋用户id
		 
		     TheBag tb1=this.tbService.queryById(id);//查询出福袋
		     TheBag tb=new TheBag();
		     tb.setId(id);
		   //  tb.setStatus(0);//未抢完，没有失效
		    // tb.setExamStatus(1);//审核通过可以显示的
		     
			if(tb1!=null) {//如果存在
			String	shopname=tb1.getShopName();
			BigDecimal money=tb1.getBalacne();//发布的福袋提成后剩余金额
			Integer num=tb1.getRobNum();//分发福袋的剩余个数（总个数、比例提成、由后台设置）
			Long sid=tb1.getStoreId();//店铺id
			//根据抢红包用户id和店铺id 判断是否已经抢了该商户发布的福袋
			  boolean flag=this.bagService.getRob(mUserId, id);
	            if(flag) {
	            	//你已经抢过该红包
	            	return map;
	            }
	            BagRecord bgr=new BagRecord();
	            bgr.setbUserId(mUserId);//用户id
	            bgr.setStoreName(shopname);//商铺名称
	            bgr.setUserName(user.getName());//用户昵称
	            bgr.setTheBagId(id);//福袋id
	            bgr.setStoreId(sid);
				//判断福袋红包剩余个数,只剩下一个的时候
	            Integer gold;
	            if(num==1) {
	            	System.out.println("就剩下一个红包了");
	            	gold=money.intValue()*100;//金额转换为金豆
	            	bgr.setGold(gold);//抢到的金豆
	            	this.bagService.saveSelective(bgr);//更新福袋记录表
	            	
	            	
	            	BagRecord bg=new BagRecord();
	            	bg.setStoreId(sid);
	            	bg.setStoreName(shopname);
	            	bg.setStatus(1);//抢红包记录将不再作为条件查询
	            	this.bagService.updateSelective(bg);
	            	
	            	tb.setStatus(1);//福袋设置为已经抢完
	            	tb.setExamStatus(0);//让它不显示了
	            	tb.setRobNum(num-1);//设置福袋红包剩下个数
	            	tb.setBalacne(money.subtract(money));
	            	this.tbService.updateSelective(tb);
	            		map.put("getBag", gold);
			            map.put("storeName", shopname);
			            map.put("storeId", sid);
	            	System.out.println("******恭喜用户:"+user.getName()+"抢到最后一个红包金额："+gold/100+"*****");
	            }else {
	            	
	            	
	            	double cf=money.doubleValue();//转换为double类型
		            double hBje=MoneyRandom.getFuDai(num, cf);
		            gold=(int)(hBje*100);
		            System.out.println("******恭喜用户:"+user.getName()+"抢到金额："+hBje+"*****");
		            //储存福袋抢红包记录表
		            bgr.setGold(gold);//抢到的金豆
		            this.bagService.saveSelective(bgr);//更新福袋记录表
		            //更新福袋发布表
		            tb.setRobNum(num-1);//设置福袋红包剩下个数
		            String hb=String.valueOf(hBje);
		            tb.setBalacne(money.subtract(new BigDecimal(hb)));
		            this.tbService.updateSelective(tb);

		            map.put("getBag", gold);
		            map.put("storeName", shopname);
		            map.put("storeId", sid);
		            
		            
	            }
	            //更新消费记录表
	            ConsumeRecord cu=new ConsumeRecord();
				List<ConsumeRecord> list = this.consumeRecordService.queryAll();
				long size = list.size();
				cu.setId(size + 1);
				cu.setMoney("+" + gold + "金豆");// 增加金豆
				cu.setConsumeType("共享福袋");// 消费类型
				String day=DateUtil.getTime();
				cu.setConsumeTime(day);// 消费时间
				cu.setConsumeStatus(1);// 成功
				cu.setUserId(mUserId);// 消费用户
				this.consumeRecordService.saveSelective(cu);
				//更新账户剩余金豆
				AppUser qud =new AppUser();
				qud.setUserId(mUserId);
				qud.setUserIntegral(user.getUserIntegral()+gold);
				this.userService.updateSelective(qud);
	            
			}else {
				//该红包不存在
				 return map;
			}
			
			 return map;
	}
				
}









