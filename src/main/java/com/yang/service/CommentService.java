package com.yang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.utils.DateUtil;
import com.utils.StringUtils;
import com.yang.mapper.CommentMapper;
import com.yang.model.AppUser;
import com.yang.model.Comment;
import com.yang.model.Great;
import com.yang.model.Wishes;

@Service
public class CommentService extends BaseService<Comment> {
	Integer fal = 1;
	Boolean flag = false;
	@Autowired
	private GreatService greatservice;
	@Autowired
	private WishesService wishesService;
	
	@Autowired
	private CommentMapper commentMapper;
	/**
	 * 根据消息ID发布评论
	 * 
	 */
	public Integer addcomment(AppUser user, Long id, String content) {

		String a = user.getUserId();
		Comment co = new Comment();
		Wishes w=new  Wishes();
		if (content != null) {
			String cs = StringUtils.replaceBlank(content);// 正则去空格换行符
			String ctime = DateUtil.getTime();
			co.setcUserId(a);
			co.setcPictureId(id);// 消息Id
			int aa = super.queryTotalCount(co);// 判断是否已经评论
			if (aa == 0) {
				co.setContent(cs);// 评论内容
				co.setcUserName(user.getName());// 获得昵称
				co.setcUserPhoto(user.getHeadPortrait());// 头像
				co.setcStatus(0);// 提交等待审核
				co.setcTime(ctime);// 存入评论时间
				super.saveSelective(co);// 存入信息
				   w.setId(id);
				   Wishes s=this.wishesService.queryById(id);
				  w.setCommentSum(s.getCommentSum()+1);
				  this.wishesService.updateSelective(w);
				fal = 1;
			} else {
				fal = 2;
			}
		} else {
			fal = 2;
		}

		return fal;
	}

	/**
	 * 根据消息ID显示评论 评论列表按点赞次数排序s
	 * 
	 * @param
	 * 
	 */
	public List<Comment> getComent(Long id, AppUser user) throws Exception {
		List<Comment> lis = new ArrayList<Comment>();
		Comment go=new Comment();
		Example example = new Example(go.getClass());
		example.setOrderByClause("created DESC ");
		//查询审核通过的.andEqualTo("c_status", 1)审核状态 0：正在审核   1：通过  2：拒绝  
		example.createCriteria().andEqualTo("cPictureId",id);
		Great gt = new Great();
		List<Comment> list = this.commentMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {// 判断是否有评论
			for (Comment bb : list) {
				// 遍历评论表
				gt.setgUserId(user.getUserId());
				gt.setCommentId(bb.getId());// 该组评论的消息ID
				Great great = this.greatservice.queryOne(gt);
				if (great != null) {
					// 点了赞
					bb.setFlag(1);
				} 
					// 未点赞
				lis.add(bb);
			}
			return lis;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 根据.点赞评论
	 * 
	 * @param user
	 *            pId id
	 */
	public void addCommentDianZan(AppUser user, Long id, Long pictureId) {
		Great gr = new Great();
		gr.setgUserId(user.getUserId());// 用户ID
		gr.setCommentId(id);// 评论id
		List<Great> list = this.greatservice.queryListByWhere(gr);
		// 查询是否有该用户对该该评论的点赞记录
		if (list != null && list.size() > 0) {
			// 如果找到了这条记录，则删除该记录，同时该条评论的点赞数减1
			Great great = list.get(0);
			// 删除记录
			this.greatservice.deleteById(great.getId());
			// 该条评论点赞数减1
			Comment com = super.queryById(id);// 根据评论id查询出评论对象
			com.setcNum(com.getcNum() - 1);
			super.updateSelective(com);
		} else {
			// 如果没有找到这条记录，则添加这条记录，同时评论点赞数加1
			// 添加记录
			gr.setgUserId(user.getUserId());// 用户ID
			gr.setPictuerId(pictureId);// 该组图片id
			gr.setCommentId(id);// 评论id
			this.greatservice.saveSelective(gr);
			// 该条评论点赞数加1
			Comment com = super.queryById(id);// 根据评论id查询出评论对象
			com.setcNum(com.getcNum() + 1);
			super.updateSelective(com);
		}
	}

}
