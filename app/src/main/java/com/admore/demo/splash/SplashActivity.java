package com.admore.demo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.admore.demo.MainActivity;
import com.admore.demo.R;
import com.admore.demo.util.UIUtils;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreSplashAd;
import com.admore.sdk.config.AdMoreMediationAdSlot;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

public class SplashActivity extends BaseActivity {

    private AdMoreSplashAd splashAd;
    private FrameLayout adMore;
    private IAdMoreNativeAd adNative;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        adMore = findViewById(R.id.ad_main);
        ((TextView) findViewById(R.id.tt)).setText("广告splash");
    }

    @Override
    public void initData() {
        startSplashAd();
    }

    private void startSplashAd() {
        if (!AdMoreSdk.init) {
            Log.e(TAG, "AdMoreSdk.init = false");
            gotoMain();
            return;
        }
        AdMoreMediationAdSlot.AdMoreMediationSplashRequestInfo splashInfo =
                new AdMoreMediationAdSlot.AdMoreMediationSplashRequestInfo(AdMoreMediationAdSlot.ADN_PANGLE, "888355782", "5407100", "");
        adNative = AdMoreSdk.getAdManager().createNative(this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setCodeId("102381470")
                .setMediationAdSlot(new AdMoreMediationAdSlot.Builder()
                        .setAdMoreMediationSplashRequestInfo(splashInfo)
                        .build())
                .setOrientation(AdMoreSlot.VERTICAL)
                .setAdSize(new AdSize(UIUtils.getScreenWidth(this), UIUtils.getScreenHeight(this))).build();
        adNative.loadSplashAd(adMoreSlot, new AdMoreSplashAd.IAdMoreSplashCallBack() {
            @Override
            public void onError(int i, String message) {
                Log.d(TAG, "onError " + message);
                Toast.makeText(getApplicationContext(), "广告加载失败！" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSplashAdLoad(AdMoreSplashAd ad) {
                Log.d(TAG, "onSplashAdLoad ");
                splashAd = ad;
                showSplash();
            }
        }, 3000);
    }

    private void showSplash() {
        splashAd.setAdMoreInteractionListener(new AdMoreSplashAd.AdMoreInteractionListener() {
            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked ");

            }

            @Override
            public void onAdShow() {
                Toast.makeText(getApplicationContext(), "广告加载成功 ！", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClose(int closeType) {
                if (closeType == AdMoreSplashAd.CLICK_SKIP) {
                    Log.d(TAG, "开屏广告点击跳过 ");
                } else if (closeType == AdMoreSplashAd.COUNT_DOWN_OVER) {
                    Log.d(TAG, "开屏广告点击倒计时结束");
                } else if (closeType == AdMoreSplashAd.CLICK_JUMP) {
                    Log.d(TAG, "点击跳转");
                }
                gotoMain();
            }
        });
        splashAd.showSplash(adMore);
    }

    private void gotoMain() {
        Log.e("splash", "find", new Throwable());
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
