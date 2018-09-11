package com.moudle.myeventbus.make;

import android.util.Log;

import com.moudle.myeventbus.bean.RequestBean;
import com.moudle.myeventbus.bean.Requestparams;
import com.moudle.myeventbus.utils.TypeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/8/15.
 */

public class InstanceReponseMake extends ResponseMake {

    private static final String TAG = "InstanceReponseMake";
    private Method mMethod;

    @Override
    protected Object invokeMethod() {

        Object object = null;
        try {
            Log.d(TAG, "invokeMethod: 11111111 ");
            object = mMethod.invoke(null,mParameters);
            mObject.putObject(object.getClass().getName(),object);
            Log.d(TAG, "invokeMethod: " + object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void setMethod(RequestBean requestBean) {

        Log.d(TAG, "setMethod: ");
        //解析参数，寻找getinstance() --UserManager
        Requestparams[] requestparams = requestBean.getRequestparams();
        Class<?>[] parameTypes = null;
        if(requestparams != null && requestparams.length > 0){
            parameTypes = new Class<?>[requestparams.length];
            for (int i = 0; i < requestparams.length ; i++){
               parameTypes[i] = mTypeCente.getClassType(requestparams[i].getParameterName());
            }
        }
        String methodName = requestBean.getMethodName(); //可能出现重载
        //這個methodName为null,因为单例的时候没有传method; -----------------只能反射类中的所有方法获取单例;
        Method method = TypeUtils.getMethodForGettingInstance(resultClass,methodName,parameTypes);
        Log.d(TAG, "setMethod: " + method.getName() + "..." + methodName);
        mMethod = method;
    }
}
