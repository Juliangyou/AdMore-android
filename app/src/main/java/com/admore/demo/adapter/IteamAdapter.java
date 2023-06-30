package com.admore.demo.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.admore.demo.R;

import java.util.List;

/**
 * Created by zcy on 2019-07-12.
 *
 * @author zcy
 * @Date 2019-07-12.
 */
public class IteamAdapter extends BaseQuickAdapter<MenuBean, BaseViewHolder> {
    public IteamAdapter( @Nullable List<MenuBean> data) {
        super(R.layout.item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        helper.setText(R.id.item_tv, item.getName());
    }
}
