package com.hbln.myadvertisingtest.FrameWork;


import com.hbln.myadvertisingtest.Tool.ImageLoadUtil;
import com.hbln.myadvertisingtest.Tool.SharedPreferencesUtil;
import com.hbln.myadvertisingtest.Tool.TimeUtil;
import com.hbln.myadvertisingtest.Tool.UiUtil;

/**
 * 自定义的工具工厂类
 * Created by lwc on 2015/12/24.
 */
public class UtilFactory {

    /**
     * 图片下载类
     *
     * @return ImageLoadUtil
     */
    public static ImageLoadUtil getImageLoadUtil() {
        return ImageLoadUtil.getInstance();
    }

    /**
     * 共同资源类
     *
     * @return SharedPreferencesUtil
     */
    public static SharedPreferencesUtil getSharedPreferencesUtil() {
        return SharedPreferencesUtil.getInstance();
    }

    /**
     * 时间转换帮助类
     *
     * @return TimeUtil
     */
    public static TimeUtil getTimeUtil() {
        return TimeUtil.getInstance();
    }

    /**
     * 界面转换帮助类类
     *
     * @return UiUtil
     */
    public static UiUtil getUiUtil() {
        return UiUtil.getInstance();
    }
}

