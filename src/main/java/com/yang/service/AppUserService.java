package com.yang.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.utils.Httpseweixin;
import com.utils.RESAUtil;
import com.utils.StringUtils;
import com.utils.UtilTrim;
import com.utils.UuidUtil;
import com.utils.WxUtils;
import com.yang.mapper.AppUserMapper;
import com.yang.mapper.OrderMapper;
import com.yang.model.AppUser;
import com.yang.model.CodeOpenid;
import com.yang.model.ConsumeRecord;
import com.yang.model.ForRefund;
import com.yang.model.Order;
import com.yang.model.OrderCommentLabel;
import com.yang.model.Volume;
import com.yang.model.Voucher;

/**
 * @author 00
 *
 */
@Service
public class AppUserService extends BaseService<AppUser> {

	@Autowired
	private OrderService oService;
	@Autowired
	private VoucherService voService;
	@Autowired
	private ConsumeRecordService consumeRecordService;
	@Autowired
	private VolumeService volumeService;
	@Autowired
	private OrderMapper oMapper;
	@Autowired
	private VoucherService voucherService;
	@Autowired
	private ForRefundService forRefundService;
	@Autowired
	private OrderCommentLabelService orclaService;//订单评论标签投票

	@Autowired
	private AppUserMapper uMapper;
	/**
	 * 缓存控制器
	 */
	@Autowired(required = true)
	private RedisService rService;
	// private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Integer REDIS_TIME = 60 * 60 * 24 * 20;

										/***************************************** 用户操作 **************************************************/
	
	
	public AppUser postOpenId(String openId, String unionId) {
		// 1.查询是否存在该微信号
		if (openId != null && !"".equals(openId) && unionId != null && !"".equals(unionId)) {
			// 创建用户表对象
			AppUser au = new AppUser();
			// 给用户表对象里的微信id赋值
			au.setUnionId(unionId);
			// 返回查询结果
			AppUser one = this.uMapper.selectOne(au);
			// 判断返回结果是否为空，不是直接返回查出信息，是则保存数据库在返回该信息
			if (one != null) {
				// 修改登录状态1为登录，0为未登录
				one.setStatus("1");
				// 修改登录状态
				super.updateSelective(one);
				return one;
			}

			// 3.将该数据存在数据库中
			// 修改登录状态1为登录，0为未登录
			au.setStatus("1");

			au.setOpenId(openId);
			// 设置用户剩余金豆
			au.setUserIntegral(0);
			// 设置用户id
			au.setUserId(UuidUtil.get32UUID());
			// 将信息存入数据库
			super.saveSelective(au);

			return au;

		}
		return null;

	}

	public AppUser getusers(String date) {
		AppUser u = new AppUser();
		u.setUnionId(date);
		AppUser us = super.queryOne(u);
		return us;
	}

	/**
	 * @author 00 查询用户信息
	 */
	public AppUser getuserall(AppUser user) {
		AppUser u = new AppUser();
		u.setUnionId(user.getUnionId());
		AppUser uss = super.queryOne(u);
		return uss;
	}

	/**
	 * 修改个人信息(前台接口)
	 * 
	 * @param user
	 * @return
	 */
	public AppUser updateleuser(AppUser user) {
		super.updateSelective(user);
		AppUser a = new AppUser();
		a.setUserId(user.getUserId());

		return super.queryOne(a);
	}
	/**
	 * 用户添加或者修改电话号码
	 * 
	 * @param user
	 * @param phone
	 * @return
	 */
	public Integer addUserPhone(AppUser user, String phone) {
		AppUser u = new AppUser();
		String po = user.getPhone();
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		boolean flag = p.matcher(phone).matches();// 校验电话号码格式是否正取
		if (po != null) {

			if (flag) {// 格式正确

				u.setUserId(user.getUserId());
				u.setPhone(phone);
				super.updateSelective(u);

				return 1;
			}
			return 2;

		}
		u.setUserId(user.getUserId());
		u.setPhone(phone);
		super.saveSelective(u);

		return 1;
	}

	
	/**
	 * 登录授权后，存储user信息。
	 */

