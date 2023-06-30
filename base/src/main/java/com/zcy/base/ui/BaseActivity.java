package com.zcy.base.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by zcy on 2019/6/13.
 *
 * @author zcy
 * @Date 2019/6/13.
 */
public abstract class BaseActivity extends AppCompatActivity implements AppCallback {
    protected Activity mActivity;
    protected String TAG = getClass().getSimpleName() + "TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(bindLayout());
        initView();
        initData();
    }
}
