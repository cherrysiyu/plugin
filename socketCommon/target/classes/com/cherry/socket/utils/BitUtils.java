package com.cherry.socket.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Utility methods for packing/unpacking primitive values in/out of byte arrays using big-endian byte ordering.
 * 
 * @author Administrator
 *
 */
public class BitUtils {
	
	private static final Charset defaultEncode = Charset.forName("UTF-8");
	private static final String EMPTY_STRING = "";
	

	//Methods for unpacking primitive values from byte arrays starting at  given offsets.
	
	public static boolean readBoolean(byte b) {
        return b != 0;
    } 
	
	public static boolean readBoolean(byte[] b, int offset) {
        return b[offset] != 0;
    }
	
	public static short readByte(byte[] b) {
       return readByte(b,0);
    } 
	
	public static short readByte(byte[] b, int offset) {
        return (short) (b[offset] < 0 ? b[offset] + 256 : b[offset]);
    }

	public static char readChar(byte[] b) {
        return readChar(b,0);
    }
	
	public static char readChar(byte[] b, int offset) {
        return (char) ((b[offset + 1] & 0xFF) +(b[offset] << 8));
    }
	
	public static int readShort(byte[] b) {
        return readShort(b,0);
    }
	
	public static int readShort(byte[] b, int offset) {
        return ((b[offset + 1] & 0xFF) + (b[offset] << 8));
    }

	public static int readInt(byte[] b) {
		return readInt(b,0);
	}
	public static int readInt(byte[] b, int offset) {
        return ((b[offset + 3] & 0xFF)      ) +
               ((b[offset + 2] & 0xFF) <<  8) +
               ((b[offset + 1] & 0xFF) << 16) +
               ((b[offset    ]       ) << 24);
    }
	  /**
     * Returns the integer represented by up to 4 bytes in network byte order.
     * @param buf the buffer to read the bytes from
     * @param start
     * @param count
     * @return
     */
    public static int networkByteOrderToInt(byte[] buf, int start, int count) {
        if (count > 4) {
            throw new IllegalArgumentException("Cannot handle more than 4 bytes");
        }

        int result = 0;

        for (int i = 0; i < count; i++) {
            result <<= 8;
            result |= (buf[start + i] & 0xff);
        }

        return result;
    }
    
	public static float readFloat(byte[] b) {
		return readFloat(b,0);
	}
	public static float readFloat(byte[] b, int offset) {
        return Float.intBitsToFloat(readInt(b, offset));
    }

	public static long readLong(byte[] b) {
		return readLong(b,0);
	}
	public static long readLong(byte[] b, int offset) {
        return ((b[offset + 7] & 0xFFL)      ) +
               ((b[offset + 6] & 0xFFL) <<  8) +
               ((b[offset + 5] & 0xFFL) << 16) +
               ((b[offset + 4] & 0xFFL) << 24) +
               ((b[offset + 3] & 0xFFL) << 32) +
               ((b[offset + 2] & 0xFFL) << 40) +
               ((b[offset + 1] & 0xFFL) << 48) +
               (((long) b[offset])      << 56);
    }

	public static double readDouble(byte[] b) {
		return readDouble(b,0);
	}
	public static double readDouble(byte[] b, int offset) {
        return Double.longBitsToDouble(readLong(b, offset));
    }

	public static String readString(byte[] array,int offset, int length){
		return readString(array, offset, length, defaultEncode);
	}
	
	public static String readString(byte[] array,int offset, int length,Charset charset){
			if (array == null || array.length <(offset+length)) {
	            return EMPTY_STRING;
	        }
	        if(charset == null)
	        	charset = defaultEncode;
	        return new String(Arrays.copyOfRange(array, offset, offset+length),0,length,charset);
	}
	
    /*
     * Methods for packing primitive values into byte arrays starting at given offsets.
     */

	public static byte boolean2Byte(boolean val) {
        return (byte) (val ? 1 : 0);
    }
	
	public static void writeBoolean(byte[] b, boolean val) {
        b[0] = (byte) (val ? 1 : 0);
    }
	
	public static void writeBoolean(byte[] b, int offset, boolean val) {
        b[offset] = (byte) (val ? 1 : 0);
    }

	public static byte[] char2Bytes(char val) {
		byte[] b = new byte[2];
		writeChar(b,val);
		return b;
    }
	public static void writeChar(byte[] b,char val) {
		writeChar(b, 0, val);
    }
	
	public static void writeChar(byte[] b, int offset, char val) {
        b[offset + 1] = (byte) (val      );
        b[offset    ] = (byte) (val >>> 8);
    }

	public static byte[] short2Bytes(short val) {
		byte[] b = new byte[2];
		writeShort(b,val);
		return b;
    }
	
	public static void writeShort(byte[] b,short val) {
		 writeShort(b, 0, val);
	}
	
