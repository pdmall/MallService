package com.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 根据经纬度获取地址信息
 * @author a55660000
 *
 */
public class GetLocation {
	
	public static void main(String[] args) {
		List<String> a=new ArrayList<>();
		List<String> b=new ArrayList<>();
		b.add("aaa");
		b.add("1114");
		a.addAll(b);

		System.out.println(a);
	}
	
	 public static String getArea(String log, String lat ) {    
	        // lat 31.2990170   纬度      
	        //log 121.3466440    经度  
	        String add = getAdd(log, lat);    
	        JSONObject jsonObject = JSONObject.fromObject(add);    
	        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));    
	        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));    
	        String allAdd = j_2.getString("admName");    
	        String arr[] = allAdd.split(","); 
	        if(arr.length<3) {
	        	return null;
	        }
	       
	       return arr[2];
	    }    
	        
	    public static String getAdd(String log, String lat ){    
	        //lat 小  log  大    
	        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
	    	//http://gc.ditu.aliyun.com/regeocoding?l=29.579214,103.46238&type=010
	        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+log+","+lat+"&type=010";    
	        String res = "";       
	        try {       
	            URL url = new URL(urlString);      
	            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();      
	            conn.setDoOutput(true);      
	            conn.setRequestMethod("POST");      
	            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));      
	            String line;      
	           while ((line = in.readLine()) != null) {      
	               res += line+"\n";      
	         }      
	            in.close();      
	        } catch (Exception e) {      
	            System.out.println("error in wapaction,and e is " + e.getMessage());      
	        }     
	         
	        return res;      
	    }    

}
