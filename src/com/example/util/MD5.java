package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static String getDate(String str){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte [] date = digest.digest(str.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i < date.length;i++){
				String result = Integer.toHexString(date[i]&0xff);
				String temp = null;
				if(result.length()==1){
					temp = "0" + result;
				}else{
					temp = result;
				}
				sb.append(temp);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