	public static void writeShort(byte[] b, int offset, short val) {
        b[offset + 1] = (byte) (val      );
        b[offset    ] = (byte) (val >>> 8);
    }
	
	public static byte[] int2Bytes(int val) {
		byte[] b = new byte[4];
		writeInt(b, 0, val);
		return b;
    }
	public static void writeInt(byte[] b,int val) {
		writeInt(b, 0, val);
	}
	public static void writeInt(byte[] b, int offset, int val) {
        b[offset + 3] = (byte) (val       );
        b[offset + 2] = (byte) (val >>>  8);
        b[offset + 1] = (byte) (val >>> 16);
        b[offset    ] = (byte) (val >>> 24);
    }

	public static byte[] float2Bytes(float val) {
		byte[] b = new byte[4];
		writeFloat(b, 0, val);
		return b;
    }
	
	public static void writeFloat(byte[] b,float val) {
		writeFloat(b, 0, val);
    }
	
	public static void writeFloat(byte[] b, int offset, float val) {
        writeInt(b, offset,  Float.floatToIntBits(val));
    }

	public static byte[] long2Bytes(long val) {
		byte[] b = new byte[8];
		writeLong(b, 0, val);
		return b;
    }
	public static void writeLong(byte[] b, long val) {
		writeLong(b, 0, val);
	}
	public static void writeLong(byte[] b, int offset, long val) {
        b[offset + 7] = (byte) (val       );
        b[offset + 6] = (byte) (val >>>  8);
        b[offset + 5] = (byte) (val >>> 16);
        b[offset + 4] = (byte) (val >>> 24);
        b[offset + 3] = (byte) (val >>> 32);
        b[offset + 2] = (byte) (val >>> 40);
        b[offset + 1] = (byte) (val >>> 48);
        b[offset    ] = (byte) (val >>> 56);
    }

	public static byte[] double2Bytes(double val) {
		byte[] b = new byte[8];
		writeDouble(b, 0, val);
		return b;
    }
	public static void writeDouble(byte[] b,double val) {
		writeDouble(b, 0,val);
	}
	public static void writeDouble(byte[] b, int offset, double val) {
        writeLong(b, offset, Double.doubleToLongBits(val));
    }
	
	/**
	 * <p>Add a String elements  into a given array. using a default CharSet（UTF-8），first write the String length(4 byte) then write String </p>
	 * @param array
	 * @param str
	 * @return The new byte[] array.
	 */
	public static void writeString(byte[] array,String str){
		writeString(array, str, defaultEncode);
	}
	/**
	 * <p>Add a String elements  into a given array. first write the String length(4 byte) then write String </p>
	 * @param array
	 * @param str
	 * @param charset
	 * @return The new byte[] array.
	 */
	public static void writeString(byte[] array,String str,Charset charset){
		if(str == null)
			return ;
		byte[] bytes = str.getBytes(charset);
		array = addBytes(array,int2Bytes(bytes.length));
		array = addBytes(array,bytes);
	}
	
	
	/**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * BitUtils.addBytes(array1, null)   = cloned copy of array1
     * BitUtils.addBytes(null, array2)   = cloned copy of array2
     * BitUtils.addBytes([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new byte[] array.
     */
	 public static byte[] addBytes(byte[] array1, byte[] array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}
	 /**
	     * Returns a hexadecimal representation of the given byte array.
	     * 
	     * @param bytes the array to output to an hex string
	     * @return the hex representation as a string
	     */
	    public static String asHex(byte[] bytes) {
	        return asHex(bytes, null);
	    }

	    /**
	     * Returns a hexadecimal representation of the given byte array.
	     * 
	     * @param bytes the array to output to an hex string
	     * @param separator the separator to use between each byte in the output
	     * string. If null no char is inserted between each byte value. 
	     * @return the hex representation as a string
	     */
	    public static String asHex(byte[] bytes, String separator) {
	    	if(bytes == null)
	    		return "";
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            String code = Integer.toHexString(bytes[i] & 0xFF);
	            if ((bytes[i] & 0xFF) < 16) {
	                sb.append('0');
	            }

	            sb.append(code);

	            if (separator != null && i < bytes.length - 1) {
	                sb.append(separator);
	            }
	        }

	        return sb.toString();
	    }

	    /**
	     * Converts a hex string representation to a byte array.
	     * 
	     * @param hex the string holding the hex values
	     * @return the resulting byte array
	     */
	    public static byte[] hexString2ByteArray(String hex) {
	        byte[] bts = new byte[hex.length() / 2];
	        for (int i = 0; i < bts.length; i++) {
	            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	        }

	        return bts;
	    }
	    
	    
		private static byte[] clone(byte[] array) {
			if (array == null) {
				return null;
			}
			return (byte[]) array.clone();
		}
}
