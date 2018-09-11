package com.moudle.myeventbus.utils;

import android.text.TextUtils;
import android.util.Log;

import com.moudle.myeventbus.bean.RequestBean;
import com.moudle.myeventbus.bean.Requestparams;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/8/14.
 */

public class TypeCenter {

    private static final String TAG = "TypeCenter";
    private static final TypeCenter outInstance = new TypeCenter();

    public static TypeCenter getInstance(){
        return outInstance;
    }

    private ConcurrentHashMap<String,Class<?>> mAnnotateClass;
    private ConcurrentHashMap<Class<?>,ConcurrentHashMap<String,Method>> mRawMethods;

    private TypeCenter(){
        mAnnotateClass = new ConcurrentHashMap<>();
        mRawMethods = new ConcurrentHashMap<>();
    }

    public void register(Class<?> clazz){
        //分为注册类，注册方法
        registerClass(clazz);
        registerMethod(clazz);
    }

    //注解类中方法的填充
    private void registerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            mRawMethods.putIfAbsent(clazz,new ConcurrentHashMap<String, Method>());
            //map都不会为空
            ConcurrentHashMap<String,Method> map = mRawMethods.get(clazz);
            String key = TypeUtils.getMethod(method);
            Log.i(TAG, "registerMethod: " + key + "----" + method);
            map.put(key,method);
        }
    }

    //AnnotatedClass 填充
    private void registerClass(Class<?> clazz) {
        String className = clazz.getName();
        mAnnotateClass.putIfAbsent(className,clazz);
    }

    public Class<?> getClassType(String name){
        if(TextUtils.isEmpty(name)){
            return  null;
        }
        Class<?> clazz = mAnnotateClass.get(name);
        if(clazz == null){
            try {
                clazz = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    /**
     *    通过请求requestbean 和 class 获取请求方法。
     *
     *     匹配method
     * @param aClass
     * @param requestBean
     * @return
     */
    public Method getMethod(Class<? extends Object> aClass, RequestBean requestBean) {

        String name = requestBean.getMethodName();   //getPerson()
        if(name != null){
            Log.d(TAG, "getMethod: " + name);

            //?  这里put 一个新的数组不会把之前的覆盖吗？ ---- 特性： 已经有了，就不添加。所以不会覆盖。
            mRawMethods.putIfAbsent(aClass,new ConcurrentHashMap<String, Method>());
            ConcurrentHashMap<String, Method> currentHashMap = mRawMethods.get(aClass);
            Method method = currentHashMap.get(name);
            if(method != null){
                Log.d(TAG, "getMethod: " + method.getName());
                return method;
            }
            int pos = name.indexOf('(');
            Class[] params = null;
            Requestparams[] requestparams = requestBean.getRequestparams();
            if(requestparams != null && requestparams.length > 0){
                for (int i = 0; i < requestparams.length; i++) {
                    params[i] = getClassType(requestparams[i].getParameterName());
                }
            }
            //感觉没必要 ---  直接吧requestmethod放在method中传递过来;  --------减少一次反射；
            method = TypeUtils.getMethod(aClass,name.substring(0,pos),params);
            currentHashMap.put(name,method);
            return method;
        }
        return null;
    }
}
