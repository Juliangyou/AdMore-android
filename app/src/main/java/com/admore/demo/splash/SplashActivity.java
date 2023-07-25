package com.admore.demo.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

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
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zcy.base.ui.BaseActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    private RxPermissions rxPermissions;
    private AdMoreSplashAd splashAd;
    private FrameLayout adMore;

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
    }

    @Override
    public void initData() {
        rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.requestEach(Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE).
                subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            gotoNext();
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {

                        }
                    }
                });
    }

    private void gotoNext() {
        startSplashAd();
    }

    private void startSplashAd() {
        if (!AdMoreSdk.init) {
            gotoMain();
            return;
        }
        AdMoreMediationAdSlot.AdMoreMediationSplashRequestInfo splashInfo =
                new AdMoreMediationAdSlot.AdMoreMediationSplashRequestInfo(AdMoreMediationAdSlot.ADN_PANGLE, "888355782", "5407100", "");
        IAdMoreNativeAd aNative = AdMoreSdk.getAdManager().createNative(this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setCodeId("102381470")
                .setMediationAdSlot(new AdMoreMediationAdSlot.Builder()
                        .setAdMoreMediationSplashRequestInfo(splashInfo)
                        .build())
                .setOrientation(AdMoreSlot.VERTICAL)
                .setAdSize(new AdSize(UIUtils.getScreenWidth(this), UIUtils.getScreenHeight(this))).build();
        aNative.loadSplashAd(adMoreSlot, new AdMoreSplashAd.IAdMoreSplashCallBack() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError " + message);

            }

            @Override
            public void onSplashAdLoad(AdMoreSplashAd ad) {
                Log.d(TAG, "onSplashAdLoad ");
                splashAd = ad;
                showBanner();
            }

            @Override
            public void onTimeout() {
                Log.d(TAG, "onTimeOut ");
                gotoMain();
            }
        });


    }

    private void showBanner() {
        splashAd.setAdMoreInteractionListener(new AdMoreSplashAd.AdMoreInteractionListener() {
            @Override
            public void onAdClicked(View var1, int var2) {
                Log.d(TAG, "onAdClicked " + var1 + " " + var2);

            }

            @Override
            public void onAdShow(View var1, int var2) {
                Log.d(TAG, "onAdClicked " + var2);

            }

            @Override
            public void onAdSkip() {
                Log.d(TAG, "onAdSkip ");
                gotoMain();
            }

            @Override
            public void onAdTimeOver() {
                Log.d(TAG, "onAdTimeOver ");
                gotoMain();
            }
        });

        adMore.addView(splashAd.getView());
    }

    private void gotoMain() {
        Log.e("splash", "find", new Throwable());
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
