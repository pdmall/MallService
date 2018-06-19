package com.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


public class WxAlgorithm{
	 
	 /**
	 * @param moneySum 输入总金额
	 * @param redNum 输入红包数量
	 */
	
	public static double getwxAlgorithm(double moneySum, int redNum) {
	 // 设置最小的金额
	 double moneyMin = 0.01;
	 Random random = new Random();
	 //精确小数点2位
	 NumberFormat formatter = new DecimalFormat("#.##");
	 for (int a=1;a<redNum;)
	 {
	  //随机一个数，数值范围在最小值与余额之间
	  String money = formatter.format(random.nextDouble() * (moneySum - moneyMin) + moneyMin);
	  //数值转换
	    moneySum = Double.valueOf(formatter.format(moneySum - Double.valueOf(money)));
	  
	 // System.out.println("第"+a+"个红包：" + money + "元 ,余额：" + moneySum);
	  return Double.valueOf(money);
	 }
	  
	 //System.out.println("最后个红包：" + moneySum + "元 ,余额：" + (moneySum - moneySum));
	return moneySum;
	 }
	 
	}