	public AppUser updateUserByUserToken(AppUser user, String name, String headPortrait, String sex, String city,
			String ip) {
		// 创建用户表对象
		AppUser au = new AppUser();
		au.setUnionId(user.getUnionId());
		AppUser one = super.queryOne(au);
		// 判断返回结果是否为空，不是直接返回查出信息，是则保存数据库在返回该信息
		if (one != null) {
			// 修改登录状态1为登录，0为未登录
			one.setUserId(user.getUserId());
			one.setStatus("1");
			one.setName(name);
			one.setHeadPortrait(headPortrait);
			one.setIp(ip);
			String day = DateUtil.getTime();
			one.setLastLogin(day);
			one.setCity(city);
			one.setSex(sex);
			// 将更新存入数据库
			super.updateSelective(one);
			return one;
		} else {
			au.setUserId(user.getUserId());
			au.setStatus("1");
			// 给用户表对象里的微信id赋值
			au.setName(name);
			au.setHeadPortrait(headPortrait);
			au.setCity(city);
			au.setIp(ip);
			au.setSex(sex);
			// 将信息存入数据库
			super.saveSelective(au);
			return one;
		}
	}

										/************************************ * 用户下单购买 **************************************************/
	
	
	/**
	 * 用户下单
	 * 
	 * @param user
	 * @param moneyprivate
	 *            String id;// id
	 * 
	 * @param gold
	 * @param status
	 * @param vocherId
	 * @param jine
	 * @param num
	 * @return
	 */
	public String postUserOrderByToken(AppUser user, Order oder) {

		if (user != null && oder != null) {
			String uId = user.getUserId();// 用户id
			String ss = oder.getOrderpdtid();// 商品id

			Volume vo = new Volume();// 团购券/现金券
			vo.setId(ss);
			Volume dd = this.volumeService.queryOne(vo);

			SimpleDateFormat df = new SimpleDateFormat("hhmmss");
			String d = df.format(new Date());
			int c = (int) ((Math.random() * 9 + 1) * 10000);
			String no = "xzdp" + d + c;// 订单号
			oder.setId(UuidUtil.get32UUID());//生成主键id
			oder.setOrderuserid(uId);// 买家id
			oder.setSelleruserid(dd.getvUserid());// 卖家id
			oder.setOrderstatus(1);// 待支付
			oder.setOrderno(no);// 订单编号

			this.oService.saveSelective(oder);
			String voId = oder.getVoucherId();
			if (voId != null | !"".equals(voId)) {// 假设使用了抵用券
				Voucher vc = new Voucher();
				vc.setCouponCode(voId);
				vo.setvUserid(uId);
				Voucher v = this.voucherService.queryOne(vc);
				if (v != null) {
					v.setvSituation(2);// 是否已经使用（0：未使用，1：已使用 2：待使用
					this.voucherService.updateSelective(v);

				}
			}

			return no;
		}

		return null;
	}

