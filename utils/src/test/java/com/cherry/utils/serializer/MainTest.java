package com.cherry.utils.serializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cherry.utils.threadPool.ThreadExector;
import com.cherry.utils.threadPool.ThreadPoolFactory;

public class MainTest {
	static List<Callable<Long>> tasks1 = new ArrayList<Callable<Long>>();
	static{
		MessageBean bean = new MessageBean();
		bean.setBigDecimalField(new BigDecimal(5));
		bean.setBigIntegerField(new BigInteger("2"));
		bean.setIntField2(new Integer(3));
		bean.setStrField("张三");
		
		for (int i = 0; i < 1500; i++) {
			tasks1.add(new KryoThread(bean));
		}
	}
	
	public static void main(String[] args) {
		
		MessageBean bean = new MessageBean();
		bean.setBigDecimalField(new BigDecimal(5));
		bean.setBigIntegerField(new BigInteger("2"));
		bean.setIntField2(new Integer(3));
		bean.setStrField("张三");
		
		byte[] serialize = JavaSerializerUtils.serialize(bean);
		System.out.println(serialize.length);
		Object deserialize = JavaSerializerUtils.deserialize(serialize);
		System.out.println(deserialize);
		
		byte[] serialize2 = KryoSerializerUtils.serialize(bean);
		System.out.println(serialize2.length);
		Object deserialize2 = KryoSerializerUtils.deserialize(serialize2);
		System.out.println(deserialize2);
		
		byte[] serialize3 = HessionSerializerUtils.serialize(bean);
		System.out.println(serialize3.length);
		Object deserialize3 = HessionSerializerUtils.deserialize(serialize3);
		System.out.println(deserialize3);
		
		
		byte[] serialize4 = JavaSerializerUtils.serialize(bean,true);
		System.out.println(serialize4.length);
		Object deserialize4 = JavaSerializerUtils.deserialize(serialize4,true);
		System.out.println(deserialize4);

		ThreadExector callableThreadExector = ThreadPoolFactory.getCallableThreadExector(2);
		List<Long> result = callableThreadExector.excuteTasksAndWaitForResult(tasks1);
		printResult(result, "tasks1 ");
		
		 callableThreadExector = ThreadPoolFactory.getCallableThreadExector(2);
		 result = callableThreadExector.excuteTasksAndWaitForResult(tasks1);
		printResult(result, "tasks2 ");
	}

	
	private static void printResult(List<Long> result,String desc){
		StringBuilder sb = new StringBuilder();
		sb.append("统计").append(desc).append("\t");
		long totalTime = 0;
		for (int i = 0; i < result.size(); i++) {
			totalTime += result.get(i);
		}
		sb.append("总个数:").append(result.size()).append("\t").append("总耗时:").append(totalTime/1000).append("ms").append("\t");
		sb.append("平均每个线程耗时:").append(totalTime/1000/result.size()).append("ms").append("\t");
		sb.append("平均每次序列化反序列化耗时:").append(totalTime/50/result.size()).append("us").append("\t");
		System.out.println(sb.toString());
	}
}
