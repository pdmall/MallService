package com.yang.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.utils.DateUtil;
import com.utils.Httpseweixin;
import com.yang.model.AppUser;
import com.yang.model.ConsumeRecord;
import com.yang.model.Vote;
import com.yang.model.Wishes;

/**
 * @author 00
 *
 */
@Service
public class WishesService extends BaseService<Wishes> {
	@Autowired
	private AppUserService service;// 用户信息
	@Autowired
	private VoteService vService;// 投票表

	@Autowired
	private ConsumeRecordService consumeRecordService;
	/**
	 * @category 投票
	 * @param user
	 *            typeId id
	 * @return Integer
	 */
	public Integer addPiao(AppUser user, Integer typeId, Long id) throws Exception {
		// AppUser au = new AppUser();// 用户
		Vote vt = new Vote();// 投票表
		int fa = 2;
		Wishes wh = super.queryById(id);// 根据主键查询所有信息
		String day = DateUtil.getTime();// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date bt = sdf.parse(day); // 当前时间
		Date et = sdf.parse(wh.getwEndTime()); // 活动结束时间
		vt.setvUserId(user.getUserId());
		vt.setvPId(id);
		int a = this.vService.queryTotalCount(vt);
		if (a == 0) {// 是否已经投票（大于零，已经投了）等于零，说明没有投过
			if (bt.before(et)) {
				// 表示bt小于et
				if (typeId == 1) {
					wh.setId(id);
					wh.setP1Sum(wh.getP1Sum() + 1);
					wh.setwSum(wh.getwSum() + 1);

					super.updateSelective(wh);
					vt.setvTypeId(typeId);
					this.vService.saveSelective(vt);// 存入数据库
					fa = 1;
				} else if (typeId == 2) {

					wh.setId(id);
					wh.setP1Sum(wh.getP2Sum() + 1);// 图二票数加一
					wh.setwSum(wh.getwSum() + 1);

					super.updateSelective(wh);
					vt.setvTypeId(typeId);
					this.vService.saveSelective(vt);// 存入数据库
					fa = 1;
				}
			} else {
				fa = 2; // 活动已结束
			}
		} else {
			fa = 3;
		}
		return fa;
	}

	/**
	 * 根据消息ID获得幸运用户。
	 * 
	 * @param id
	 * @return Integer
	 */
	public AppUser getLuck(Long id) {
		AppUser au = new AppUser();// 用户
		Wishes wh = super.queryById(id);// 根据主键查询所有信息
		Random random = new Random();
		Vote vt = new Vote();// 投票表
		int sum1 = wh.getP1Sum();// 图片一的投票数
		int sum2 = wh.getP2Sum();// 图片二的投票数
		if (sum1 > sum2) { // 图片一的投票数>图片二的投票数
			vt.setvPId(id);
			vt.setvTypeId(1);
			List<Vote> list = this.vService.queryListByWhere(vt);
			if (list != null && list.size() > 0) {
				int ran = random.nextInt(sum1);
				vt = list.get(ran);
				au.setUserId(vt.getvUserId());
				AppUser user = this.service.queryOne(au);
				if(user==null) {
					return null;
				}
				user.setUserIntegral(user.getUserIntegral() + wh.getwSum());// 余额加上奖励金豆
				this.service.updateSelective(user);
				
				//消费记录
				ConsumeRecord cu=new ConsumeRecord();
				String day=DateUtil.getTime();
				cu.setMoney("+"+wh.getwSum()+"金豆");//获得金豆
				cu.setConsumeType("众愿");//消费类型
				cu.setConsumeTime(day);//消费时间
				cu.setUserId(vt.getvUserId());//消费用户
				this.consumeRecordService.saveSelective(cu);
				
				
				return user;
			}
			wh.setwStatus(2);// 活动已结束
			wh.setwWiner(1);// 图一胜出
			super.updateSelective(wh);

		} else if (sum1 == sum2) { // 图片一的投票数等于图片二的投票数
			vt.setvPId(id);
			List<Vote> list = this.vService.queryListByWhere(vt);
			if (list != null && list.size() > 0) {
				if(wh.getwSum()<1) { 
					
				}else{
					int ran = random.nextInt(wh.getwSum());
					vt = list.get(ran);
				au.setUserId(vt.getvUserId());
				AppUser user = this.service.queryOne(au);
				user.setUserIntegral(user.getUserIntegral() + wh.getwSum());// 余额加上奖励金豆
				this.service.updateSelective(user);
				
				//消费记录
				ConsumeRecord cu=new ConsumeRecord();
				String day=DateUtil.getTime();
				cu.setMoney("+"+wh.getwSum()+"金豆");//扣除金豆
				cu.setConsumeType("众愿");//消费类型
				cu.setConsumeTime(day);//消费时间
				cu.setUserId(vt.getvUserId());//消费用户
				this.consumeRecordService.saveSelective(cu);
				
				
				return user;
			}
			}
			wh.setwStatus(2);// 活动已结束
			wh.setwWiner(vt.getvTypeId());// 图x胜出
			super.updateSelective(wh);
			
		} else { // 图片一的投票数小于图片二的投票数
			vt.setvPId(id);
			vt.setvTypeId(2);
			List<Vote> list = this.vService.queryListByWhere(vt);
			if (list != null && list.size() > 0) {
				int ran = random.nextInt(sum2);
				vt = list.get(ran);
				au.setUserId(vt.getvUserId());
				AppUser user = this.service.queryOne(au);
				user.setUserIntegral(user.getUserIntegral() + wh.getwSum());// 余额加上奖励金豆
				this.service.updateSelective(user);
				
				//消费记录
				ConsumeRecord cu=new ConsumeRecord();
				String day=DateUtil.getTime();
				cu.setMoney("+"+wh.getwSum()+"金豆");//扣除金豆
				cu.setConsumeType("众愿");//消费类型
				cu.setConsumeTime(day);//消费时间
				cu.setUserId(vt.getvUserId());//消费用户
				this.consumeRecordService.saveSelective(cu);
				
				
				return user;
			}
			wh.setwStatus(2);// 活动已结束
			wh.setwWiner(2);// 图二胜出
			super.updateSelective(wh);
		}

		return null;
	}

