package com.moudle.myeventbus.make;

import android.util.Log;

import com.moudle.myeventbus.bean.RequestBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/8/15.
 */

public class ObjectResponseMake extends ResponseMake{

    private static final String TAG = "ObjectResponseMake";
    private Method mMethod;
    private Object object;

    @Override
    protected Object invokeMethod() {

        Log.d(TAG, "invokeMethod: object" + object  +".." +  Arrays.toString(mParameters));
        Exception exception;
        try {
            return mMethod.invoke(object,mParameters);
        } catch (IllegalAccessException e) {
            exception = e;
        } catch (InvocationTargetException e) {
            exception = e;
        }
        throw new RuntimeException("invoke Method");
    }

    @Override
    protected void setMethod(RequestBean requestBean) {

        //这个对象是调用单例创建的 ---------------- 如果不存在，可以利用反射，newnstance创建；
        object = mObject.getObject(resultClass.getName());
        Log.d(TAG, "setMethod: " + object);
        Method method = mTypeCente.getMethod(object.getClass(),requestBean);
        Log.d(TAG, "setMethod: " + method.getName());
        mMethod = method;
    }
}
