package com.admore.demo.manager;

import android.content.Context;
import android.os.Build;

import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.config.AdMoreConfig;
import com.admore.sdk.config.IAdMoreConfig;

public class SdMoreManager {


    public static void init(Context context, String appName, String appId) {
        init(context,appName,appId,null);
    }
    public static void init(Context context, String appName, String appId,AdMoreSdk.InitCallBack initCallBack) {
        AdMoreSdk.initialize(context, buildV2Config(appName, appId),initCallBack);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            String processName = UIUtils.getCurrProcessName(context);
//            if (!"com.header.app.untext".equals(processName)) {
//                WebView.setDataDirectorySuffix(processName);
//            }
        }
    }

    /**
     * 初始化配置类
     * 更多配置参数请参考接入文档
     *
     * @return
     */
    public static IAdMoreConfig buildV2Config(String name, String appId) {
        return buildV2Config(name, appId, true);
    }

    public static IAdMoreConfig buildV2Config(String name, String appId, boolean debug) {
        return new AdMoreConfig.Builder()
                /**
                 * 注：需要替换成在媒体平台申请的appID ，切勿直接复制
                 */
                .setAppId(appId)
                .setAppName(name)
                /**
                 * 上线前需要关闭debug开关，否则会影响性能
                 */
                .setDebug(debug)
                .build();
    }
}
