package com.yang.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.utils.DateUtil;
import com.utils.Httpseweixin;
import com.utils.MoneyRandom;
import com.yang.service.TimeRulesService;
import com.yang.model.AppUser;
import com.yang.model.ConsumeRecord;
import com.yang.model.DakaRecord;
import com.yang.model.DakaRule;
import com.yang.model.Dakaleixing;
import com.yang.model.RedGold;
import com.yang.model.TimeRules;

@Service
public class DakaService extends BaseService<DakaRule> {
	@Autowired
	private DakaleixingService dakaleixinService;
	@Autowired
	private AppUserService appuserService;
	@Autowired
	private TimeRulesService timerService;
	@Autowired
	private RedGoldService redgoldService;
	@Autowired
	private DakaRecordService dakaRecordService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	static Integer folat = 2;

	/**
	 * 判断打卡时间是否在规定的时间内
	 * 
	 * @param
	 * @return Boolean
	 */
	public Integer DakaTime() {
		TimeRules tr = this.timerService.queryById(1l);
		String tdateh = tr.getDkStart();// 打卡开始时间
		int T = Integer.parseInt(tdateh.substring(0, 2));// 截取小时
		// 打卡结束时间
		String tdate = tr.getDkEnd();
		int R = Integer.parseInt(tdate.substring(0, 2));// 截取小时
		String sub = DateUtil.getTime();// 获取打卡当前时间
		int dateh = Integer.parseInt(sub.substring(11, 13));// 截取小时
		int datem = Integer.parseInt(sub.substring(14, 16));// 截取分钟
		int dates = Integer.parseInt(sub.substring(17, 19));// 截取秒数
		if (dateh >= T && dateh <= R) {
			// 在时间范围内
			if (dateh == R && datem > 0 && dates > 0) {
				// 刚好超过结束时间
				folat = 2;
			}
			folat = 1;
		} else {
			folat = 2;
		}
		return folat;
	}

