package com.yang.test;

import java.util.TimerTask;

import com.utils.Tools;

public class TimerTaskTest extends TimerTask{  
  
@Override  
public void run() {  
   // TODO Auto-generated method stub  
   System.out.println("第一次使用定时器");
   
   System.out.println(Tools.getRandomNum());
   
   
}  
}  