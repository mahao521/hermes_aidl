package com.moudle.herms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xiaofei.library.hermes.Hermes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hermes.connectApp(getApplicationContext(),"com.moudle.myeventbus");
    }
}
