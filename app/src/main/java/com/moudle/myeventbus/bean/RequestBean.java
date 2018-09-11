package com.moudle.myeventbus.bean;

/**
 * Created by Administrator on 2018/8/14.
 */

public class RequestBean {

    private String className;
    private String resultClassName;
    private String resultObject;
    private String methodName;
    //参数
    private Requestparams[] mRequestparams;

    public Requestparams[] getRequestparams() {
        return mRequestparams;
    }

    public void setRequestparams(Requestparams[] requestparams) {
        mRequestparams = requestparams;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResultClassName() {
        return resultClassName;
    }

    public void setResultClassName(String resultClassName) {
        this.resultClassName = resultClassName;
    }

    public String getResultObject() {
        return resultObject;
    }

    public void setResultObject(String resultObject) {
        this.resultObject = resultObject;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
