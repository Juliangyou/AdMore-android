package com.admore.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zcy.base.ui.BaseActivity;

import io.reactivex.functions.Consumer;


/**
 * Created by zcy on 2019/6/13.
 *
 * @author zhaochenyu@mxtrip.cn
 * @Date 2019/6/13.
 */
public class WelcomeActivity extends BaseActivity {

    private RxPermissions rxPermissions;
    private FrameLayout adMore;
    private ImageView iv;

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
        iv = findViewById(R.id.iv);
    }

    @Override
    public void initData() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
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
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        }, 3000);
    }
}
