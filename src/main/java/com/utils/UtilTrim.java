package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UtilTrim {
	public static String delTrim(String s) {
		
		return s.trim();
	}
    public static String getDate() {
    	  Date date=new Date();
  		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		String time=format.format(date);
  		return time;
    }
    public static String getDate1() {
  	  Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		return time;
  }
    public static String getRandomFileName() {
		  SimpleDateFormat simpleDateFormat;
		  simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		  Date date = new Date();
		  String str = simpleDateFormat.format(date);
		  Random random = new Random();
		  int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		  return rannum + str;// 当前时间  
		  }
}
