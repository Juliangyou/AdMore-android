package com.admore.demo.nativiead;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.admore.demo.R;
import com.admore.demo.util.UIUtils;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreNativeAd;
import com.admore.sdk.config.AdMoreDislikeCallBack;
import com.admore.sdk.config.AdMoreRenderListener;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

import java.util.List;

public class FeedNativeActivity extends BaseActivity {

    private FrameLayout adMain;
    private IAdMoreNativeAd adMoreNativeAd;
    private boolean mLoadSuccess;
    private AdMoreNativeAd nativeAd;

    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        adMain = findViewById(R.id.ad_main);
        ((TextView) findViewById(R.id.title)).setText("feed native");
    }

    @Override
    public void initData() {

        adMain.post(new Runnable() {
            @Override
            public void run() {
                startAd();
            }
        });
    }

    private void startAd() {
        adMoreNativeAd = AdMoreSdk.getAdManager().createNative(this);
        loadAd();
    }

    private void loadAd() {
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setAdCount(1)
                .setCodeId("102361333")
                .setAdSize(new AdSize(UIUtils.getScreenWidth(this), UIUtils.getScreenHeight(this))).build();
        adMoreNativeAd.loadNativeAd(adMoreSlot, new AdMoreNativeAd.IAdMoreNativeCallBack() {
            @Override
            public void onError(int code, String message) {
                mLoadSuccess = false;
                Toast.makeText(getApplicationContext(), "广告加载失败！" + message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError " + message);

            }

            @Override
            public void onFeedAdLoad(List<AdMoreNativeAd> ads) {
                Log.d(TAG, "onFeedAdLoad " + ads.size());
                mLoadSuccess = true;
                if (ads == null || ads.isEmpty()) {
                    Log.e(TAG, "on FeedAdLoaded: ad is null!");
                    Toast.makeText(getApplicationContext(), "广告加载失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                nativeAd = ads.get(0);
                Toast.makeText(getApplicationContext(), "广告加载成功！", Toast.LENGTH_SHORT).show();
                showAd();
                mLoadSuccess = false;
            }
        });


    }

    private void showAd() {
        if (!mLoadSuccess || nativeAd == null) {
            Toast.makeText(getApplicationContext(), "请先加载广告！", Toast.LENGTH_SHORT).show();
            return;
        }
        nativeAd.setRenderListener(new AdMoreRenderListener() {
            @Override
            public void onRenderFail(View view, String message, int code) {
                Log.d(TAG, "onRenderFail " + message);
                Toast.makeText(getApplicationContext(), "广告已经无效，请重新请求！", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClick() {
                Log.d(TAG, "onAdClick ");

            }

            @Override
            public void onAdShow() {
                Log.d(TAG, "onAdShow ");

            }

            @Override
            public void onRenderSuccess(View view, float var2, float var3, boolean var4) {
                Log.d(TAG, "onRenderSuccess ");
                if (nativeAd.getNativeAdManager().hasDislike()) {
                    nativeAd.setDislikeCallBack(FeedNativeActivity.this, new AdMoreDislikeCallBack() {
                        @Override
                        public void onShow() {
                            Log.d(TAG, "onShow ");

                        }

                        @Override
                        public void onSelected(int var1, String var2, boolean var3) {
                            Log.d(TAG, "onSelected ");
                            adMain.removeAllViews();

                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "onCancel ");

                        }
                    });
                }

                View adView = nativeAd.getAdView();
                if (adView.getParent() != null && adView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) (adView.getParent())).removeView(view);
                }
                adMain.removeAllViews();
                adMain.addView(adView);
            }
        });

        mLoadSuccess = false;
        nativeAd.render();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adMoreNativeAd != null) {
            adMoreNativeAd.destroy();
        }
    }
}
