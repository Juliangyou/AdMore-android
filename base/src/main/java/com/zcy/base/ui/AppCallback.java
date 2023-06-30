package com.zcy.base.ui;

/**
 * Created by zcy on 2019/6/13.
 *
 * @author zcy
 * @Date 2019/6/13.
 */
public interface AppCallback {
    /**
     * 绑定View操作
     */
    int bindLayout();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();


}
