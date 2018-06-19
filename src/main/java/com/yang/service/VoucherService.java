package com.yang.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.yang.mapper.VoucherMapper;
import com.yang.model.AppUser;
import com.yang.model.MemberUser;
import com.yang.model.RobVoucher;
import com.yang.model.Voucher;

/**
 * @author 00
 *
 */
@Service
public class VoucherService extends BaseService<Voucher> {
	@Autowired
	private MemberUserService mService;// 会员
	@Autowired
	private RobVoucherService roService;// 用户表
	@Autowired
	private VoucherMapper  vochMapper;
	
	Integer flag = 0;

	/**
	 * 会员抢现金券
	 *
	 */
	public Integer getXianVocher(AppUser user, Integer upType,Long activityId) {
		MemberUser me = new MemberUser();
		String day = DateUtil.getDay();// 获取当前日期
		String uId = user.getUserId();// 用户ID
		me.setMemberUserId(uId);//
		MemberUser m = this.mService.queryOne(me);
		// 判断该用户是否是会员，
		if (m != null) {
			me.setmStatus(0);// 没有过期
			MemberUser mr = this.mService.queryOne(me);//
			// 是否到期
			if (mr != null) {// 没有过期。是会员，参与抢券
				Voucher vo = new Voucher();
				vo.setActivityId(activityId);//活动id
				vo.setUpType(upType);//0平台发布还是1商家发布
				vo.setQiangQuan(0);// 还没有被抢的现金券
				vo.setUpTime(day);//发布日期
				List<Voucher> list = super.queryListByWhere(vo);
				vo.setQiangQuan(1);// 已经被抢的现金券
				vo.setvUserId(uId);
				Voucher vc=super.queryOne(vo);
				if(vc!=null) {
					
					return 6;//你已经抢过了
				}
				// 参与抢券，一百张现金券，通过发布时间，发布人（平台/商户）先查询出这一百张现金券，将序号放入集合，方便删除
				
				
				List<Integer> list2 = new ArrayList<Integer>();
				if (list != null && !list.isEmpty()) {
					// 不为空。显示剩余现金券的数量
					for (Voucher v : list) {
						int s = (v.getId()).intValue();
						list2.add(s);
					}
					Collections.sort(list2); // 进行排序
					// 随机数，范围是，取出id最小值，id最大值*4;在这个范围里取随机数，
					Random random = new Random();
					int min = list2.get(0);// 最小
					int max = list2.get(list2.size() - 1);// 最大的一个元素
					// list2有了,今天所有发布的现金券的序号，放在了集合里，
					int s1 = random.nextInt(max * 4) % (max - min + 1) + min;
					// 判断取出的随机数是否在这个集合里if(arrayList.contains(3))
					if (list2.contains(s1)) {
						// 恭喜抢到券，如果在集合里,不再被选取
						vo.setvUserId(uId);
						vo.setId((long) s1);
						vo.setvStatus(1);// 已经被抢了
						// 根据序号id,将用户id存入该现金券，返回值 1 恭喜抢券成功。
						super.updateSelective(vo);
						
						
						//更新活动表券的总数
						RobVoucher rob=this.roService.queryById(activityId);
						Integer num=rob.getNum();//券总数
						RobVoucher rob1 = new RobVoucher();
						rob1.setId(activityId);
						rob1.setNum(num-1);
						this.roService.updateSelective(rob1);
						flag = 1;

					} else {
						// 如果不在集合里，谢谢参与，返回值 2
						flag = 2;
						
					}

				} else {
					// 集合为空，那么券已抢完，改天来
					flag = 3;
				}

			} else {
				// 会员号已经过期，返回 4 ,重新购买
				flag = 4;
			}
		} else {// 不是会员，
				// 返回 5，你还不是会员，请购买会员参与抢券。
			flag = 5;
		}

		return flag;
	}

	/**
	 * 获取抢券活动内容
	 *
	 */
	public List<RobVoucher> getVocherList(AppUser user) {
		Voucher vo = new Voucher();
		String day = DateUtil.getDay();
		String uid = user.getUserId();
		vo.setUpTime(day);
		vo.setvUserId(uid);
		vo.setQiangQuan(1);
		List<Voucher> cs = super.queryListByWhere(vo);
		if (cs != null && !cs.isEmpty()) {// 是否抢了券

			List<RobVoucher> list = this.roService.queryAll();
			List<RobVoucher> list2 = new ArrayList<RobVoucher>();
			if (list != null && !list.isEmpty()) {
				for (RobVoucher ro : list) {
					vo.setUpType(ro.getUpPosition());

					if (super.queryOne(vo) != null) {
						ro.setStatus(1);
						list2.add(ro);
					}
					list2.add(ro);
				}
				return list2;
			} else {

				return new ArrayList<>();
			}

		} else {

			return this.roService.queryAll();
		}

	}

	public String getUseRule(Long id) {

		RobVoucher s = this.roService.queryById(id);
		if (s != null) {
			if (!"".equals(s.getUseRule())) {
				return s.getUseRule();
			}
			return null;

		}
		return null;

	}

	/**
	 * 查询自己的抵用券
	 * @param user
	 * @return
	 */
	public List<Voucher> getVoucherByUserId(AppUser user) {

		Voucher cr = new Voucher();
		String uId=user.getUserId();
		Example example = new Example(cr.getClass());
		example.setOrderByClause("created DESC ");
		example.createCriteria().andEqualTo("vUserId", uId);
		List<Voucher> list=this.vochMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return new ArrayList<>();
	}

}