	/**
	 * 用户进入众愿后，根据userId信息判断活动是否截止，是否参与投票，是否为胜出者等等。 返回一个处理后的集合
	 * 
	 * @param user
	 * @return list
	 * @throws ParseException
	 */
	public List<Wishes> getAll(AppUser user) throws Exception {
		List<Wishes> li = new ArrayList<>();
		Vote vo = new Vote();
		String userId = user.getUserId();// 得到用戶id
		String day = DateUtil.getTime();// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date bat = sdf.parse(day); // 当前时间
		List<Wishes> list = super.queryAll();// 全查全查询众愿信息表
		for (Wishes s : list) {
			Date eat = sdf.parse(s.getwEndTime());// 查询该条活动的截止时间

			if (bat.before(eat)) {// 活动未截止
				vo.setvUserId(userId);
				vo.setvPId(s.getId());// 根据userId和消息id查询是否投票
				Vote qe = this.vService.queryOne(vo);// 查询一条数据
				if (qe == null) {
				} else {
					s.setVoteType(qe.getvTypeId());// 投了那张图片
				}
				li.add(s);
			} else {
				// 活动已经截止
				if (s.getwStatus() == 1) {
					// 未选择幸运者
					AppUser uid = getLuck(s.getId());// 选取幸运用户
					s.setwUserId(uid.getUserId());
					s.setwStatus(2);
					s.setwName(uid.getName());
					super.updateSelective(s);
					// 查询是否投票
					vo.setvUserId(userId);
					vo.setvPId(s.getId());
					Vote qu = this.vService.queryOne(vo);
					if (qu == null) {

					} else {
						s.setVoteType(qu.getvTypeId());
						if (uid.getUserId().equals(user.getUserId())) {
							// 自己是幸运用户
							s.setLuckly(1);
						}
					}
					li.add(s);
				} else {
					// 已选择
					// 判断是否是幸运用户
					vo.setvUserId(userId);
					vo.setvPId(s.getId());
					Vote qu = this.vService.queryOne(vo);
					if (qu == null) {

					} else {
						if (s.getwUserId().equals(user.getUserId())) {
							// 自己是幸运用户
							s.setLuckly(1);
						}
					}
					li.add(s);
				}
			}
		}
		return li;
	}
	/**
	 *  派对众愿
	 * @param code
	 * @return
	 */
	public  JSONObject  gerJsonByWish(String code) {
		
		 //小程序唯一标识   (在微信小程序管理后台获取)  
	    String wxspAppid = "wxda787e4bfeafe899";  
	    //小程序的 secret (在微信小程序管理后台获取)  
	    String wxspSecret = "043f4cd27f377f78e96c016e84116a58";  
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
	
}