	/**
	 * 打卡成功与否
	 * 
	 * @param 用户信息，区域类型ID
	 * @return Integer
	 */
	public Integer selectStatus(AppUser user) {
		Integer i = 0;
		String day = DateUtil.getTime();// 当前时间
		Integer da = DakaTime();// 判断是否在规定范围内，在：1，不在：2
		String dakatime = DateUtil.getDay();
		DakaRule d = new DakaRule();
		d.setDkId(user.getUserId());
		d.setDkStauts(2);// 已经参加
		d.setDay(dakatime);// 参加打卡的时间
		List<DakaRule> dkr = super.queryListByWhere(d);
		if (dkr != null && !dkr.isEmpty()) {
			if (da == 1) {
				// 打卡时间在规定范围内
				for (DakaRule dk : dkr) {
					// 打卡成功
					Integer gs = dk.getSum() + 1;
					d.setSum(gs);
					if (gs > dk.getMaxCont()) {
						d.setMaxCont(dk.getMaxCont() + 1);
					}
					d.setId(dk.getId());
					d.setDkTime(day);
					d.setDkStauts(0);
					super.updateSelective(d);
					i = 1;
					// 打卡记录表
					DakaRecord dc = new DakaRecord();
					dc.setDakaStatus(0);
					dc.setDakaTime(day);
					dc.setUserId(user.getUserId());
					this.dakaRecordService.saveSelective(dc);// 打卡记录
				}
			} else {

				i = 2;
			}
			return i;

		} else {
			i = 3;
		}
		return i;
	}
	/**
	 * 打卡成功，随机分配金豆
	 * 
	 * @param user
	 * @return
	 * @return
	 */
	public  synchronized Integer getdakaGold() {

//		System.out.println("哎呀哎呀-----进来啦---进来啦");
		List<Dakaleixing> q = dakaleixinService.queryAll();
		String day = DateUtil.getDay();
		String days = DateUtil.getTime();
		if (q != null && !q.isEmpty()) {
			RedGold g = redgoldService.queryById(1L);
			RedGold ga =redgoldService.queryById(2L);
			for (Dakaleixing d : q) {

				DakaRule dr = new DakaRule();
				dr.setTypeId(d.getId());// 类型id
				dr.setDay(day);// 日期
				List<DakaRule> qw = super.queryListByWhere(dr);// 根据条件查询当天打卡类型的总人数
				if (qw != null && !qw.isEmpty()) {

					int size = qw.size();// 该类型的总人数
					int c = size * d.getJine();// 总金豆
					int i = (int) (c - (c * d.getTi_cheng()));// 可分配的金豆
					dr.setDkStauts(0);// 打卡状态 0：成功 1：失败 2：参加 3：已参加
					List<DakaRule> qt = super.queryListByWhere(dr);// 打卡成功人数
					int a, bbk;
					if (d.getId() == 1) {
						a = g.getGudingNum();// 固定红包个数
						bbk = g.getHongbaoGold();// 固定红包每一个的金额
					} else {
						a = ga.getGudingNum();// 固定红包个数
						bbk = ga.getHongbaoGold();// 固定红包每一个的金额

					}
					int six = qt.size();// 打卡成功的人数
					if (qt != null && !qt.isEmpty()) {
						if (six == 1) {
							DakaRule du = new DakaRule();
							ConsumeRecord cu = new ConsumeRecord();
							for (DakaRule das : qt) {
								du.setFpje(100);
								du.setId(das.getId());
								super.updateSelective(du);// 存入账户

								// 消费记录
								List<ConsumeRecord> list = this.consumeRecordService.queryAll();
								long id = list.size();
								cu.setId(id + 1);
								cu.setMoney("+" + 100 + "金豆");// 扣除金豆
								cu.setConsumeType("天天打卡");// 消费类型
								cu.setConsumeTime(days);// 消费时间
								cu.setConsumeStatus(1);// 成功
								cu.setUserId(das.getDkId());// 消费用户
								this.consumeRecordService.saveSelective(cu);

								AppUser qud = this.appuserService.queryById(das.getDkId());
								qud.setUserIntegral(qud.getUserIntegral() + 100);
								this.appuserService.updateSelective(qud);

								// 打卡记录
								DakaRecord dc = new DakaRecord();
								List<DakaRecord> de = this.dakaRecordService.queryAll();
								long id1 = de.size();
								dc.setId(id1 + 1);
								dc.setDakaStatus(0);// 打卡成功
								dc.setDakaTime(days);
								dc.setUserId(das.getDkId());
								this.dakaRecordService.saveSelective(dc);// 打卡记录
								System.out.println("虽然只有一个人成功，还是得分配");
							}

						} else {
							// 如果成功人数不为1

							if (six <= a) {// 如果打卡成功人数小于固定红包的个数
								int guding1;
								int hongbao1;
								if (i < bbk) {
									guding1 = 1;// 默认设置固定红包个数为一
									hongbao1 = 100;// 默认设置固定红包金豆为100
								} else {
									guding1 = 1;
									hongbao1 = bbk;
								}

								Integer vvs[] = null;
								int size2 = qt.size();// 打卡成功总人数
								//int moneyMin = g.getMoneyMin();
								int sy = size2 - guding1; // 剩余个数
								int zong = guding1 * hongbao1; // 固定红包总金额
								double jine;
								if (i < zong) {// 可分配金额小于固定红包金额
									if (d.getId() == 1) {
										jine = (double)(i - (50 * guding1)) / 100;
										bbk = 50;
									} else {
										jine = (double)(i - (100 * guding1)) / 100;
										bbk = 200;
									}
								} else {
									jine = (double)(i - zong) / 100; // 剩余总金额
								}
								if (size2 != 0 && jine != 0 && sy != 0) {
									vvs = MoneyRandom.getHBje(size2,sy, jine);// 生成的随机数和固定金额都放入一个数组里
								}
								for (int cc = 0; cc < guding1; cc++) {
									vvs[sy + cc] = bbk;
									// 固定红包放入数组
								}
								// 分发到各账户
								DakaRule du = new DakaRule();
								ConsumeRecord cu = new ConsumeRecord();
								int a1 = 0;
								for (DakaRule das : qt) {
									du.setFpje(vvs[a1]);
									du.setId(das.getId());
									super.updateSelective(du);// 存入账户

									// 消费记录
									List<ConsumeRecord> list = this.consumeRecordService.queryAll();
									long id = list.size();
									cu.setId(id + 1);
									cu.setMoney("+" + vvs[a1] + "金豆");// 扣除金豆
									cu.setConsumeType("天天打卡");// 消费类型
									cu.setConsumeTime(days);// 消费时间
									cu.setConsumeStatus(1);// 完成消费
									cu.setUserId(das.getDkId());// 消费用户
									this.consumeRecordService.saveSelective(cu);

									AppUser qud = this.appuserService.queryById(das.getDkId());
									qud.setUserIntegral(qud.getUserIntegral() + vvs[a1]);
									this.appuserService.updateSelective(qud);
									a1++;
								}
							} else {
								Integer vvs[] = null;
								int size2 = qt.size();// 打卡成功总人数
								//int moneyMin = g.getMoneyMin();
								int sy = size2 - a; // 剩余个数
								int zong = a * bbk; // 固定红包总金额
								double jine;
								if (i < zong) {
									if (d.getId() == 1) {
										jine = (double)(i - (50 * a)) / 100;
										bbk = 50;
									} else {
										jine = (double)(i - (100 * a)) / 100;
										bbk = 200;
									}
								} else {
									jine = (double)(i - zong) / 100; // 剩余总金额
								}
								if (size2 != 0 && jine != 0 && sy != 0) {
									vvs = MoneyRandom.getHBje(size2,sy, jine);// 生成的随机数和固定金额都放入一个数组里
								}
								for (int cc = 0; cc < a; cc++) {
									vvs[sy + cc] = bbk;
									// 固定红包放入数组
								}
								// 分发到各账户
								DakaRule du = new DakaRule();
								ConsumeRecord cu = new ConsumeRecord();
								int a1 = 0;
								for (DakaRule das : qt) {
									du.setFpje(vvs[a1]);
									du.setId(das.getId());
									super.updateSelective(du);// 存入账户

									// 消费记录
									List<ConsumeRecord> list = this.consumeRecordService.queryAll();
									long id = list.size();
									cu.setMoney("+" + vvs[a1] + "金豆");// 扣除金豆
									cu.setId(id + 1);
									cu.setConsumeType("天天打卡");// 消费类型
									cu.setConsumeTime(days);// 消费时间
									cu.setUnit("打卡抽奖");
									cu.setConsumeStatus(1);// 完成消费
									cu.setUserId(das.getDkId());// 消费用户
									this.consumeRecordService.saveSelective(cu);

									AppUser qud = this.appuserService.queryById(das.getDkId());
									qud.setUserIntegral(qud.getUserIntegral() + vvs[a1]);
									this.appuserService.updateSelective(qud);
									a1++;
								}

							}

						}

					} else {
						System.out.println("no!什么也没有");
					}
					// 分发成功后，统一将参加了挑战，但未打卡的人，修改为打卡失败。
					dr.setDkStauts(2);
					List<DakaRule> li = super.queryListByWhere(dr);
					DakaRecord dc = new DakaRecord();
					for (DakaRule s : li) {
						dr.setDkStauts(1);// 打卡失败
						dr.setId(s.getId());
						dr.setDkTime(days);
						super.updateSelective(dr);

						// 打卡记录
						List<DakaRecord> de = this.dakaRecordService.queryAll();
						long id = de.size();
						dc.setId(id + 1);
						dc.setDakaStatus(1);// 打卡失败
						dc.setDakaTime(days);
						dc.setUserId(s.getDkId());
						this.dakaRecordService.saveSelective(dc);// 打卡记录
					}

				} else {
					System.out.println("no!今天没人成功打卡，你敢相信？");
					return 3;
				}

			}
			return 1;
		}
		return 2;

	}
	
