package com.cherry.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cherry.utils.http.sync.HttpConnectionManager;
import com.cherry.utils.ipseek.IPSeeker;
import com.cherry.utils.ipseek.IPSeekerImpl;

public class IPSeekTest {
	private static String url = "http://ip.taobao.com/service/getIpInfo.php?ip=";
	static String[] ips = new String[]{"221.176.14.72","183.221.220.220","61.234.123.64","116.236.216.116","218.207.10.68",
		"221.10.102.199","218.7.132.1","221.10.40.234","114.255.183.164","119.97.164.48","202.106.16.36","202.105.247.122",
		"183.221.217.36","58.42.236.241","218.59.144.95","218.90.174.167","183.207.232.194","117.21.192.7","101.4.60.43","117.156.8.72",
		"64.31.22.143","108.165.33.5","183.89.105.171","93.115.8.229","64.31.22.131","118.97.66.4","89.46.101.122","14.139.111.91",
		"202.29.235.122","199.200.120.36","46.209.216.100","202.119.199.147","61.138.104.30","202.198.17.141","111.77.123.8","112.243.251.19","219.149.247.224","112.120.87.20"};
	public static void main(String[] args) throws IOException {
		IPSeeker iPSeeker = new IPSeekerImpl();
		System.out.println(iPSeeker.getIPLocation("117.136.11.14"));
		JacksonUtils instance = JacksonUtils.getInstance();
		
		for (String ip : ips) {
			String stringResult = HttpConnectionManager.getStringResult(url+ip, true);
			if(StringUtils.isNotBlank(stringResult)){
				Map<String, Object> readJSON2Map = instance.readJSON2Map(stringResult);
				System.out.println(readJSON2Map);
			}
			System.out.println(ip+"---->"+iPSeeker.getIPLocation(ip));
		}
		
		System.out.println("");
		System.out.println("");
		
		
	}
	
	

}
