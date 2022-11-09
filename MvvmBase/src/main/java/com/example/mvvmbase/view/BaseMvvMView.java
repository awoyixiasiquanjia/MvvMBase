package com.example.mvvmbase.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;

public interface BaseMvvMView {

    /**
     * 无数据
     */
    void onNoData(String tips);

    /**
     *展示错误页面
     */
    void onShowError(String errorMsg);

    /**
     *展示loading
     */
    void showLoadingDialog();

    /**
     *关闭loading
     */
    void dismissLoadingDialog();


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