	/**
	 * 点击早起打卡判断是否参加了打卡
	 * 判断的是昨天是否参与，如果已经参与，判断时间是否在范围内，在范围呢直接去打卡页面，不在范围内，查询是否参加明天的打卡挑战，参加了
	 * 去打卡页面，没有参加就去参与打卡挑战页面。如果昨天没有参与，判断是否参与明天的挑战，已参与，到打卡页面。没有参加就到参与打卡页面
	 * 
	 * @param user
	 * @return
	 */
	public Integer getweidaka(AppUser user) {
		String da1 = DateUtil.getDay();// 当前时期判断昨天是否参加打卡挑战
		DakaRule d = new DakaRule();
		d.setDkId(user.getUserId());
		d.setDkStauts(2);// 查询条件 2：表示参加了挑战
		d.setDay(da1);// 日期条件
		List<DakaRule> q = super.queryListByWhere(d);
		if (q.size() != 0) {
			// 如果已经参与 判断时间是否在范围内
			int a = DakaTime();// 是否在时间段内
			if (a == 1) {
				// 表示在打卡时间规定范围里，直接去打卡页面
				return 1;

			}
			// 不在时间范围里。
			String houDay = DateUtil.getAfterDay();// 查询明天是否参加了打卡挑战
			d.setDay(houDay);
			List<DakaRule> q1 = super.queryListByWhere(d);
			if (q1 != null && q1.size() == 1) {// 已经参加，但是只参加了一个，那么进入参加挑战页面

				return 2;

			} else if (q1.size() == 2) {// 已经参加，但是只参加了两个，那么进入打卡页面

				return 3;
			}
			// 没有参加，到参加页面
			return 4;

		}
		// 如果昨天没有参加
		String houDay = DateUtil.getAfterDay();// 查询明天是否参加了打卡挑战
		d.setDay(houDay);
		List<DakaRule> q1 = super.queryListByWhere(d);
		if (q1 != null && q1.size() == 1) {// 已经参加，但是只参加了一个，那么进入参加挑战页面

			return 5;

		} else if (q1.size() == 2) {// 已经参加，但是只参加了两个，那么进入打卡页面

			return 6;
		}
		// 昨天没有参加，到参加明天挑战页面
		return 7;
	}

