package com.moudle.myeventbus.utils;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.moudle.myeventbus.ClassId;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * Created by Administrator on 2018/8/14.
 */

public class TypeUtils {

    private static final String TAG = "TypeUtils";

    private static final HashSet<Class<?>> CONTEXT_CLASSES = new HashSet<Class<?>>(){
        {
            add(Context.class);
            add(Activity.class);
            add(AppCompatActivity.class);
            add(Application.class);
            add(FragmentActivity.class);
            add(IntentService.class);
            add(Service.class);
        }
    };

    public static String getClassId(Class<?> clazz){
        ClassId classId = clazz.getAnnotation(ClassId.class);
        if(classId != null){
            return classId.value();
        }else {
            return clazz.getName();
        }
    }

    /**
     *    getPerson(Int.class,Double.class)
     * @param method
     * @return
     */
    public static String getMethod(Method method){
        StringBuilder result = new StringBuilder(method.getName());
        result.append('(').append(getMethodParameters(method.getParameterTypes())).append(')');
        return  result.toString();
    }

    private static String getMethodParameters(Class<?>[] parameterTypes) {

        StringBuilder result = new StringBuilder();
        int length = parameterTypes.length;
        if(length == 0){
            return result.toString();
        }
        result.append(getClassName(parameterTypes[0]));
        for(int i = 1 ; i < parameterTypes.length; i++){
            result.append(',').append(getClassName(parameterTypes[i]));
        }
        return result.toString();
    }

    private static String getClassName(Class<?> parameterType) {
        if(parameterType == Boolean.class){
            return "boolean";
        }else if(parameterType == byte.class){
            return "byte";
        }else if(parameterType == CharSequence.class){
            return "char";
        }else if(parameterType == Short.class){
            return "short";
        }else if(parameterType == Integer.class){
            return "int";
        }else if(parameterType == Long.class){
            return "long";
        }else if(parameterType == Float.class){
            return "float";
        }else if(parameterType == Void.class){
            return "void";
        }else if(parameterType == Double.class){
            return "double";
        }else {
            return parameterType.getName();
        }
    }


    /**
     *
     * @param clazz  类名
     * @param methodName  方法
     * @param parameTypes  方法形参类型数组
     * @return
     */
    public static Method getMethodForGettingInstance(Class<?> clazz, String methodName, Class<?>[] parameTypes) {
        Method[] methods = clazz.getMethods();
        Method result = null;
        if(parameTypes == null){
            parameTypes = new Class[0];
        }
        for(Method method : methods){
            String tempName = method.getName();
            Log.d(TAG, "getMethodForGettingInstance: " + tempName);
            if(tempName.equals("getsInstance")){
                if(classAssignable(method.getParameterTypes(),parameTypes)){
                    result = method;
                    break;
                }
            }
        }
        if(result != null){
            return result;
        }
        return null;
    }


    public static boolean classAssignable(Class<?>[] classes1, Class<?>[] classes2) {
        if (classes1.length != classes2.length) {
            return false;
        }
        int length = classes2.length;
        for (int i = 0; i < length; ++i) {
            if (classes2[i] == null) {
                continue;
            }
            if (primitiveMatch(classes1[i], classes2[i])) {
                continue;
            }
            if (!classes1[i].isAssignableFrom(classes2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean primitiveMatch(Class<?> class1, Class<?> class2) {
        if (!class1.isPrimitive() && !class2.isPrimitive()) {
            return false;
        } else if (class1 == class2) {
            return true;
        } else if (class1.isPrimitive()) {
            return primitiveMatch(class2, class1);
            //class2 is primitive
            //boolean, byte, char, short, int, long, float, and double void
        } else if (class1 == Boolean.class && class2 == boolean.class) {
            return true;
        } else if (class1 == Byte.class && class2 == byte.class) {
            return true;
        } else if (class1 == Character.class && class2 == char.class) {
            return true;
        } else if (class1 == Short.class && class2 == short.class) {
            return true;
        } else if (class1 == Integer.class && class2 == int.class) {
            return true;
        } else if (class1 == Long.class && class2 == long.class) {
            return true;
        } else if (class1 == Float.class && class2 == float.class) {
            return true;
        } else if (class1 == Double.class && class2 == double.class) {
            return true;
        } else if (class1 == Void.class && class2 == void.class) {
            return true;
        } else {
            return false;
        }
    }

    public static Method getMethod(Class<? extends Object> aClass, String name, Class[] params) {

        Method result = null;
        Method[] methods = aClass.getMethods();
        for (Method method : methods){
            if (method.getName().equals(name) && classAssignable(method.getParameterTypes(),params)){
                if(result == null){
                    result = method;
                }
            }
        }
        return result;
    }
}
