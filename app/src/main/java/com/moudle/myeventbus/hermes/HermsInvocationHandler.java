package com.moudle.myeventbus.hermes;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.moudle.myeventbus.Response;
import com.moudle.myeventbus.bean.ResponseBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2018/8/14.
 */

class HermsInvocationHandler implements InvocationHandler {

    private Class mClass;
    private Class hermeService;

    public <T> HermsInvocationHandler(Class<HermesService> hermesServiceClass, Class<T> clazz) {
        this.mClass = clazz;
        this.hermeService = hermesServiceClass;
    }

    private static final String TAG = "HermsInvocationHandler";

    private Gson mGson = new Gson();


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.d(TAG, "invoke: " + method.getName());
        Response response = Hermes.getDefault().sendObjectRequest(hermeService,mClass,method,args);
        if(!TextUtils.isEmpty(response.getData())){
            ResponseBean responseBean = mGson.fromJson(response.getData(),ResponseBean.class);
            if(responseBean.getData() != null){
                Object getUserResult = responseBean.getData();
                String data = mGson.toJson(getUserResult);
                Class<?> returnType = method.getReturnType();
                Object o = mGson.fromJson(data,returnType);
                Log.d(TAG, "invoke: " + getUserResult + " o : " + o);
                return o;
            }
        }
        return null;
    }
}
