package com.moudle.myeventbus.hermes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.moudle.myeventbus.EventBusService;
import com.moudle.myeventbus.Request;
import com.moudle.myeventbus.Response;
import com.moudle.myeventbus.make.InstanceReponseMake;
import com.moudle.myeventbus.make.ObjectResponseMake;
import com.moudle.myeventbus.make.ResponseMake;

/**
 * Created by Administrator on 2018/8/14.
 */

public class HermesService extends Service {

    private static final String TAG = "HermesService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private EventBusService.Stub mBinder = new EventBusService.Stub() {
        @Override
        public Response send(Request request) throws RemoteException {

            Log.d(TAG, "send:  service");
            //对请求参数进行处理，生成reponse返回
            ResponseMake responseMake = null;
            switch (request.getType()){
                case Hermes.TYPE_GET: //获取单例
                    responseMake = new InstanceReponseMake();
                    break;
                case Hermes.TYPE_NEW: //获取对象
                    responseMake = new ObjectResponseMake();
                    break;
            }
            return responseMake.makeResponse(request);
        }
    };

    public static class HermsService0 extends HermesService{}

    public static class HermsService1 extends HermesService{}

    public static class HermsService2 extends HermesService{}

    public static class HermsService3 extends HermesService{}

    public static class HermsService4 extends HermesService{}

    public static class HermsService5 extends HermesService{}

    public static class HermsService6 extends HermesService{}

}










