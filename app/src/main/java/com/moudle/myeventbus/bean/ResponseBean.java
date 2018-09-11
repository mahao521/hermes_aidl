package com.moudle.myeventbus.bean;

/**
 * Created by Administrator on 2018/8/14.
 */

public class ResponseBean {

    private Object data;

    public ResponseBean(Object data){
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
