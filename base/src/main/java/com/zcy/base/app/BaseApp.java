package com.zcy.base.app;

import android.app.Application;

/**
 * Created by zcy on 2019/6/13.
 *
 * @author zcy
 * @Date 2019/6/13.
 */
public class BaseApp extends Application {
    private static BaseApp mInstance;

    public static BaseApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
}
