package com.cherry.utils.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
  @description:序列化相关工具类
  @version:0.1
  @author:Cherry
  @date:2013-6-22
 */
public class JavaSerializerUtils {
    private JavaSerializerUtils() {
        super();
    }

    /**
     * 克隆对象
     * @param <T> 待克隆的对象
     * @param object 克隆成功后的对象
     * @return
     */
    public static <T extends Serializable> T clone(T object) {
        if (object == null) {
            return null;
        }
        byte[] objectData = serialize(object);
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);

        ClassLoaderAwareObjectInputStream in = null;
        try {
            // stream closed in the finally
            in = new ClassLoaderAwareObjectInputStream(bais, object.getClass().getClassLoader());
            /*
             * when we serialize and deserialize an object,
             * it is reasonable to assume the deserialized object
             * is of the same type as the original serialized object
             */
            @SuppressWarnings("unchecked") // see above
            T readObject = (T) in.readObject();
            return readObject;

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("ClassNotFoundException while reading cloned object data", ex);
        } catch (IOException ex) {
            throw new RuntimeException("IOException while reading cloned object data", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException("IOException on closing cloned object data InputStream.", ex);
            }
        }
    }

    /**
     * 对象序列化，默认是不压缩的
     * @param obj
     * @param outputStream
     */
    public static void serialize(Serializable obj, OutputStream outputStream) {
    	serialize(obj,outputStream,false);
    }
   
    /**
     * 对象序列化,选择是够压缩序列化
     * @param obj  待序列化的对象
     * @param outputStream
     * @param zip 是否压缩,如果压缩了，则反序列化回来的时候还需要解压缩
     */
    public static void serialize(Serializable obj, OutputStream outputStream,boolean zip) {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        
        ObjectOutputStream out = null;
        try {
        	if (zip)
        		outputStream = new GZIPOutputStream(outputStream);
            // stream closed in the finally
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) { // NOPMD
                // ignore close exception
            }
        }
    }

    
    /**
     * 序列化对象，默认不压缩序列化
     * @param obj 待序列化的累
     * @return
     */
    public static byte[] serialize(Serializable obj) {
    	return serialize(obj,false);
    }
   
    /**
     * 序列化对象，可选择是否需要压缩序列化(注意：压缩序列化会比不压缩序列化慢，但是占有的空间会小一些)
     * @param obj 待序列化的累
     * @param zip  true:压缩，false:不压缩
     * @return
     */
    public static byte[] serialize(Serializable obj, boolean zip) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(obj, baos,zip);
        return baos.toByteArray();
    }

    
    /**
     * 反序列化
     * @param inputStream
     * @return
     */
    public static Object deserialize(InputStream inputStream) {
    	return deserialize(inputStream,false);
    }
   
    /**
     * 反序列化输入流
     * @param inputStream
     * @param zipped 是否解压缩
     * @return
     */
    public static Object deserialize(InputStream inputStream,boolean zipped) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream in = null;
        try {
        	if(zipped)
        		inputStream =  new GZIPInputStream(inputStream);
            // stream closed in the finally
            in = new ObjectInputStream(inputStream);
            return in.readObject();

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) { // NOPMD
                // ignore close exception
            }
        }
    }

    /**
     * 反序列化得到对象
     * @param objectData
     * @return
     */
    public static Object deserialize(byte[] objectData) {
    	return deserialize(objectData,false);
    }
   
    /**
     * 反序列化得到对象，可选择是否解压缩(注意：如果对象序列化时压缩了，那么反序列化时也必须解压缩)
     * @param objectData
     * @param zippd
     * @return
     */
    public static Object deserialize(byte[] objectData,boolean zippd) {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        return deserialize(bais,zippd);
    }

    
     static class ClassLoaderAwareObjectInputStream extends ObjectInputStream {
        private ClassLoader classLoader;

        public ClassLoaderAwareObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
            super(in);
            this.classLoader = classLoader;
        }

       
        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            String name = desc.getName();
            try {
                return Class.forName(name, false, classLoader);
            } catch (ClassNotFoundException ex) {
                return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
            }
        }

    }
	
	
}
