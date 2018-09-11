package com.moudle.myeventbus;

import android.os.UserManager;

import com.moudle.myeventbus.bean.Person;

/**
 * Created by Administrator on 2018/8/15.
 */

public class MahaoManager implements IMahaoManager {

    Person mPerson;
    private static MahaoManager sInstance = null;
    private MahaoManager(){
    }

    public static synchronized MahaoManager getsInstance(){
        if(sInstance == null){
            sInstance = new MahaoManager();
        }
        return  sInstance;
    }

    @Override
    public Person getPerson() {
        return mPerson;
    }

    @Override
    public Person makePerson() {
        return mPerson;
    }

    @Override
    public void setPerson(Person person) {
         this.mPerson = person;
    }
}
