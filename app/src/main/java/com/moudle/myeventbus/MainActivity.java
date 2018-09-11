package com.moudle.myeventbus;

import android.content.Intent;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.moudle.myeventbus.bean.Person;
import com.moudle.myeventbus.hermes.Hermes;

/**
 *   herms使用：
 *
 *   A线程mahaoManager   B线程 想要拿到这个对象
 *
 *   B线程： register() ； init() ;  清单文件设置service  注意android系统服务，高版本必须同时设置action和package；
 *
 *   B线程：
 *   1  建立一个IManager接口
 *   2  创建和A线程相同的 mahaoManager实现该接口；并且类名classId注解
 *   3 ： application connect A中的服务；
 *   4 ： 获取单例，获取对象，调用方法，--调用反射invoke()；
 *
 *   实现原理 ： 通过aidl 传递requst对象给服务端， 服务端依据request传递类型，创建对象返回给客户端。客户端使用该类，该对象的方法。
 *
 *    1 ： 获取对象；
 *
 *    2 ： 动态代理该对象中的方法；
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hermes.getDefault().init(this);
        Hermes.getDefault().register(MahaoManager.class);

        findViewById(R.id.btn_send_client).setOnClickListener(this);
        findViewById(R.id.btn_receive_server).setOnClickListener(this);
        findViewById(R.id.btn_server).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_send_client:
                MahaoManager.getsInstance().setPerson(new Person("mahao","男"));
                break;
            case R.id.btn_receive_server:

                break;
            case R.id.btn_server:
                Intent intent = new Intent(this,SencondActivity.class);
                startActivity(intent);
                break;
        }
    }
}
