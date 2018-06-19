package com.utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author YJG 随机红包生成
 *  @category 2018/04/20
 */

public class MoneyRandom {
	public static Integer[] getMoneyRandom(int daz,double moneySum, int redNum, double moneyMin) {
		// 设置最小的金额
		int a = 0;
		Integer[] arry = new Integer[daz];
		Random random = new Random();
		// 精确小数点2位
		// NumberFormat formatter = new DecimalFormat("#.##");
		for (int i = 1; i < redNum; i++) {
			// 随机一个数，数值范围在最小值与余额之间 (max-min) + min);
			double money = random.nextDouble() * ((moneySum / redNum) * 2 - moneyMin);

			double amoney = m1(money);// 随机金额，保留两位小数
			// 余额
			moneySum = m1(moneySum - amoney);// 余额，精确小数点2位
			double money2 = amoney <= moneyMin ? moneyMin : amoney;
			arry[a] = (int) (money2 * 100);

			a++;
			System.out.println("第" + i + "个红包：" + money2 + "金豆 ,余额：" + moneySum);
		}
		arry[a] = (int) (moneySum * 100);
		System.out.println("最后个红包：" + moneySum + "金豆,余额：" + (moneySum - moneySum));
		return arry;
	}

	/**
	 * 保留两位小数
	 * 返回double类型
	 */
	public static double m1(double f) {
		BigDecimal bg = new BigDecimal(f);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
		return f1;
	}
	
	public static void main(String[] agr) {
		
		Integer[] a=getMoneyRandom(30, 30, 30, 1);
		 for(int i = 0; i < a.length;i++){
			 System.out.println(a[i]);
		    }
		
	} 

	public static Integer[] getHBje(int daz,int num,double money) {
		Integer[] array1 = new Integer[daz];
		List<Double> array = new ArrayList<>();
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < num; i++) {
            array.add(r.nextDouble()*money + 0.1 * num * money);//经过小小的计算，这样使最小的钱尽量接近0.1；num越大，此计算越没有用
            sum += array.get(i);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, array.get(i) / sum*money);
        }
        Collections.sort(array);
        //double z=1.0*money;
        for (int i = 0; i < array.size(); i++) {//将钱进行分配；
            if(array.get(i)<=0.5){//不足0.1的直接给0.1；
                //z-=0.01;
                array.set(i, 0.5);
            }
            else if(i==array.size()-1){
                //array.set(i, (int)(z*100)*1.0/100);
            	//将剩余的一点money给最后一个人，因为排序，最后一个人最大份，所以最后分配的肯定是正数
                BigDecimal he =new BigDecimal("0");
                for(int j=0;j<array.size()-1;j++){
                    BigDecimal b =new BigDecimal(Double.toString(array.get(j)));
                    he=he.add(b);
                }
                BigDecimal moneyb =new BigDecimal(Double.toString(money));
                array.set(i, moneyb.subtract(he).doubleValue());
            }
            else{
                array.set(i, (int)(array.get(i)*100)*1.0/100);
                //z-=array.get(i);
            }
        }
        Collections.shuffle(array);
        int i=0;
        for(Double a:array){
        	System.out.println("瓜分到金豆："+"-->>>>>>>"+a*100);
        	array1[i]= (int) (a * 100);;
        	i++;
        }
		return array1;
   
		
	}
	
	public static double getFuDai(int num,double money) {
		List<Double> array = new ArrayList<>();
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < num; i++) {
            array.add(r.nextDouble()*money + 0.01 * num * money);//经过小小的计算，这样使最小的钱尽量接近0.01；num越大，此计算越没有用
            sum += array.get(i);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, array.get(i) / sum*money);
        }
        Collections.sort(array);
        //double z=1.0*money;
        for (int i = 0; i < array.size(); i++) {//将钱进行分配；
            if(array.get(i)<=0.01){//不足0.01的直接给0.01；
                //z-=0.01;
                array.set(i, 0.01);
            }
            else if(i==array.size()-1){
                //array.set(i, (int)(z*100)*1.0/100);
            	//将剩余的一点money给最后一个人，因为排序，最后一个人最大份，所以最后分配的肯定是正数
                BigDecimal he =new BigDecimal("0");
                for(int j=0;j<array.size()-1;j++){
                    BigDecimal b =new BigDecimal(Double.toString(array.get(j)));
                    he=he.add(b);
                }
                BigDecimal moneyb =new BigDecimal(Double.toString(money));
                array.set(i, moneyb.subtract(he).doubleValue());
            }
            else{
                array.set(i, (int)(array.get(i)*100)*1.0/100);
                //z-=array.get(i);
            }
        }
        Collections.shuffle(array);
        for(Double a:array){
        	//System.out.println(a);
        	sum= a;
        }
        if(sum==0) {
        	sum+=3;
        }
          
        return sum;
        
     
		
	}

	
	
}
