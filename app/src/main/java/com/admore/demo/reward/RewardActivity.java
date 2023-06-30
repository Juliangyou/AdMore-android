package com.admore.demo.reward;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admore.demo.R;
import com.admore.demo.util.UIUtils;
import com.admore.sdk.AdMoreSdk;
import com.admore.sdk.ads.AdMoreRewardAd;
import com.admore.sdk.config.AdMoreSlot;
import com.admore.sdk.config.AdSize;
import com.admore.sdk.config.IAdMoreNativeAd;
import com.admore.sdk.config.IAdMoreSlot;
import com.zcy.base.ui.BaseActivity;

public class RewardActivity extends BaseActivity {

    private FrameLayout adMain;
    private boolean mLoadSuccess;
    private AdMoreRewardAd rewardAd;

    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView() {
        adMain = findViewById(R.id.ad_main);
        ((TextView) findViewById(R.id.title)).setText("reward");
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

    private void loadAd() {
        IAdMoreNativeAd rewardVideoAd = AdMoreSdk.getAdManager().createNative(this);
        IAdMoreSlot adMoreSlot = new AdMoreSlot.Builder()
                .setAdCount(1)
                .setCodeId("102326387")
                .setAdSize(new AdSize(UIUtils.getScreenWidth(this), UIUtils.getScreenHeight(this))).build();
        rewardVideoAd.loadRewardAd(adMoreSlot, new AdMoreRewardAd.IAdMoreRewardCallBack() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError " + message);
                mLoadSuccess = false;
                Toast.makeText(getApplicationContext(), "广告加载失败！" + message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRewardAdLoad(AdMoreRewardAd ad) {
                Log.d(TAG, "onRewardAdLoad " + (ad == null));
                if (ad == null) {
                    Log.e(TAG, "on rewardAdlood: ad is null!");
                    Toast.makeText(getApplicationContext(), "广告加载失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "广告加载成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardVideoCached(AdMoreRewardAd ad) {
                Log.d(TAG, "onRewardVideoCached ");
                mLoadSuccess = true;
                rewardAd = ad;
                showAd();
                mLoadSuccess = false;

            }
        });
    }

    private void startAd() {
        loadAd();
    }

    private void showAd() {

        if (mLoadSuccess && rewardAd != null) {
            if (rewardAd.getRewardAdManager().isReady()) {
                //在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                //展示广告，并传入广告展示的场景

                rewardAd.setRewardAdInteractionListener(new AdMoreRewardAd.RewardAdInteractionListener() {
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
                    public void onVideoError() {
                        Log.d(TAG, "onVideoError ");

                    }

                    @Override
                    public void onRewardArrived(boolean var1, int var2, Bundle var3) {
                        Log.d(TAG, "onRewardArrived " + var1 + " " + var2);

                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.d(TAG, "onSkippedVideo ");

                    }
                });
                rewardAd.show(this);
                mLoadSuccess = false;
            } else {
                Toast.makeText(getApplicationContext(), "当前广告不满足show的条件", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "请先加载广告", Toast.LENGTH_SHORT).show();
        }

    }


    private void removeAdView() {
        if (adMain != null) {
            adMain.removeAllViews();
        }
    }

}
