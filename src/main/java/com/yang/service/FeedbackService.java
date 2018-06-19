package com.yang.service;

import org.springframework.stereotype.Service;
import com.utils.StringUtils;
import com.yang.model.AppUser;
import com.yang.model.Feedback;

/**
 * @author 00
 *  意见反馈
 */
@Service
public class FeedbackService extends BaseService<Feedback>{

	public Integer addFeedback(AppUser user, String content) {
		
		Feedback fe=new Feedback();
		if(content!=null&&!"".equals(content)) {
			String cs = StringUtils.replaceBlank(content);// 正则去空格换行符
			fe.setFeUserId(user.getUserId());
			fe.setContent(cs);
			super.saveSelective(fe);
			return 1;
			
		}
		
		return 2;
	}

}