	/**
	 * 微信支付参与打卡挑战
	 * 
	 * @param user
	 * @return
	 */
	public Integer injinDaka(AppUser user, Long typeId, String gold, Integer fangShi) {
		AppUser us = new AppUser();
		String day1=DateUtil.getDay();
		String time = DateUtil.getDay();// 参与打卡日期
		String houtime = DateUtil.getAfterDay();// 一天后的时间
		DakaRule d = new DakaRule();
		d.setDkId(user.getUserId());
		d.setDay(houtime);
		d.setTypeId(typeId);
		d.setDkStauts(2);// 已经参加的
		DakaRule dak = super.queryOne(d);// 查询是否有这条数据
		// String hour = t.getDakaClose();
		if (dak != null && !time.equals(houtime)) {
			folat = 2;
		} else {
			if (fangShi == 1) {
				// 支付方式为剩余金豆支付
				Integer yue = user.getUserIntegral();// 金额
				Integer gg = Integer.parseInt(gold);
				if(yue>=gg) {
					Integer v = yue - gg;// 剩余金豆
					us.setUserIntegral(v);
					us.setUserId(user.getUserId());
					this.appuserService.updateSelective(us);
					d.setDkStauts(2);
					d.setDay(houtime);
					d.setDkGold(gg);
					d.setName(user.getName());
					d.setPhoto(user.getHeadPortrait());
					super.saveSelective(d);
					folat = 1;
					// 消费记录
					ConsumeRecord cu = new ConsumeRecord();
					String day = DateUtil.getTime();
					cu.setMoney("-" + gold + "金豆");// 扣除金豆
					cu.setConsumeType("天天打卡");// 消费类型
					cu.setConsumeTime(day);// 消费时间
					cu.setConsumeStatus(1);// 完成消费
					cu.setUnit("账户剩余金豆");// 支付方式
					BigDecimal aa=new BigDecimal(gold);
					cu.setDay(day1);
					cu.setXiaoFeiType(2);//金豆支付
					cu.setConsumePrice(aa);
					cu.setUserId(user.getUserId());// 消费用户
					this.consumeRecordService.saveSelective(cu);

				}else {folat=3;}
			} else if (fangShi == 2) {
				// 支付方式为余额支付
				BigDecimal yue = user.getMoney();// 金额
				Integer gg = Integer.parseInt(gold);
				String ss = String.valueOf(gg / 100);
				BigDecimal dd= new BigDecimal(ss);
				int a=yue.compareTo(dd);
				if(a!=-1) {
					BigDecimal v = yue.subtract(new BigDecimal(ss));
					us.setUserId(user.getUserId());
					us.setMoney(v);
					this.appuserService.updateSelective(us);
					d.setDkStauts(2);
					d.setDay(houtime);
					d.setDkGold(gg);
					d.setName(user.getName());
					d.setPhoto(user.getHeadPortrait());
					super.saveSelective(d);
					folat = 1;
					// 消费记录
					ConsumeRecord cu = new ConsumeRecord();
					String day = DateUtil.getTime();
					cu.setMoney("-" + gg / 100 + "元");// 扣除余额
					cu.setConsumeType("天天打卡");// 消费类型
					cu.setConsumeStatus(1);// 完成消费
					cu.setConsumeTime(day);// 消费时间
					cu.setUnit("账户余额");// 支付方式
					BigDecimal aa=new BigDecimal( gg / 100);
					cu.setDay(day1);
					cu.setXiaoFeiType(1);//钱支付
					cu.setConsumePrice(aa);
					cu.setUserId(user.getUserId());// 消费用户
					this.consumeRecordService.saveSelective(cu);
				}else {folat=3;}
				
			} else {

				Integer gg = Integer.parseInt(gold);
				// 微信支付方式
				d.setDkId(user.getUserId());
				d.setDkStauts(2);
				d.setDay(houtime);
				d.setTypeId(typeId);
				d.setDkGold(gg);
				d.setName(user.getName());
				d.setPhoto(user.getHeadPortrait());
				super.saveSelective(d);
				folat = 1;
				// 消费记录
				ConsumeRecord cu = new ConsumeRecord();
				String day = DateUtil.getTime();
				cu.setMoney("-" + gg / 100 + "元");// 扣除余额
				cu.setConsumeType("天天打卡");// 消费类型
				cu.setConsumeStatus(1);// 完成消费
				cu.setConsumeTime(day);// 消费时间
				BigDecimal aa=new BigDecimal( gg / 100);
				cu.setDay(day1);
				cu.setXiaoFeiType(1);//钱支付
				cu.setConsumePrice(aa);
				cu.setUnit("微信支付");// 支付方式
				cu.setUserId(user.getUserId());
				this.consumeRecordService.saveSelective(cu);
			}

		}
		return folat;

	}

	

