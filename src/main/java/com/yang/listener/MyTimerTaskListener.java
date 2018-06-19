package com.yang.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyTimerTaskListener implements ServletContextListener{
	
//	Timer timer=new Timer();
//	@SuppressWarnings("static-access")
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// QuartzManager quartzManager = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(QuartzManager.class);
		System.out.println("-----------这个暂时没有用了-------------");
//		QuartzManager.addJob("daka进入", HelloQuartz.class, "0/20 * * * * ?");
		//new TimerManager();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("-----------结束时取消------------");
		}
		
	}


