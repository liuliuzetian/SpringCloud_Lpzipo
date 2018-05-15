package com.lpzipo.common.util;

import java.io.*;

/**
 *
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 *
 * Java版的 Serialize
 *
 * <p>
 *
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年6月2日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 *
 */
public class SerializeUtil {

    /**
     * 将对象序列化成byte数组
     * @param object
     * @return
     */
    public static byte[] serialize(Object object){
        if(object == null){
            throw new NullPointerException("Can't serialize null");
        }
        byte[] res = null;
        ObjectOutputStream os = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(object);
            res = bos.toByteArray();
        } catch (IOException e) {
            LoggerUtil.error("Serialize error：",e);
        }finally {
            close(os);
            close(bos);
        }
        return res;
    }

    /**
     * 反序列化成对象
     * @param in
     * @return
     */
    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    public static <T> T deserialize(byte[] in, Class<T>...requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            LoggerUtil.error("deserialize error",e);
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                LoggerUtil.error("close stream error",e);
            }
    }

}
