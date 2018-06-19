package com.yang.test;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yang.service.DakaService;
@Component
public class HelloQuartz implements Job {
	
	 @Autowired
	  public DakaService service;

	protected Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//JobDetail detail = context.getJobDetail();
	//	Object s = detail.getJobDataMap().get("parameterList");
		Integer list=service.getdakaGold();
		System.out.println(list);
		
		
		
	}
	}	
		

