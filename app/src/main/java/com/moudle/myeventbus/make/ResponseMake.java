package com.moudle.myeventbus.make;

import android.util.Log;

import com.google.gson.Gson;
import com.moudle.myeventbus.Request;
import com.moudle.myeventbus.Response;
import com.moudle.myeventbus.bean.RequestBean;
import com.moudle.myeventbus.bean.Requestparams;
import com.moudle.myeventbus.bean.ResponseBean;
import com.moudle.myeventbus.utils.ObjectCenter;
import com.moudle.myeventbus.utils.TypeCenter;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/8/15.
 */

public abstract class ResponseMake {

    private static final String TAG = "ResponseMake";
    //Usermanager的class
    protected Class<?> resultClass;
    //getInstance() 参数数组
    protected Object[] mParameters;
    Gson mGson = new Gson();
    public TypeCenter mTypeCente = TypeCenter.getInstance();
    public static ObjectCenter mObject = ObjectCenter.getsInstance();

    protected abstract Object invokeMethod();
    protected abstract void setMethod(RequestBean requestBean);

    public Response makeResponse(Request request){
        RequestBean requestBean = mGson.fromJson(request.getData(),RequestBean.class);
        resultClass = mTypeCente.getClassType(requestBean.getResultClassName());
        //参数还原
        Requestparams[] requestparams = requestBean.getRequestparams();
        if(requestparams != null && requestparams.length > 0){
            mParameters = new Requestparams[requestparams.length];
            for(int i = 0; i < requestparams.length ; i++) {
                Requestparams requestparam = requestparams[i];
                Class<?> classType = mTypeCente.getClassType(requestparam.getParameterName());
                mParameters[i] = mGson.fromJson(requestparam.getParameterValue(),classType);
            }
        }else {
            mParameters = new Object[0];
        }

        Log.d(TAG, "makeResponse: " + Arrays.toString(mParameters));
        //重载，类似策略模式
        setMethod(requestBean);

        Object resultObject = invokeMethod();
        //---------------------------------------------- 将object----变成制定key的json
        ResponseBean responseBean = new ResponseBean(resultObject);
        //返回
        String data = mGson.toJson(responseBean);
        Response response = new Response(data);
        return response;
    }
}















