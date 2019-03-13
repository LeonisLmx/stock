package com.app.stock.common;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.*;

import static io.netty.util.internal.StringUtil.byteToHexString;

/**
 * Created by lmx
 * Date 2018/12/24
 */
public class CommonUtil {

    public static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for(int i=0;i<b.length;i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static StringBuilder sortMap(Map<String,Object> map){
        if(map == null || map.isEmpty()){
            return null;
        }
        Map<String,Object> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entity:sortMap.entrySet()){
            sb.append(entity.getKey()).append(entity.getValue());
        }
        return sb;
    }
}
