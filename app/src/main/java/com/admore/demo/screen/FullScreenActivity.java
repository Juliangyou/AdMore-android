package com.admore.demo.screen;

import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.admore.demo.R;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreFullScreenAd;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

public class FullScreenActivity extends BaseActivity {

    private FrameLayout adMain;
    private AdMoreFullScreenAd screenAd;
    private boolean mLoadSuccess;

    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        adMain = findViewById(R.id.ad_main);
        ((TextView) findViewById(R.id.title)).setText("full Screen");
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
        IAdMoreNativeAd aNative = AdMoreSdk.getAdManager().createNative(this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setAdCount(1)
                .setCodeId("102361045")
                .setOrientation(AdMoreSlot.VERTICAL)
                .setAdSize(new AdSize(adMain.getWidth(), adMain.getHeight())).build();
        aNative.loadScreenFullAd(adMoreSlot, new AdMoreFullScreenAd.AdMoreScreenFullAdVideoListener() {
            @Override
            public void onError(int var1, String message) {
                Log.d(TAG, "onError " + message);

            }

            @Override
            public void onFullScreenVideoCached(AdMoreFullScreenAd var1) {
                Log.d(TAG, "onFullScreenVideoCached ");
                screenAd = var1;
                mLoadSuccess = true;
                showAd();
            }

            @Override
            public void onFullScreenVideoAdLoad(AdMoreFullScreenAd var1) {

                Log.d(TAG, "onFullScreenVideoAdLoad ");
            }
        });
    }


    private void showAd() {

        if (mLoadSuccess && screenAd != null && screenAd.getFullScreenAdManager().isReady()) {
            screenAd.setVideoAdListener(new AdMoreFullScreenAd.FullScreenVideoAdListener() {
                @Override
                public void onAdShow() {
                    Log.d(TAG, "onAdShow ");

                }

                @Override
                public void onAdVideoBarClick() {
                    Log.d(TAG, "onAdVideoBarClick ");

                }

                @Override
                public void onAdClose() {
                    Log.d(TAG, "onAdClose ");

                }

                @Override
                public void onVideoComplete() {
                    Log.d(TAG, "onVideoComplete ");

                }

                @Override
                public void onSkippedVideo() {
                    Log.d(TAG, "onSkippedVideo ");

                }
            });
            screenAd.show(this);
            mLoadSuccess = false;
        } else {
            Toast.makeText(getApplicationContext(), "请先加在广告", Toast.LENGTH_SHORT).show();
        }
    }

}
