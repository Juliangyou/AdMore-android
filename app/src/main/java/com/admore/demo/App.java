package com.admore.demo;

import android.util.Log;

import androidx.multidex.MultiDex;

import com.admore.demo.manager.SdMoreManager;
import com.admore.sdk.AdMoreSdk;
import com.zcy.base.app.BaseApp;

public class App extends BaseApp {
    public static boolean adInit = false;

    @Override
    public void onCreate() {
        super.onCreate();
        SdMoreManager.init(this, "智慧城市", "5407100", new AdMoreSdk.InitCallBack() {
            @Override
            public void success() {
                adInit = true;
            }

            @Override
            public void fail(int i, String s) {
                adInit = false;
                Log.d("App", "adInitFailed" + s);
            }
        });
        MultiDex.install(this);

    }
}