	/**
	 * 早起挑战打卡结束后，选出早起之星
	 * 
	 * @param typeId
	 * @return
	 */
	public List<DakaRule> getUpEarly(Long typeId) {
		List<String> list = new ArrayList<String>();
		DakaRule dr = new DakaRule();
		String time = DateUtil.getDay();// 当前的日期YYYY-MM-DD格式
		String qianTime = DateUtil.getQianDay();// 前一天
		dr.setTypeId(typeId);// 类型（如：1：一元区或者2：五元区）
		dr.setDkStauts(0);// 打卡成功
		dr.setDay(time);
		List<DakaRule> lp = super.queryListByWhere(dr);
		if (lp != null && !lp.isEmpty()) {
			for (DakaRule s : lp) {
				list.add(s.getDkTime());
			}
			Collections.sort(list);
			if (list != null && list.size() > 0) {
				String upEarly = list.get(0);
				dr.setDkTime(upEarly);
				List<DakaRule> upEarlylist = super.queryListByWhere(dr);
				return upEarlylist;
			} else {
				return new ArrayList<>();
			}

		}
		dr.setDay(qianTime);
		List<DakaRule> lp1 = super.queryListByWhere(dr);
		if (lp1 != null && !lp1.isEmpty()) {
			for (DakaRule s : lp1) {
				list.add(s.getDkTime());
			}
			Collections.sort(list);
			if (list != null && list.size() > 0) {
				String upEarly = list.get(0);
				dr.setDkTime(upEarly);
				List<DakaRule> upEarlylist = super.queryListByWhere(dr);
				return upEarlylist;
			} else {
				return new ArrayList<>();
			}
		}
		return new ArrayList<>();
	}

	/**
	 * 早起挑战打卡结束后，选出最佳手气者
	 * 
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<DakaRule> goldHighOne(Long typeId) {

		DakaRule br = new DakaRule();
		String time = DateUtil.getDay();// 用当日时时间作为查询条件
		String qianTime = DateUtil.getQianDay();// 前一天
		br.setTypeId(typeId);
		br.setDkStauts(0);
		br.setDay(time);
		List<DakaRule> lp = super.queryListByWhere(br);

		if (lp != null && !lp.isEmpty()) {
			List<Integer> list = new ArrayList<Integer>(lp.size());

			for (DakaRule s : lp) {
				list.add(s.getFpje());
			}
			Collections.sort(list);

			if (list != null && list.size() > 0) {
				Integer max = list.get(list.size() - 1);
				br.setFpje(max);
				List<DakaRule> maxlist = super.queryListByWhere(br);
				return maxlist;
			} else {
				return new ArrayList<>();
			}

		}
		br.setDay(qianTime);
		List<DakaRule> lp1 = super.queryListByWhere(br);
		List<Integer> list = new ArrayList<Integer>(lp1.size());
		if (lp1 != null) {
			for (DakaRule s : lp1) {
				list.add(s.getFpje());
			}
			Collections.sort(list);

			if (list != null && list.size() > 0) {
				Integer max = list.get(list.size() - 1);
				br.setFpje(max);
				List<DakaRule> maxlist = super.queryListByWhere(br);
				return maxlist;
			}
			return new ArrayList<>();
		}

		return new ArrayList<>();

	}

	/**
	 * 早起挑战打卡结束后，筛选出连续打卡次数最多者
	 * 
	 * @param typeId
	 * @return
	 */
	public List<DakaRule> lianXuDakaNum(Long typeId) {

		List<Integer> list = new ArrayList<Integer>();
		DakaRule br = new DakaRule();
		br.setTypeId(typeId);
		List<DakaRule> lp = super.queryListByWhere(br);
		if (lp != null && !lp.isEmpty()) {
			// 有打卡者
			for (DakaRule dk : lp) {
				list.add(dk.getMaxCont());
			}
			Collections.sort(list);
			Integer max = list.get(list.size() - 1);
			br.setMaxCont(max);
			List<DakaRule> maxlist = super.queryListByWhere(br);
			return maxlist;
		} else {
			return new ArrayList<>();
		}

	}