	/**
	 * 
	 * @param userId
	 * @param state
	 * @param page
	 * @param rows
	 * @return
	 */
	// 根据订单状态查询订单
	public List<Order> getUserOrderListByState(AppUser user, Integer state) {
		Order order = new Order();
		String uId=user.getUserId();
		Example example = new Example(order.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("orderstatus", state).andEqualTo("orderuserid",uId );
		List<Order> sbList = this.oMapper.selectByExample(example);
		if(sbList!=null&&!sbList.isEmpty()) {
			return sbList;
		}
		return new ArrayList<>();
	}

	// 根据用户id查看订单列表
	public List<Order> getUserOrderListByToken(String userId) {
		Order order = new Order();
		Example example = new Example(order.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("orderuserid", userId);
		List<Order> orderList = this.oMapper.selectByExample(example);
		if(orderList!=null) {
			return orderList;
		}
		return new ArrayList<>();
	}

	/**
	 * 删除订单(前台接口)
	 * 
	 * @param id
	 */
	public void deteleorderByid(Long id) {
		
		this.oService.deleteById(id);
       
		
	}

	/**
	 * 根据订单号查询详情
	 * 
	 * @param orderId
	 * @return
	 */

	public Order getOrderDetailByOrderId(String order) {
		Order or = new Order();
		or.setOrderno(order);
		Order c = this.oService.queryOne(or);
		if (c != null) {
			return c;
		}
		return null;
	}
	
	
	
	
									/************************************ * 支付 **************************************************/
	
	/**
	 * 支付(前台接口)(余额支付)
	 * 
	 * @param money
	 * @param order
	 * @param user
	 * @return
	 */
	public AppUser weixinpay(BigDecimal money, String order, AppUser user, Integer gold, String vocherId) {
		AppUser u = new AppUser();
		String uId = user.getUserId();
		String day1=DateUtil.getDay();
		BigDecimal a = user.getMoney();// 账户余额
		int num = a.compareTo(money);
		if (gold != 0 && !"111".equals(vocherId)) {// 支付金豆不为零,且已使用抵用券。
			if (num != -1) {
				// 如果余额大于支付金额
				u.setUserId(user.getUserId());
				u.setMoney(a.subtract(money));// 刷新账户余额
				u.setUserIntegral(user.getUserIntegral() - gold);// 剩余金豆
				super.updateSelective(u);

				Order or = new Order();
				String g = String.valueOf(gold / 100);
				BigDecimal dd = new BigDecimal(g);
				BigDecimal sum = money.add(dd);

				or.setOrderno(order);// 设置订单编号
				or.setOrderstatus(2);// 订单状态默认，1.待支付 2.待消费 3.已完成
				String day = DateUtil.getTime();
				or.setOrderuserid(user.getUserId());// 买家id
				or.setPaytime(day);// 支付时间
				or.setmFinalmoney(sum);// 订单的总价
				this.oService.updateSelective(or);// 将订单更新数据库

				Voucher vo = new Voucher();
				vo.setvUserId(uId);
				vo.setCouponCode(vocherId);// 券码
				vo.setvSituation(1);// 已使用
				this.voService.updateSelective(vo);// 更新抵用券的使用情况

				ConsumeRecord cu = new ConsumeRecord();
				// 消费记录
				cu.setMoney("-" + sum + "元");// 扣除余额
				cu.setConsumeType("美食");// 消费类型
				cu.setConsumeTime(day);// 消费时间
				cu.setUserId(uId);// 消费用户
				
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(sum);
				this.consumeRecordService.saveSelective(cu);
				return u;
			}
			return null;

		} else if (gold != 0 && "111".equals(vocherId)) {// 支付金豆不为零,没有使用抵用券。

			if (num != -1) {
				// 如果余额大于支付金额
				u.setUserId(user.getUserId());
				u.setMoney(a.subtract(money));// 刷新账户余额
				u.setUserIntegral(user.getUserIntegral() - gold);// 剩余金豆
				super.updateSelective(u);

				Order or = new Order();
				String g = String.valueOf(gold / 100);
				BigDecimal dd = new BigDecimal(g);
				BigDecimal sum = money.add(dd);
				or.setOrderno(order);// 设置订单编号
				or.setOrderstatus(2);// 表示已使用
				String day = DateUtil.getTime();
				or.setOrderuserid(user.getUserId());
				or.setPaytime(day);// 支付时间
				or.setmFinalmoney(sum);// 订单的总价
				this.oService.updateSelective(or);// 将订单消息存入数据库

				ConsumeRecord cu = new ConsumeRecord();
				// 消费记录
				String days = DateUtil.getTime();
				cu.setMoney("-" + sum + "元");// 扣除金豆
				cu.setConsumeType("美食");// 消费类型
				cu.setConsumeTime(days);// 消费时间
				cu.setUserId(user.getUserId());// 消费用户
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(sum);
				this.consumeRecordService.saveSelective(cu);
				return u;
			}
			return null;

		} else if (gold == 0 && !"111".equals(vocherId)) {// 使用抵用券，支付金豆为零

			if (num != -1) {
				// 如果余额大于支付金额
				u.setUserId(user.getUserId());
				u.setMoney(a.subtract(money));// 刷新账户余额
				super.updateSelective(u);

				Order or = new Order();
				or.setOrderno(order);// 设置订单编号
				or.setOrderstatus(2);
				String day = DateUtil.getTime();
				or.setOrderuserid(user.getUserId());
				or.setPaytime(day);// 支付时间
				or.setmMoney(money);// 订单的总价
				this.oService.updateSelective(or);// 将订单消息存入数据库

				Voucher vo = new Voucher();
				vo.setCouponCode(vocherId);// 券码
				vo.setvSituation(1);// 已使用
				this.voService.updateSelective(vo);// 更新抵用券的使用情况

				ConsumeRecord cu = new ConsumeRecord();
				// 消费记录
				cu.setMoney("-" + money + "元");// 扣除金豆
				cu.setConsumeType("美食");// 消费类型
				cu.setConsumeTime(day);// 消费时间
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(money);
				cu.setUserId(user.getUserId());// 消费用户
				this.consumeRecordService.saveSelective(cu);
				return u;
			}
			return null;

		}

		if (num != -1) {
			// 如果余额大于支付金额
			u.setUserId(user.getUserId());
			u.setMoney(a.subtract(money));// 刷新账户余额
			super.updateSelective(u);

			Order or = new Order();
			or.setOrderno(order);// 设置订单编号
			or.setOrderstatus(2);// 表示已使用
			String day = DateUtil.getTime();
			or.setOrderuserid(user.getUserId());
			or.setPaytime(day);// 支付时间
			or.setmMoney(money);// 订单的总价
			this.oService.updateSelective(or);// 将订单消息存入数据库

			ConsumeRecord cu = new ConsumeRecord();
			// 消费记录
			cu.setMoney("-" + money + "元");// 扣除金豆
			cu.setConsumeType("美食");// 消费类型
			cu.setConsumeTime(day);// 消费时间
			cu.setDay(day1);
			cu.setXiaoFeiType(1);//钱支付
			cu.setConsumePrice(money);
			cu.setUserId(user.getUserId());// 消费用户
			this.consumeRecordService.saveSelective(cu);
			return u;
		}

		return null;

	}

	/**
	 * 剩余金豆支付
	 * 
	 * @param gold
	 * @param ss
	 * @param user
	 * @param vocherId
	 * @return
	 */
	public AppUser weixinpayByGold(Integer gold, String ss, AppUser user, String vocherId) {
		Integer a = user.getUserIntegral();// 账户剩余金豆
		String day1=DateUtil.getAfterDay();	
		AppUser u = new AppUser();
		if (vocherId != null && !"".equals(vocherId)) {// 使用了抵用券的情况
			if (a >= gold) {
				// 如果余豆大于支付金豆
				u.setUserId(user.getUserId());
				u.setUserIntegral(a - gold);// 刷新账户余额
				super.updateSelective(u);

				Order or = new Order();
				or.setOrderno(ss);// 设置订单编号
				or.setOrderstatus(2);// 待消费
				String day = DateUtil.getTime();
				or.setOrderuserid(user.getUserId());
				or.setPaytime(day);// 支付时间
				String sss = String.valueOf(gold / 100);
				or.setmFinalmoney(new BigDecimal(sss));// 订单的总价
				this.oService.updateSelective(or);// 将订单消息存入数据库

				Voucher vo = new Voucher();
				vo.setCouponCode(vocherId);// 券码
				vo.setvSituation(1);// 已使用
				this.voService.updateSelective(vo);// 更新抵用券的使用情况

				ConsumeRecord cu = new ConsumeRecord();
				// 消费记录
				cu.setMoney("-" + gold + "金豆");// 扣除金豆
				cu.setConsumeType("美食");// 消费类型
				cu.setConsumeTime(day);// 消费时间
				cu.setDay(day1);
				cu.setXiaoFeiType(2);//钱支付
				BigDecimal aa=new BigDecimal(gold);
				cu.setConsumePrice(aa);
				cu.setUserId(user.getUserId());// 消费用户
				this.consumeRecordService.saveSelective(cu);
				return u;
			}

			return null;

		} // 直接金豆进行支付
		if (a >= gold) {
			// 如果剩余大于支付金豆
			u.setUserId(user.getUserId());
			u.setUserIntegral(a - gold);// 刷新账户余额
			super.updateSelective(u);

			Order or = new Order();
			or.setOrderno(ss);// 设置订单编号
			or.setOrderstatus(2);// 待消费
			String day = DateUtil.getTime();
			or.setOrderuserid(user.getUserId());
			or.setPaytime(day);// 下单时间
			String sss = String.valueOf(gold / 100);
			or.setmFinalmoney(new BigDecimal(sss));// 订单的总价
			this.oService.updateSelective(or);// 将订单消息存入数据库

			ConsumeRecord cu = new ConsumeRecord();
			// 消费记录
			cu.setMoney("-" + gold + "金豆");// 扣除金豆
			cu.setConsumeType("美食");// 消费类型
			cu.setConsumeTime(day);// 消费时间
			cu.setDay(day1);
			cu.setXiaoFeiType(2);//金豆支付
			BigDecimal aa=new BigDecimal(gold);
			cu.setConsumePrice(aa);
			cu.setUserId(user.getUserId());// 消费用户
			this.consumeRecordService.saveSelective(cu);
			return u;
		}

		return null;
	}

	/**
	 * 微信支付
	 * 
	 * @param user
	 * @param money
	 * @param gold
	 * @param vocherId
	 */
	public Integer addOrderAfterPlay(String order, AppUser user, BigDecimal money, Integer gold, String vocherId,
			BigDecimal jine) {
		String day1=DateUtil.getDay();
		AppUser u = new AppUser();
		BigDecimal a = user.getMoney();
		Integer b = user.getUserIntegral();
		String day = DateUtil.getTime();
		int cd = a.compareTo(money);
		if (money != null && gold != 0 && vocherId != null) {// 代金券，金豆，余额，都使用还不够时
			if (cd != -1 && b >= gold) {

				Order or = new Order();
				or.setOrderuserid(user.getUserId());
				or.setOrderno(order);// 设置订单编号
				or.setOrderstatus(2);// 待消费
				this.oService.updateSelective(or);// 将订单消息存入数据库

				Voucher vo = new Voucher();
				vo.setCouponCode(vocherId);// 券码
				vo.setvSituation(1);// 已使用
				this.voService.updateSelective(vo);// 更新抵用券的使用情况
				// 如果余豆大于支付金豆
				u.setUserId(user.getUserId());
				u.setMoney(a.subtract(money));// 刷新余额
				u.setUserIntegral(b - gold);// 刷新账户剩余金豆
				super.updateSelective(u);
				BigDecimal t1 = new BigDecimal(String.valueOf(gold / 100));
				BigDecimal ddd = t1.add(jine);
				BigDecimal sum = a.add(ddd);

				ConsumeRecord cu = new ConsumeRecord();
				// 消费记录
				cu.setMoney("-" + sum + "元");// 扣除元
				cu.setConsumeType("美食");// 消费类型
				cu.setConsumeTime(day);// 消费时间
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(sum);
				cu.setUserId(user.getUserId());// 消费用户
				this.consumeRecordService.saveSelective(cu);
				return 1;
			}

			return 2;

		}
		Order or = new Order();
		or.setOrderuserid(user.getUserId());
		or.setOrderno(order);// 订单编号
		or.setOrderstatus(2);
		this.oService.updateSelective(or);// 将订单消息跟新数据库

		Voucher vo = new Voucher();
		vo.setCouponCode(vocherId);// 券码
		vo.setvSituation(1);// 已使用
		this.voService.updateSelective(vo);// 更新抵用券的使用情况

		ConsumeRecord cu = new ConsumeRecord();
		// 消费记录
		cu.setMoney("-" + jine + "元");// 扣除元
		cu.setConsumeType("美食");// 消费类型
		cu.setConsumeTime(day);// 消费时间
		cu.setDay(day1);
		cu.setXiaoFeiType(1);//钱支付
		cu.setConsumePrice(jine);
		cu.setUserId(user.getUserId());// 消费用户
		this.consumeRecordService.saveSelective(cu);

		return 1;

	}
						/********************************************用户提现操作*****************************************/
	

	/**
	 * 用户提现（后台操作）
	 * 
	 * @param openid
	 * @param money
	 * @return
	 */
	// 用户提现
	public Map<String, String> tixian(String openid, Integer money) {
		String desc = "红包提现";
		String partner_trade_no = UtilTrim.getRandomFileName(); // 同一单号不可以重复提交，如果重复提交同一单号，微信以为是同一次付款，只要成功一次，以后都会无视。
		Map<String, String> map = WxUtils.transfer(openid, money, desc, partner_trade_no);

		return map;

	}
	
	
	
	
	
	
	
	
	
	
						/********************************************用户授权*****************************************/
	/**
	 * 
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @param program
	 *            那个小程序（1：天天打卡 , 2,众愿 ，3：美食）
	 * @return
	 */
	public Map<String, Object> getCode(String encryptedData, String iv, String code, Integer program) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		// 登录凭证不能为空
		if (code == null || code.length() == 0) {
			map.put("status", 0);
			map.put("msg", "code 不能为空");
			return map;
		}
		if (program == 1) {// 打卡
			DakaService dak = new DakaService();

			json = dak.gerJsonByDaKa(code);

		} else if (program == 2) {// 众愿
			WishesService ws = new WishesService();
			json = ws.gerJsonByWish(code);
		} else if (program == 3) {// 美食
			StoreService sr = new StoreService();
			json = sr.gerJsonByMeiShi(code);

		} else {
			json = decodeUserInfoGongZhongHao(code);

		}
		if (json.get("session_key") == null || "".equals(json.get("session_key"))) {
			return map;
		}
		// 获取会话密钥（session_key）
		String session_key = json.get("session_key").toString();

		//////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
		try {
			String result = RESAUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
			if (null != result && result.length() > 0) {

				JSONObject userInfoJSON = JSONObject.parseObject(result);
				Map<String, Object> userInfo = new HashMap<String, Object>();
				// 用户的唯一标识（unionId）
				String unionId = (String) userInfoJSON.get("unionId");
				String openId1 = (String) userInfoJSON.get("openId");
				CodeOpenid entitys = new CodeOpenid();

				if (unionId != null && openId1 != null) {
					entitys = JSONObject.toJavaObject(json, CodeOpenid.class);
				} else {
					return map;
				}

				String openId = entitys.getOpenid();
				@SuppressWarnings("unused")
				AppUser au = postOpenId(openId, unionId);
				String token = DigestUtils.md5Hex(System.currentTimeMillis() + unionId);
				this.rService.set("TOKEN_" + token, unionId, REDIS_TIME);

				map.put("status", 1);
				map.put("msg", "解密成功");
				map.put("token", token);

				userInfo.put("openId", userInfoJSON.get("openId"));
				userInfo.put("nickName", userInfoJSON.get("nickName"));
				userInfo.put("gender", userInfoJSON.get("gender"));
				userInfo.put("city", userInfoJSON.get("city"));
				userInfo.put("province", userInfoJSON.get("province"));
				userInfo.put("country", userInfoJSON.get("country"));
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
				userInfo.put("unionId", userInfoJSON.get("unionId"));
				map.put("userInfo", userInfo);
				System.out.println("后台解密成功。。。成功授权");
				return map;
			}

		} catch (Exception e) {
			System.out.println("<***********授权异常**************>");
			;
		}
		map.put("status", 0);
		map.put("msg", "解密失败");
		System.out.println("后台解密失败。。。");
		return map;
	}

	/**
	 * 微信公众号验证授权
	 * 
	 * @param code
	 * @return
	 */
	public JSONObject decodeUserInfoGongZhongHao(String code) {

		// 天天打卡小程序唯一标识 (在微信小程序管理后台获取)
		String wxspAppid = "wx09230d380d307bdb";
		// 小程序的 app secret (在微信小程序管理后台获取)
		String wxspSecret = "19a58973d9fcf6a7bb16af44c7c8cc";
		// 授权（必填）
		String grant_type = "authorization_code";
		//////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
		// 请求参数
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
				+ grant_type;
		// 发送请求
		String sr = Httpseweixin.get("https://api.weixin.qq.com/sns/jscode2session", params);

		// 解析相应内容（转换成json对象）
		JSONObject json = JSONObject.parseObject(sr);

		return json;

	}
	/**
	 * 
	 * @param user 
	 * @param id//订单id
	 */
	public Integer  applicationForRefundById(AppUser user, String id,String season,String discreb) {
		String uId=user.getUserId();
		
		Order or=this.oService.queryById(id);
		 String orderNo=or.getOrderno();//订单编号
		 String sellNo=or.getSelleruserid();//卖家id
		 
		 ForRefund fo=new ForRefund();
		 fo.setfUserId(uId);//申请退款用户
		 fo.setUserName(user.getName());
		 fo.setOrderNo(orderNo);//订单编号
		 fo.setSellUserId(sellNo);//卖家商户id
		 fo.setTime(DateUtil.getTime());//申请时间
		 fo.setReason(season);//退款理由
		 String text=StringUtils.replaceBlank(discreb);
		 fo.setDescribe(text);
		 this.forRefundService.saveSelective(fo);
		 
		 Order oder=new Order();
		 oder.setId(id);
		 oder.setOrderstatus(4);//订单状态 退款处理中
		 this.oService.updateSelective(oder);
		 
		 return 1;
	}
	/**
	 * 评价完成订单,投票关键词
	 * @param user
	 * @param order
	 * @return
	 */
	public Integer oderCommentByToken(Order order,OrderCommentLabel ola) throws Exception{
		
		if(ola!=null) {
			Long bbk=ola.getoStoreId();
			OrderCommentLabel ol=this.orclaService.queryById(bbk);
			OrderCommentLabel olabel=new OrderCommentLabel();
			if(ola.getLabel1()!=null) {
				olabel.setNum1(ol.getNum1()+1);
			}else if(ola.getLabel2()!=null) {
				olabel.setNum2(ol.getNum2()+1);
			}else if(ola.getLabel3()!=null) {
				olabel.setNum3(ol.getNum3()+1);
			}else if(ola.getLabel4()!=null) {
				olabel.setNum4(ol.getNum4()+1);
			}else if(ola.getLabel5()!=null) {
				olabel.setNum5(ol.getNum5()+1);
			}else if(ola.getLabel6()!=null) {
				olabel.setNum6(ol.getNum6()+1);
			}else if(ola.getLabel7()!=null) {
				olabel.setNum7(ol.getNum7()+1);
			}
			//更新便签投票数
			olabel.setoStoreId(bbk);
			this.orclaService.updateSelective(olabel);
		}
		
		this.oService.saveSelective(order);
		
		return 1;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public AppUser getuser(String userId) {
		
		AppUser a=super.queryById(userId);
		return a==null?null:a;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
