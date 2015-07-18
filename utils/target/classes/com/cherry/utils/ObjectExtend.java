package com.cherry.utils;

import java.util.Map;

/**
 * 对象属性值合并
 * 
 * @description:
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 * 
 * @param <K>
 * @param <V>
 */
public final class ObjectExtend<K, V> {
	/**
	 * 对 <K, V> 对象的属性值进行合并，并返回目标对象<br>
	 * 注意：参数 objs 的第一个参数即为目标对象<br>
	 * <code>
	 * Map<String, Object> m1 = ...<br>
	 * Map<String, Object> m2 = ...<br>
	 * Map<String, Object> m3 = ObjectExtend.extend(m1, m2); //m1 为目标对象 <br>
	 * Map<String, Object> m4 = ObjectExtend.extend(new HashMap<String, Object>(), m1, m2); //new HashMap<String, Object>()为目标对象 <br>
	 * </code>
	 * Properties p1 = ...<br>
	 * Properties p2 = ...<br>
	 * Properties p3 = ...<br>
	 * Properties p4 = ObjectExtend.extend(p1, p2, p3);<br>
	 * 
	 * @param <K>
	 * @param <V>
	 * @param <T>
	 * @param deep
	 *            是否深度递归合并
	 * @param objs
	 *            要合并属性值的对象（objs的个数至少为2个，即：可以合并2...n个<K,V>到一个目标对象中）
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public static <K, V, T> T extend(boolean
	 * deep, Object... objs) { if ( objs == null ) { return null; }
	 * 
	 * if ( objs.length < 2 ) { return (T) objs[0]; }
	 * 
	 * // 合并后返回的目标对象 target=第一个对象 Object target = objs[0], options = null;
	 * Object src = null, copy = null;
	 * 
	 * int len = objs.length; int i = 1;
	 * 
	 * if ( !(target instanceof Map) ) { target = new HashMap<K, V>(); }
	 * 
	 * for ( ; i < len; i++ ) { options = objs[i];
	 * 
	 * if ( options != null && options instanceof Map ) { for ( Object key :
	 * ((Map<K, V>) options).keySet() ) { src = ((Map<K, V>) target).get(key);
	 * copy = ((Map<K, V>) options).get(key);
	 * 
	 * if ( target == copy || target.equals(copy) ) { continue; }
	 * 
	 * if ( deep && copy != null && copy instanceof Map ) { ((Map)
	 * target).put(key, extend(deep, src, copy)); } else { ((Map)
	 * target).put(key, copy); }
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * return (T) target; }
	 */

	@SuppressWarnings("unchecked")
	private static <K, V, T> T doExtend(Class<? extends T> clazz,
			boolean isDeep, Object... objs) {
		if (objs == null) 
			return null;

		if (objs.length < 2) 
			return (T) objs[0];

		// 合并后返回的目标对象 target
		Map<K, V> target = null;
		try {
			target = (Map<K, V>) clazz.newInstance();
		} catch (InstantiationException e) {
			LogUtils.error(e);
		} catch (IllegalAccessException e) {
			LogUtils.error(e);
		}

		Map<K, V> options = null;
		V src = null;
		V copy = null;

		int len = objs.length;
		int i = 0;

		for (; i < len; i++) {
			options = (Map<K, V>) objs[i];

			// if ( options != null && options instanceof Map )
			if (options != null) {
				// for ( K key : options.keySet() )
				for (Map.Entry<K, V> e : options.entrySet()) {
					src = target.get(e.getKey());
					copy = e.getValue();// options.get(key);

					if (target == copy || target.equals(copy)) 
						continue;

					if (isDeep && copy != null && copy instanceof Map) 
						target.put(e.getKey(), (V) doExtend(clazz, isDeep, src,copy));
					 else 
						target.put(e.getKey(), copy);
					

				}
			}

		}

		return (T) target;
	}

	/**
	 * 对 <code><K, V></code> 对象的属性值进行合并，并返回目标对象<br>
	 * 注意：该方法不进行深度合并，如需深度合并，请使用 ObjectExtend.extend( boolean deep, Object...
	 * objs ); 方法
	 * 
	 * @param <K>
	 * @param <V>
	 * @param <T>
	 * @param objs
	 *            要合并属性值的对象（objs的个数至少为2个，即：可以合并2...n个<K,V>到一个目标对象中）
	 * @return
	 */
	/*
	 * public static <K, V, T> T extend(Object... objs) { return extend(false,
	 * objs); }
	 */

	public static <K, V, T> T extend(Class<? extends T> clazz, Object... objs) {
		return doExtend(clazz, false, objs);
	}

	public static <K, V, T> T extend(Class<? extends T> clazz, boolean isDeep,Object... objs) {
		return doExtend(clazz, isDeep, objs);
	}

	public static void main(String[] args) {

		/*
		 * Properties m1_1 = new Properties(); m1_1.put("code", "");
		 * m1_1.put("state", "1002");
		 * 
		 * Map<String, Object> m1 = new HashMap<String, Object>();
		 * m1.put("name", "yswang"); m1.put("age", 25); m1.put("code", m1_1);
		 * 
		 * 
		 * HashMap<String, Object> m2_1 = new HashMap<String, Object>();
		 * m2_1.put("code", "x5080"); m2_1.put("state", "01111");
		 * 
		 * HashMap<String, Object> m2 = new HashMap<String, Object>();
		 * m2.put("name", "ss"); m2.put("age", 26); m2.put("address",
		 * "nanjingfenghuo"); m2.put("code", m2_1);
		 * 
		 * Map<String, Object> m = extend( HashMap.class, true, m1, m2); for (
		 * String key : m.keySet() ) { LogUtils.debug(key + " : " +
		 * m.get(key)); }
		 * LogUtils.debug("------------------------------------"); for (
		 * String key : m1.keySet() ) { LogUtils.debug(key + " : " +
		 * m1.get(key)); }
		 */
		/*
		 * Properties PROP = new Properties();
		 * PROP.setProperty("max-connection", "100");
		 * PROP.setProperty("min-connection", "10");
		 * PROP.setProperty("init-connection", "2");
		 * PROP.setProperty("connection-provider",
		 * "com.gdk.connection.proxool.ProxoolConnectionProvider");
		 * 
		 * Properties ds1 = new Properties(); ds1.setProperty("max-connection",
		 * "50"); ds1.setProperty("init-connection", "5");
		 * ds1.setProperty("connection-provider",
		 * "com.gdk.connection.c3p0.C3p0ConnectionProvider");
		 * 
		 * Properties ds2 = new Properties(); ds2.setProperty("load-on-startup",
		 * "true"); ds2.setProperty("show-sql", "show-sql");
		 * 
		 * Properties p = doExtend(Properties.class, false, PROP, ds1, ds2); for
		 * ( Enumeration e = p.propertyNames(); e.hasMoreElements(); ) { String
		 * key = e.nextElement().toString(); LogUtils.debug(key + " : " +
		 * p.getProperty(key)); }
		 * LogUtils.debug("------------------------------------"); for (
		 * Enumeration e = PROP.propertyNames(); e.hasMoreElements(); ) { String
		 * key = e.nextElement().toString(); LogUtils.debug(key + " : " +
		 * PROP.getProperty(key)); }
		 */

	}

}
