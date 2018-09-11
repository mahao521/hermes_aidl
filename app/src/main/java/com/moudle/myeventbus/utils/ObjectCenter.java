package com.moudle.myeventbus.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/8/15.
 */

public class ObjectCenter {

    private static final String TAG = "ObjectCenter";

    private static volatile ObjectCenter sInstance = null;

    private  ConcurrentHashMap<String,Object> mObjects;

    private ObjectCenter(){
        mObjects = new ConcurrentHashMap<>();
    }

    public static ObjectCenter getsInstance(){
        if(sInstance == null){
            synchronized (ObjectCenter.class){
                if(sInstance == null){
                    sInstance = new ObjectCenter();
                }
            }
        }
        return sInstance;
    }

    public Object getObject(String name){
       return mObjects.get(name);
    }

    public void putObject(String name,Object object){
        mObjects.put(name,object);
    }
}
