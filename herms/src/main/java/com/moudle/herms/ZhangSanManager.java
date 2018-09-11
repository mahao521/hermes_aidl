package com.moudle.herms;

import xiaofei.library.hermes.annotation.ClassId;

/**
 * Created by Administrator on 2018/8/17.
 */

@ClassId("com.moudle.myeventbus.ZhangSanManager")
public class ZhangSanManager {

    private Person mPerson;

    public Person getPerson() {
        return mPerson;
    }

    public void setPerson(Person person) {
        mPerson = person;
    }
}
