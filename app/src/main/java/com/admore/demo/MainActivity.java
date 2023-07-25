package com.admore.demo;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admore.demo.adapter.MenuBean;
import com.admore.demo.splash.SplashActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zcy.base.ui.BaseActivity;
import com.admore.demo.adapter.IteamAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zcy on 2019-07-12.
 *
 * @author zcy
 * @Date 2019-07-12.
 */
public class MainActivity extends BaseActivity {

    private RecyclerView recycler;
    private List<MenuBean> itemDatas;
    private IteamAdapter mAdapter;
    private int temp = 1;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        recycler = findViewById(R.id.recycler);
    }

    @Override
    public void initData() {
        recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        itemDatas = new ArrayList<>();
        itemDatas.add(new MenuBean("banner", "com.admore.demo.banner.BannerActivity"));
        itemDatas.add(new MenuBean("draw", "com.admore.demo.draw.AdMoreDrawActivity"));
        itemDatas.add(new MenuBean("splash", "com.admore.demo.splash.SplashActivity"));
        itemDatas.add(new MenuBean("reward", "com.admore.demo.reward.RewardActivity"));
        itemDatas.add(new MenuBean("fullScreen", "com.admore.demo.screen.ScreenActivity"));
        itemDatas.add(new MenuBean("native", "com.admore.demo.nativiead.FeedNativeActivity"));

        mAdapter = new IteamAdapter(itemDatas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.equals("splash", itemDatas.get(position).getName())) {
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(itemDatas.get(position).getAction());
                startActivity(intent);
            }
        });
        recycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
