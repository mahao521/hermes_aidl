package com.moudle.myeventbus;

import com.moudle.myeventbus.bean.Person;

/**
 * Created by Administrator on 2018/8/15.
 */

@ClassId("com.moudle.myeventbus.MahaoManager")
public interface IMahaoManager {

     Person makePerson();

     Person getPerson();

     void setPerson(Person person);

}