	/**
	 * 明日参与挑战打卡人数和挑战金奖池
	 * 
	 * @param typeId
	 * @return
	 */
	public Map<String, Integer> getAfterDk(Long id, Integer gold) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String day = DateUtil.getAfterDay();// 获取一天后的日期"yyyy-MM-dd"
		DakaRule dk = new DakaRule();
		dk.setTypeId(id);
		dk.setDay(day);
		Integer num = super.queryTotalCount(dk);// 总人数
		Integer moneynum = num * gold;// 总金额
		map.put("tomrow", num);// 明日参与挑战打卡人数
		map.put("moneynum", moneynum);// 挑战金奖池

		return map;
	}

	/**
	 * 今日参与打卡人数和今日成功打卡人数
	 * 
	 * @param
	 * @return
	 */
	public Map<String, Integer> getDkToday(Long id, Integer gold) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String day = DateUtil.getDay();// "yyyy-MM-dd"
		DakaRule dk = new DakaRule();
		dk.setTypeId(id);
		dk.setDay(day);
		int a = super.queryTotalCount(dk);// 今日参与打卡人数
		int num = a * gold;// 奖金池
		dk.setDkStauts(0);
		int v = super.queryTotalCount(dk);// 今日成功打卡人数
		map.put("today_canjia", a);
		map.put("today_success", v);
		map.put("today_jiangjichi", num);

		return map;
	}

	public Map<String, Integer> getDkTodayZongNum() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String day = DateUtil.getDay();// "yyyy-MM-dd"
		DakaRule dk = new DakaRule();
		// 类型1
		Dakaleixing dal = this.dakaleixinService.queryById(1l);
		dk.setDay(day);
		dk.setTypeId(1L);
		int a = super.queryTotalCount(dk);// 今日参与打卡人数
		int num = a * dal.getJine();// 奖金池

		// 类型2
		Dakaleixing dalx = this.dakaleixinService.queryById(2l);
		dk.setTypeId(2L);
		int aa = super.queryTotalCount(dk);// 今日参与打卡人数
		int numm = a * dalx.getJine();// 奖金池

		// 类型1
		dk.setTypeId(1L);
		dk.setDkStauts(0);
		int v = super.queryTotalCount(dk);// 今日成功打卡人数
		// 类型2
		dk.setTypeId(2L);
		dk.setDkStauts(0);
		int vv = super.queryTotalCount(dk);// 今日成功打卡人数

		int zongJine = num + numm;// 今日挑战总奖金池
		int canJaDaka = a + aa;// 今日参与打卡总人数
		int zongDaka = v + vv;// 成功打卡人数
		// 类型1区域
		map.put("can1", a);// 类型一今日成功打卡人数
		map.put("cangold1", num);// 类型一奖金池
		map.put("cg1", v);// 类型一今日成功打卡人数
		// 类型2区域
		map.put("can2", aa);// 类型二今日成功打卡人数
		map.put("cangold2", numm);// 类型二奖金池
		map.put("cg2", vv);// 类型二今日成功打卡人数
		// 总的
		map.put("tz", zongJine);// 今日挑战总奖金池
		map.put("cz", canJaDaka);// 今日参加总人数
		map.put("cgz", zongDaka);// 今日挑战成功的总人数
		return map;
	}

	/**
	 * 派对天天打卡
	 * 
	 * @param code
	 * @return
	 */
	public JSONObject gerJsonByDaKa(String code) {

		// 天天打卡小程序唯一标识 (在微信小程序管理后台获取)
		String wxspAppid = "wx9d82360ba0304046";
		// 小程序的 app secret (在微信小程序管理后台获取)
		String wxspSecret = "cef00252dbb5c92677f456be9bbb26ff";
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
}
