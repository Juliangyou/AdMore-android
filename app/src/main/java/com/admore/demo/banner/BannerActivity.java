package com.admore.demo.banner;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admore.demo.R;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreBannerExpressAd;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

import java.util.List;

public class BannerActivity extends BaseActivity {

    private FrameLayout adMain;
    private String mAdUnitId;
    private AdMoreBannerExpressAd mBannerViewAd;
    private boolean mIsLoaded;

    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        adMain = findViewById(R.id.ad_banner);
        ((TextView) findViewById(R.id.title)).setText("banner");
    }

    @Override
    public void initData() {

        adMain.post(new Runnable() {
            @Override
            public void run() {
                startLoadBanner();
            }
        });
    }

    private void startLoadBanner() {
        Log.d(TAG, "startLoadBanner ");
        adMain.removeAllViews();
        IAdMoreNativeAd aNative = AdMoreSdk.getAdManager().createNative(BannerActivity.this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setAdCount(1)
                .setCodeId("102361698")
                .setAdSize(new AdSize(adMain.getWidth(), adMain.getHeight())).build();
        aNative.loadBannerExpressAd(adMoreSlot, new AdMoreBannerExpressAd.IAdMoreBannerCallBack() {

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError " + message);
            }

            @Override
            public void onBannerAdLoad(List<AdMoreBannerExpressAd> ads) {
                Log.d(TAG, "onBannerAdLoad size" + ads.size());
                if (ads.isEmpty()) return;
                mIsLoaded = true;
                mBannerViewAd = ads.get(0);
                mBannerViewAd.setAdMoreBannerInteractionListener(new AdMoreBannerExpressAd.IAdMoreBannerInteractionListener() {
                    @Override
                    public void onAdClicked(View var1, int var2) {
                        Log.d(TAG, "onAdClicked ");

                    }

                    @Override
                    public void onAdShow(View var1, int var2) {
                        Log.d(TAG, "onAdShow ");

                    }

                    @Override
                    public void onRenderFail(View var1, String var2, int var3) {
                        Log.d(TAG, "onRenderFail ");

                    }

                    @Override
                    public void onRenderSuccess(View var1, float var2, float var3) {
                        Log.d(TAG, "onRenderSuccess ");

                    }
                });
                mBannerViewAd.setAdMoreDisLikeCallBack(BannerActivity.this, new AdMoreBannerExpressAd.AdMoreDisLikeCallBack() {
                    @Override
                    public void onShow() {
                        Log.d(TAG, "onShow ");

                    }

                    @Override
                    public void onSelected(int var1, String var2, boolean var3) {
                        Log.d(TAG, "onSelected " + var1 + " " + var2 + " " + var3);

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancel ");

                    }
                });
                showBanner();
            }
        });


    }

    public void showBanner() {
        // 加载成功才能展示
        if (mIsLoaded && mBannerViewAd != null) {
            // 在添加banner的View前需要清空父容器
            adMain.removeAllViews();
            mIsLoaded = false;

            // 注意：mBannerViewAd.getBannerView()一个广告对象只能调用一次，第二次为null
            View view = mBannerViewAd.getAdView();
            if (view != null) {
                adMain.addView(view);
            } else {
                Toast.makeText(getApplicationContext(), "请重新加载广告", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "请先加载广告", Toast.LENGTH_SHORT).show();
        }
    }
}
