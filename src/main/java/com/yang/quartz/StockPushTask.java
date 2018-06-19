package com.yang.quartz;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yang.service.DakaService;
/**
 * 
 * @author YJG
 *2018/06/05
 */
@Component  
public class StockPushTask {  
  
    @Resource  
    private DakaService dakaService;  
  
      
    public void test() {  
        System.out.println("————每天早上八点统一瓜分金豆————");  
      
        Integer a=dakaService.getdakaGold();
        if(a==1) {
        	
        	System.out.println("金豆分配成功");
        }else if(a==2){
        	System.out.println("类型数据为空");
        }
    }
    } 
