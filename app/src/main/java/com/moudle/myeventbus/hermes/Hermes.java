package com.moudle.myeventbus.hermes;

import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;

import com.google.gson.Gson;
import com.moudle.myeventbus.ClassId;
import com.moudle.myeventbus.Request;
import com.moudle.myeventbus.Response;
import com.moudle.myeventbus.bean.RequestBean;
import com.moudle.myeventbus.bean.Requestparams;
import com.moudle.myeventbus.utils.TypeCenter;
import com.moudle.myeventbus.utils.TypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Hermes {

    private static final String TAG = "Hermes";
    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_GET = 1;
    public static final Hermes ourInstance = new Hermes();
    private TypeCenter mTypeCenter;
    private ServiceConnectionManager mServiceConnectionManager;

    Gson mGson = new Gson();
    private Context mContext;
    private Hermes(){
        mServiceConnectionManager = ServiceConnectionManager.getManagerInstance();
        mTypeCenter = TypeCenter.getInstance();
    }

    public static Hermes getDefault(){
        return ourInstance;
    }

    //A进程
    public void register(Class<?> clazz){
        mTypeCenter.register(clazz);
    }

    //B进程中执行
    public void connect(Context context,Class<? extends HermesService> service){
        connectApp(context,null,service);
    }

    public void unConnect(Context context){
        mServiceConnectionManager.unBind(context);
    }

    public void init(Context context){
        mContext = context;
    }

    public void connectApp(Context context,String packages,Class<? extends HermesService> service){
        init(context);
        mServiceConnectionManager.bind(context,packages,service);
    }


    //防止方法重载
    public<T> T getInstance(Class<T> clazz,Object... parameters){
        //为了创建类的对象
         Response response = sendRequest(HermesService.class,clazz,null,parameters);
         //从类的对象---调用类中的其他方法
        return getProxy(HermesService.class,clazz);
    }


    /**
     *   获取单例 对象；
     * @param hermesServiceClass
     * @param clazz
     * @param o
     * @param parameters
     * @param <T>
     * @return
     */
    private <T> Response sendRequest(Class<HermesService> hermesServiceClass, Class<T> clazz, Method method, Object[] parameters) {

        RequestBean requestBean = new RequestBean();
        String className = null;
        if(clazz.getAnnotation(ClassId.class) == null){
            requestBean.setClassName(clazz.getName());
            requestBean.setResultClassName(clazz.getName());
        }else {
            requestBean.setClassName(clazz.getAnnotation(ClassId.class).value());
            requestBean.setResultClassName(clazz.getAnnotation(ClassId.class).value());
        }
        if(method != null){
            requestBean.setMethodName(TypeUtils.getMethod(method));
        }

        Requestparams[] requestParameters=null;
        if (parameters != null && parameters.length > 0) {
            requestParameters=new Requestparams[parameters.length];
            for (int i=0;i<parameters.length ;i++) {
                Object parameter=parameters[i];
                String parameterClassName = parameter.getClass().getName();
                String parameterValue = mGson.toJson(parameter);
                Log.d(TAG, "sendRequest: "+ parameterClassName + "...." + parameterValue);

                Requestparams requestParameter = new Requestparams(parameterClassName, parameterValue);
                requestParameters[i]=requestParameter;
            }
        }

        if (requestParameters != null) {
            requestBean.setRequestparams(requestParameters);
        }

//        请求获取单例 ----》对象 ----------》调用对象的方法
        Request request = new Request(mGson.toJson(requestBean), TYPE_GET);
        return mServiceConnectionManager.request(hermesServiceClass,request);

    }

    /**
     *    动态代理每一个方法；
     * @param hermesServiceClass     service
     * @param clazz                   注解的class
     * @param <T>
     * @return
     */
    private <T> T getProxy(Class<HermesService> hermesServiceClass, Class<T> clazz) {
        ClassLoader classLoader = hermesServiceClass.getClassLoader();
        //java代理和代理模式，都是围绕被代理对象的方法（接口)展开的。 代理对象可以不清晰，代理方法（接口）很容易确定。
        //clazz 是代理对象。。。
        //代理模式中： 代理人和被代理对象都必须实现接口；
        //代理的方法，只有被调用，才会走invoke方法-----invoke可以做自己的处理。
        T proxy = (T) Proxy.newProxyInstance(classLoader,new Class<?>[]{clazz},new HermsInvocationHandler(hermesServiceClass,clazz));
        return proxy;
    }

    /**
     *   动态获取每一个方法返回
     * @param hermeService
     * @param aClass
     * @param method
     * @param args
     * @return
     */
    public Response sendObjectRequest(Class hermeService, Class aClass, Method method, Object[] args) {

        RequestBean requestBean = new RequestBean();
        if(aClass.getAnnotation(ClassId.class) == null){
            requestBean.setClassName(aClass.getName());
            requestBean.setResultClassName(aClass.getName());
        }else{
            ClassId annotation = (ClassId) aClass.getAnnotation(ClassId.class);
            requestBean.setClassName(annotation.value());
            requestBean.setResultClassName(annotation.value());
        }

        /**
         *   获取单例对象传递的null
         *
         *   代理 传的有值  就拼接
         */
        if(method != null){
            requestBean.setMethodName(TypeUtils.getMethod(method));
        }

        Requestparams[] requestparams = null;
        if(args != null){
            requestparams = new Requestparams[args.length];
            for(int i = 0; i < args.length; i++){
                Object arg = args[i];
                String objName = arg.getClass().getName();
                String objvalue = mGson.toJson(arg);
                Requestparams params = new Requestparams(objName,objvalue);
                requestparams[i] = params;
            }
        }

        if(requestparams != null){
            requestBean.setRequestparams(requestparams);
        }

        //获取单例----获取对象---调用方法
        Request request = new Request(mGson.toJson(requestBean).toString(),TYPE_NEW);
        return mServiceConnectionManager.request(hermeService,request);
    }
}















