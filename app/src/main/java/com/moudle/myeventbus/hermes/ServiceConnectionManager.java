package com.moudle.myeventbus.hermes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.moudle.myeventbus.EventBusService;
import com.moudle.myeventbus.Request;
import com.moudle.myeventbus.Response;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/8/14.
 */

class ServiceConnectionManager {

    private static final ServiceConnectionManager managerInstance = new ServiceConnectionManager();
    private HermesServiceConnection mConnection;

    private ServiceConnectionManager(){

    }
    public static ServiceConnectionManager getManagerInstance(){
        return  managerInstance;
    }
    //class binder对象
    private ConcurrentHashMap<Class<? extends HermesService>,EventBusService> mConcurrentService = new ConcurrentHashMap<>();
    //Class对应的链接对象
    private final ConcurrentHashMap<Class<? extends HermesService>,HermesServiceConnection> mHermsConntions = new ConcurrentHashMap<>();

    public void bind(Context context, String packageName, Class<? extends HermesService> service){
        mConnection = new HermesServiceConnection(service);
        mHermsConntions.put(service, mConnection);
        Intent intent;
        if(TextUtils.isEmpty(packageName)){
            intent = new Intent(context,service);
        }else {
            intent = new Intent();
            intent.setClassName(packageName,service.getName());
        }
        context.bindService(intent, mConnection,Context.BIND_AUTO_CREATE);
    }

    /**
     *    发送请求
     */
    public Response request(Class<HermesService> hermesServiceClass, Request request){
        EventBusService eventBusService = mConcurrentService.get(hermesServiceClass);
        if(eventBusService != null){
            try {
                Response response = eventBusService.send(request);
                return response;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void unBind(Context context) {
        context.unbindService(mConnection);
    }


    /**
     *   创建service链接，并且保存到mConcurrentService 中
     */
    class HermesServiceConnection implements ServiceConnection{

        private Class<? extends HermesService> mClass;

        HermesServiceConnection(Class<? extends HermesService> service){
            this.mClass = service;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            EventBusService busService = EventBusService.Stub.asInterface(service);
            mConcurrentService.put(mClass,busService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
          mConcurrentService.remove(mClass);
        }
    }
}
