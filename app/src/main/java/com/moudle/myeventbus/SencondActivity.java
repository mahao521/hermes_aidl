package com.moudle.myeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.moudle.myeventbus.hermes.Hermes;
import com.moudle.myeventbus.hermes.HermesService;

public class SencondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SencondActivity";
    IMahaoManager mIMahaoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencond);
        //1 ： 建立连接，bind绑定服务
        Hermes.getDefault().connect(this, HermesService.class);

        findViewById(R.id.btn_client).setOnClickListener(this);
        findViewById(R.id.btn_send_server).setOnClickListener(this);
        findViewById(R.id.btn_receive_server).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_receive_server:
                //2 ： 通过aidl 发送请求，返回respnse  在Service中处理
                mIMahaoManager = Hermes.getDefault().getInstance(IMahaoManager.class);
           //     Log.d(TAG, "onClick: " + mIMahaoManager.getPerson());
                break;
            case R.id.btn_send_server:

                break;
            case R.id.btn_client:

                break;

            case 0:
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        DLNALauncherPlayerSender.getInstance().startDLNALauncherVideoPlay(url,
                                urltitle, mContext);

                        msg.obj = "接收到DLNA视频推送";
                        mHandler.sendMessage(msg);
                    }
                }, delaytime);
                break;
            default :
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        msg.obj = "DLNA推送失败";
                        mHandler.sendMessage(msg);
                    }
                }, delaytime);
                break;
        }

        LogUtils.d(TAG + "   " + "setPlayUri end~~");
        return 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hermes.getDefault().unConnect(this);
    }
}
