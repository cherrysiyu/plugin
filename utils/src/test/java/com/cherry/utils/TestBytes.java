package com.cherry.utils;

import org.junit.Test;

public class TestBytes {
	
	
	@Test
	public  void testBytes() {
		int[] array = new int[]{18,189,217,1023,1048,12345,654321};
		
		for (int i : array) {
			byte[] int2Bytes = BitUtils.int2Bytes(i);
			LogUtils.debug(BitUtils.readInt(int2Bytes));
		
		}
		LogUtils.debug("==================================");
		short[] shortArray = new short[]{18,189,217,1023,1048,12345,6543};
		for (short s : shortArray) {
			byte[] short2Bytes = BitUtils.short2Bytes(s);
			LogUtils.debug(BitUtils.readShort(short2Bytes));
		}
	}
	
	public static void main(String[] args) {
		
	}

}
