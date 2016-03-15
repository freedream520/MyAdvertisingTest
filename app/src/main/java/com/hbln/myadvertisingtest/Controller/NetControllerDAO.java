package com.hbln.myadvertisingtest.Controller;

import com.hbln.myadvertisingtest.Config.NetConstants;
import com.hbln.myadvertisingtest.Config.ShareConstants;
import com.hbln.myadvertisingtest.FrameWork.UtilFactory;
import com.hbln.myadvertisingtest.Tool.Logs;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求的实现
 * Created by lwc on 2015/12/26.
 */
public class NetControllerDAO implements NetController {

    @Override
    public void baseRequest(final String url, final HashMap<String, String> params, final iCallbackResult callback) {
        RequestParams request = new RequestParams(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().post(request, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.e(getClass(), "url", url, "/n result", result);
                if (result != null && callback != null)
                    callback.success(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    @Override
    public void travelInfo(iCallbackResult callback) {
        baseRequest(NetConstants.URL_TG_LIST, null, callback);
    }

    @Override
    public void getDataUpdata(iCallbackResult callback) {
        HashMap<String, String> param = new HashMap<>();
        param.put("xlid", UtilFactory.getSharedPreferencesUtil().getString(ShareConstants.XLID));
        param.put("usertime", UtilFactory.getSharedPreferencesUtil().getString(ShareConstants.USER_TIME));
        baseRequest(NetConstants.URL_DATA_UPDATA, param, callback);
    }

    @Override
    public void getFirstDataUpdata(iCallbackResult callback) {
        HashMap<String, String> param = new HashMap<>();
        baseRequest(NetConstants.URL_DATA_UPDATA, param, callback);
    }

    @Override
    public void getUserList(iCallbackResult callback) {
        baseRequest(NetConstants.URL_USER_LIST, null, callback);
    }
}
