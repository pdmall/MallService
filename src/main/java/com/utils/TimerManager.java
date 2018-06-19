package com.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yang.service.DakaService;
import com.yang.service.DakaleixingService;
@Component
public class TimerManager {
    @Autowired
    DakaleixingService dakaleixinService;
	// 时间间隔
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public TimerManager() {
		Calendar calendar = Calendar.getInstance();

		/*** 定制每日8:00执行方法 ***/

		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 50);
		calendar.set(Calendar.SECOND, 0);

		Date date = calendar.getTime(); // 第一次执行定时任务的时间

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("-------执行任务-----成功打卡随机获得金豆-----");
				DakaService dk=new DakaService();
				Integer list=dk.getdakaGold();
				if(list==1) {
					System.out.println("分配成功啦");
				}else if(list==2) {
					System.out.println("糟糕，操作没成功！");
				}
				System.out.println("糟糕，没有人参见/没人爱！");
			}
		}, date, PERIOD_DAY);// 这里设定将延时每天固定执行
	}
	// 安排指定的任务在指定的时间开始进行重复的固定延迟执行.
	// timer.scheduleAtFixedRate(task,date,PERIOD_DAY);
	// }
	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}
