package com.xindong.api.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class NullObjectHandle {
	
	
 static Integer i =0;
	
	private static NullObjectHandle instance = null;
	
	private NullObjectHandle(){};
	
	public static NullObjectHandle getInstance(){
		if( null == instance  ){
			synchronized(i){
				if( null == instance ){
					instance = new NullObjectHandle();
				}
			}
		}
		return instance;
	}
	
	public static Object setObjectNotNull(Object o) {
		for (Class<?> classType = o.getClass(); classType != Object.class; classType = classType
				.getSuperclass()) {
			try {
				// 获得对象的所有属性
				Field fields[] = classType.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					try {
						Field field = fields[i];
						// 设置些属性是可以访问的
						field.setAccessible(true);
						// 属性名称
						String fieldName = field.getName();
						if( fieldName.startsWith("serialVersionUID") || fieldName.endsWith("serialVersionUID") ){
							continue;
						}
						// 得到属性名称的第一个字母并转成大小
						String firstLetter = fieldName.substring(0, 1)
								.toUpperCase();
						// 获得和属性对应的getXXX()方法的名字：get+属性名称的第一个字母并转成大小+属性名去掉第一个字母，
						// 如属性名称为name，则：get+N+ame
						String getMethodName = "get" + firstLetter
								+ fieldName.substring(1);
						// 获得和属性对应的setXXX()方法的名字
						String setMethodName = "set" + firstLetter
								+ fieldName.substring(1);
						// 获得和属性对应的getXXX()方法
						String type = field.getType().toString();// 得到此属性的类型
						Method getMethod = classType.getMethod(getMethodName,
								new Class[] {});
						// 获得和属性对应的setXXX()方法，传入参数为参数的类型
						Method setMethod = classType.getMethod(setMethodName,
								new Class[] { field.getType() });
						// 调用原对象的getXXX()方法
						Object value = getMethod.invoke(o, new Object[] {});
						if (null == value) {
							Object val = field.get(o);// 得到此属性的值
							if (type.endsWith("String")) {
								field.set(o, ""); // 给属性设值
							} else if (type.endsWith("double")
									|| type.endsWith("Double")) {
								field.set(o, 0); // 给属性设值
							} else if (type.endsWith("long")
									|| type.endsWith("Long")) {
								field.set(o, 0); // 给属性设值
							} else if (type.endsWith("int")
									|| type.endsWith("Integer")) {
								field.set(o, 0); // 给属性设值
							} 
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return o;
	}

}
