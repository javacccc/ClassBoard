package com.bjw.bean;

/*************************************************
 *@date：2017/10/31
 *@author：  zxj
 *@description： 封装好数据然后用于页面上的传递
*************************************************/
public class BeanForSerial {
	public String totalnum="";
	public int flag;
	public BeanForSerial(String num, int flag1){
		totalnum=num;
		flag=flag1;
	}
}