package com.moudle.herms;

import xiaofei.library.hermes.annotation.ClassId;

/**
 * Created by Administrator on 2018/8/17.
 */

@ClassId("com.moudle.myeventbus.ZhangSanManager")
public interface IZhangSanManager {

    public Person getPerson();

    public void setPerson(Person person) ;
}
