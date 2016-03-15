package com.hbln.myadvertisingtest.Config;

/**
 * 网页Url
 * Created by lwc on 2015/12/26.
 */
public class NetConstants {
    //主要请求地址
    public static final String HOST = "http://www.ttysj.com";
    private static final String HOST_API = "http://www.ttysj.com/index.php/Home/Wxapi/";

    public static final String URL_TG_LIST = HOST_API + "tglist";
    public static final String URL_USER_LIST = HOST_API + "get_userinfo/csid/1";
    public static final String URL_DATA_UPDATA = HOST_API + "dataupdata";//usertime 用户时间最后1个 xlid 线路ID第1个

    //得到二维码
    public static final String URL_GET_QR = HOST_API + "getqr/csid/1";//获得ticket的值，用于下一个接口
    public static final String URL_GET_QR_IMAGE = HOST_API + "https://mp.weixin.qq.com/cgi-bin/showqrcode";//ticket 上一个接口传下来的


}
