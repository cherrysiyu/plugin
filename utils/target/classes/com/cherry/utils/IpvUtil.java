package com.cherry.utils;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title. IPV公共功能类<br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2014-8-5 上午10:43:55
 * <p>
 * 
 * <p>
 * <p>
 * Version: 1.0.0
 * <p>
 */
public class IpvUtil {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(IpvUtil.class);
	/**
	 * 协议版本4
	 */
	private static final int IPV4 = 4;
	/**
	 * 协议版本6
	 */
	private static final int IPV6 = 6;

	/**
	 * 判断IP地址是v4还是v6
	 * 
	 * @param address
	 *            IP地址
	 * @return 4：v4；6：v6；0：非正常IP
	 * @throws UnknownHostException
	 */
	public static int getVersionByAddress(String addr) {
		int result = 0;
		try {
			if (StringUtils.isBlank(addr)) {
				logger.error("address is empty!");
				return result;
			}

			InetAddress inetAddress = InetAddress.getByName(addr);
			if (inetAddress instanceof Inet4Address) {
				result = IPV4;
			} else if (inetAddress instanceof Inet6Address) {
				result = IPV6;
			}
		} catch (UnknownHostException e) {
			logger.error("address error, is not correct address: " + addr, e);
		}

		return result;
	}

	/**
	 * 判断两个IP地址是否相同(V6、V4均支持)
	 * 
	 * @param addr1
	 *            待比较的地址1
	 * @param addr2
	 *            待比较的地址2
	 * @return true:相同，false:不同
	 */
	public static boolean ipEquals(String addr1, String addr2) {
		String fullIp1 = getFullIP(addr1);
		String fullIp2 = getFullIP(addr2);

		if (StringUtils.isNotBlank(fullIp1) && fullIp1.equals(fullIp2)) {
			return true;
		}

		return false;
	}

	/**
	 * IPV6地址是否与IPV4兼容
	 * 
	 * @param addr
	 *            IPV6地址
	 * @return true:兼容，false:不兼容
	 */
	public static boolean isIPv4CompatibleAddress(String addr) {
		if (IPV6 != getVersionByAddress(addr)) {
			logger.error("addr is not ipv6 version!");
			return false;
		}

		try {
			Inet6Address inet6Address = (Inet6Address) InetAddress
					.getByName(addr);
			return inet6Address.isIPv4CompatibleAddress();
		} catch (UnknownHostException e) {
			logger.error("address error, is not correct address: " + addr, e);
		}

		return false;

	}

	/**
	 * 校验IP是否属于地址段（V6、V4均支持）
	 * 
	 * @param addr
	 *            待校验地址
	 * @param validIPs
	 *            地址段
	 * @return
	 */
	public static Boolean validateADDF(String addr, String validIPs) {
		if (StringUtils.isBlank(validIPs)) {
			logger.error("validIPs is empty!");
			return false;
		}

		String ip = getFullIP(addr);
		String[] ips = validIPs.split(";");

		for (String ipTmp : ips) {
			if (!StringUtils.isEmpty(ipTmp)) {
				if (ipTmp.contains("~")) {
					String[] ipArea = ipTmp.split("~");
					// 如果IP段在起始与结束中间，则为匹配的
					if (StringToBigInt(ip).compareTo(StringToBigInt(ipArea[0])) >= 0
							&& StringToBigInt(ip).compareTo(
									StringToBigInt(ipArea[1])) <= 0) {
						return true;
					}
				} else {
					if (ip.equals(ipTmp)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 获取全IP地址，一般用于IPV6
	 * 
	 * @param addr
	 * @return
	 */
	public static String getFullIP(String addr) {
		String hostAddress = "";
		try {
			InetAddress inetAddress = InetAddress.getByName(addr);
			hostAddress = inetAddress.getHostAddress();
			logger.info("Full IP: " + hostAddress);
		} catch (UnknownHostException e) {
			logger.error("address error, is not correct address: " + addr, e);
		}

		return hostAddress;
	}

	/**
	 * 将地址字符串转为大Int型（V6、V4均支持）
	 * 
	 * @param ipInString
	 * @return
	 */
	private static BigInteger StringToBigInt(String ipInString) {
		ipInString = ipInString.replace(" ", "");
		byte[] bytes;
		if (ipInString.contains(":"))
			bytes = ipv6ToBytes(ipInString);
		else
			bytes = ipv4ToBytes(ipInString);
		return new BigInteger(bytes);
	}

	/**
	 * ipv6地址转有符号byte[17]
	 * 
	 * @param ipv6
	 * @return
	 */
	private static byte[] ipv6ToBytes(String ipv6) {
		byte[] ret = new byte[17];
		ret[0] = 0;
		int ib = 16;
		boolean comFlag = false;// ipv4混合模式标记
		if (ipv6.startsWith(":"))// 去掉开头的冒号
			ipv6 = ipv6.substring(1);
		String groups[] = ipv6.split(":");
		for (int ig = groups.length - 1; ig > -1; ig--) {// 反向扫描
			if (groups[ig].contains(".")) {
				// 出现ipv4混合模式
				byte[] temp = ipv4ToBytes(groups[ig]);
				ret[ib--] = temp[4];
				ret[ib--] = temp[3];
				ret[ib--] = temp[2];
				ret[ib--] = temp[1];
				comFlag = true;
			} else if ("".equals(groups[ig])) {
				// 出现零长度压缩,计算缺少的组数
				int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
				while (zlg-- > 0) {// 将这些组置0
					ret[ib--] = 0;
					ret[ib--] = 0;
				}
			} else {
				int temp = Integer.parseInt(groups[ig], 16);
				ret[ib--] = (byte) temp;
				ret[ib--] = (byte) (temp >> 8);
			}
		}
		return ret;
	}

	/**
	 * ipv4地址转有符号byte[5]
	 * 
	 * @param ipv4
	 * @return
	 */
	private static byte[] ipv4ToBytes(String ipv4) {
		byte[] ret = new byte[5];
		ret[0] = 0;
		// 先找到IP地址字符串中.的位置
		int position1 = ipv4.indexOf(".");
		int position2 = ipv4.indexOf(".", position1 + 1);
		int position3 = ipv4.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ret[1] = (byte) Integer.parseInt(ipv4.substring(0, position1));
		ret[2] = (byte) Integer.parseInt(ipv4.substring(position1 + 1,
				position2));
		ret[3] = (byte) Integer.parseInt(ipv4.substring(position2 + 1,
				position3));
		ret[4] = (byte) Integer.parseInt(ipv4.substring(position3 + 1));
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(validateADDF("192.168.10.112", "192.168.0.0~192.168.10.255;192.168.0.1;10.20.0.0~10.20.0.1"));
	}
}
