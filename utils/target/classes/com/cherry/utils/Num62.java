package com.cherry.utils;

/**
 * 62进制数字
 * 
 */
public class Num62 {
	/**
	 * 62个字母和数字，含大小写
	 */
	public static final char[] N62_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	/**
	 * 36个小写字母和数字
	 */
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	/**
	 * 长整型用N36表示的最大长度
	 */
	public static final int LONG_N36_LEN = 13;
	/**
	 * 长整型用N62表示的最大长度
	 */
	public static final int LONG_N62_LEN = 11;

	/**
	 * 长整型转换成字符串
	 * 
	 * @param l
	 * @param chars
	 * @return
	 */
	private static StringBuilder longToNBuf(long l, char[] chars) {
		int upgrade = chars.length;
		StringBuilder result = new StringBuilder();
		int last;
		while (l > 0) {
			last = (int) (l % upgrade);
			result.append(chars[last]);
			l /= upgrade;
		}
		return result;
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN62(long l) {
		return longToNBuf(l, N62_CHARS).reverse().toString();
	}

	/**
	 * 长整型转换成N36
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN36(long l) {
		return longToNBuf(l, N36_CHARS).reverse().toString();
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @param length
	 *            如不足length长度，则补足0。
	 * @return
	 */
	public static String longToN62(long l, int length) {
		StringBuilder sb = longToNBuf(l, N62_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * 长整型转换成N36
	 * 
	 * @param l
	 * @param length
	 *            如不足length长度，则补足0。
	 * @return
	 */
	public static String longToN36(long l, int length) {
		StringBuilder sb = longToNBuf(l, N36_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * N62转换成整数
	 * 
	 * @param n62
	 * @return
	 */
	public static long n62ToLong(String n62) {
		return nToLong(n62, N62_CHARS);
	}

	/**
	 * N36转换成整数
	 * 
	 * @param n36
	 * @return
	 */
	public static long n36ToLong(String n36) {
		return nToLong(n36, N36_CHARS);
	}

	private static long nToLong(String s, char[] chars) {
		char[] nc = s.toCharArray();
		long result = 0;
		long pow = 1;
		for (int i = nc.length - 1; i >= 0; i--, pow *= chars.length) {
			int n = findNIndex(nc[i], chars);
			result += n * pow;
		}
		return result;
	}

	private static int findNIndex(char c, char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				return i;
			}
		}
		throw new RuntimeException("N62(N36)非法字符：" + c);
	}

	/** 62进制对应码表  */
	final static char[] digits = new char[] {
			'0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' , 
			'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' ,
			'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' ,
			'u' , 'v' , 'w' , 'x' , 'y' , 'z' , 'A' , 'B' , 'C' , 'D' ,
			'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' , 'M' , 'N' ,
			'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'X' ,
			'Y'
	};
	
	
	/**
	 * 依照62位对应码表关系，获取字符在表中的索引位置
	 * @param c 需要获取索引的字符
	 * @return 索引位置
	 */
	private static int get61Position(char c) {
		int i = (int)c;
		if (i > 47 && c < 58)
			return i - 48;
		if (i > 96 && i < 123)
			return i - 87;
		if (i > 64 && i < 91)
			return i - 29;
		return 0;
	}
	
	/**
	 * 将整型转换成62进制字符串
	 * @param l 要进行转换的数值
	 * @return 转换后的62进制结果
	 */
	public static String toString61(long l) {
		char[] buffer = new char[65];
		int charPos = 64;
		boolean negative = (l < 0) ? true : false;
		if (!negative) {
			l = -l;
		}
		while (l <= -61) {
			buffer[charPos--] = digits[(int) (-(l % 61))];
			l = l / 61;
		}
		buffer[charPos] = digits[(int) (-l)];
		if (negative) {
			buffer[charPos] = '-';
		}
		return new String(buffer, charPos, (65 - charPos));
	}
	
	/**
	 * 将62进制字符串转换成长整型
	 * @param s 要转换的62进制字符
	 * @return 转换后的整型数值
	 * @throws NumberFormatException
	 */
	public static long parseLong61(String s) throws NumberFormatException {
		if (s == null)
			throw new NumberFormatException("null");
		long res = 0;
		for (int i = 0; i < s.length(); i++) {
	        res = res * 61 + get61Position(s.charAt(i));
        }
		return res;
	}
	
	public static void main(String[] args) {
		System.out.println(longToN62(Long.MAX_VALUE));
		System.out.println(toString61(Long.MAX_VALUE));
	}
}
