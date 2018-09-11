// EventBusService.aidl
package com.moudle.myeventbus;

import  com.moudle.myeventbus.Request;
import  com.moudle.myeventbus.Response;

interface EventBusService {
   Response send(in Request request);
}
