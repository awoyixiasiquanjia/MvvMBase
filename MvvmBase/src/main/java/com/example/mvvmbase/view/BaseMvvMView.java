package com.example.mvvmbase.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;

public interface BaseMvvMView {

    /**
     * 网络请求结束
     */
    void onNetComplete();

    /**
     * 网络故障
     */
    void onNetError();

    /**
     * 无数据
     */
    void onNoData();

    /**
     * 加载中
     */
    void onLoading();

    /**
     *展示错误页面
     */
    void onShowError(String errorMsg);

    /**
     *加载更多结束
     */
    void endLoadMore();

    @LayoutRes
    int getLayoutRes();



    /**
     * 初始化布局
     */
      void initView();

    /**
     * 处理具体的业务逻辑
     */
      void initData();

    /**
     * 获取资源数据
     */
      void getBundleExtras(Bundle extras);

      void initViewModel();

}
