package com.sp.lang;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ReflectUtil {
	
	public static Object callGetMethod(Object obj,String fieldName) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        Method get = pd.getReadMethod();
        return get.invoke(obj);
    }
	
	public static Object callStaticMethod(String className, String methodName) throws Exception {
		Class<?> clazz = Class.forName(className);
		Method method = clazz.getMethod(methodName);
		return method.invoke(null);
	}
	
	public static Object callStaticMethod(String className, String methodName, Object...parameterValues) throws Exception {
		Class<?> clazz = Class.forName(className);
		
		List<Method> sameNameMethods = new ArrayList<>();
		
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String mn = method.getName();
            if(mn.equals(methodName)) {
            	sameNameMethods.add(method);
            }
        }
        
        if(sameNameMethods.size()==0) {
        	throw new InterpretException("NotFound "+methodName+" in " + className);
        } else if(sameNameMethods.size()==1) {
        	Method method = sameNameMethods.get(0);
        	return method.invoke(null, parameterValues);
        }
        
        for(Method method : sameNameMethods) {
        	if(checkMethodAppropriateParam(method, parameterValues)) {
        		return method.invoke(null, parameterValues);
        	}
        }
        
        throw new InterpretException("NotFound appropriate "+methodName+" in " + 
        		className + ", "+parameterValues );
	}
	
	public static boolean checkMethodAppropriateParam(Method method, Object...parameterValues) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		
		if(parameterTypes.length != parameterValues.length) {
			return false;
		}
		
        for (int i=0; i<parameterTypes.length; i++) {
        	Class<?> clazz = parameterTypes[i];
        	Object obj = parameterValues[i];
        	if(obj==null){
        		continue;
        	}
        	
          	if(!clazz.isInstance(obj)){
          		return false;
          	}
        }
        
        return true;
	}
	
	public static Object newInstance(String className) throws Exception{
		Class<?> clazz = Class.forName(className);
		return clazz.newInstance();
	}
	
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object...parameterValues) throws Exception{
		Class<?> clazz = Class.forName(className);
		Constructor<?> c=clazz.getConstructor(parameterTypes);
	    return c.newInstance(parameterValues);
	}
}
