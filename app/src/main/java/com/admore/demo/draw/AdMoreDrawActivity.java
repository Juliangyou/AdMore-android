package com.admore.demo.draw;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.admore.demo.R;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreDrawAd;
import com.admore.sdk.config.AdMoreRenderListener;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

import java.util.List;

public class AdMoreDrawActivity extends BaseActivity {

    private FrameLayout adMain;
    private AdMoreDrawAd adMoreDrawAd;
    private boolean mLoadSuccess;

    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        adMain = findViewById(R.id.ad_main);
        ((TextView) findViewById(R.id.title)).setText("draw");

    }

    @Override
    public void initData() {

        adMain.post(new Runnable() {
            @Override
            public void run() {
                startDrawAd();
            }
        });
    }

    private void startDrawAd() {
        IAdMoreNativeAd aNative = AdMoreSdk.getAdManager().createNative(this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setAdCount(1)
                .setCodeId("102360464")
                .setOrientation(AdMoreSlot.VERTICAL)
                .setAdSize(new AdSize(adMain.getWidth(), adMain.getHeight())).build();
        aNative.loadDrawAd(adMoreSlot, new AdMoreDrawAd.AdMoreDrawLoadListener() {
            @Override
            public void onError(int var1, String message) {
                Toast.makeText(getApplicationContext(), "广告加载失败！" + message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError " + message);

            }

            @Override
            public void onDrawFeedAdLoad(List<AdMoreDrawAd> ads) {
                Log.d(TAG, "onDrawFeedAdLoad " + ads.size());
                if (ads == null || ads.isEmpty()) {
                    Log.e(TAG, "on draw: ad is null!");
                    Toast.makeText(getApplicationContext(), "广告加载失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLoadSuccess = true;
                adMoreDrawAd = ads.get(0);
                Toast.makeText(getApplicationContext(), "广告加载成功！", Toast.LENGTH_SHORT).show();
                showDrawAd();
            }
        });


    }


    private void showDrawAd() {
        if (!mLoadSuccess || adMoreDrawAd == null) {
            Toast.makeText(getApplicationContext(), "请先加载广告！", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (adMoreDrawAd.getDrawAdManager().isExpress())
        mLoadSuccess = false;
        adMoreDrawAd.setAdMoreRenderListener(new AdMoreRenderListener() {
            @Override
            public void onRenderFail(View view, String message, int code) {
                Log.d(TAG, "onRenderFail " + message);

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
                Log.d(TAG, "onRenderSuccess " + var2 + " " + var3 + " " + var4);
                adMain.removeAllViews();
                View adView = adMoreDrawAd.getAdView();
                if (adView.getParent() != null && adView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) adView.getParent()).removeAllViews();
                }
                adMain.addView(adView);
            }
        });
        adMoreDrawAd.render();
    }
}
