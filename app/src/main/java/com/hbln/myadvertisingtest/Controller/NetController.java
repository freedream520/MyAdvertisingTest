package com.hbln.myadvertisingtest.Controller;

import java.util.HashMap;

/**
 * 网络请求的接口
 * Created by lwc on 2015/12/26.
 */
public interface NetController {
    void baseRequest(String url, HashMap<String,String> params,iCallbackResult callback);
   void travelInfo(iCallbackResult callback);
    void getDataUpdata(iCallbackResult callback);
    void getUserList(iCallbackResult callback);

}

