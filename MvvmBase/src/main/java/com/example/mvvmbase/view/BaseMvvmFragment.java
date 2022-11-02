package com.example.mvvmbase.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmbase.view.utils.InstanceUtil;


/**
 * @author sujinbiao
 * @date 2022/10/26
 * @description  Fragment的基类
 */

public abstract class BaseMvvmFragment <VB extends ViewDataBinding,VM extends BaseViewModel> extends Fragment implements BaseMvvMView {

    protected VB mBinding;
    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            getBundleExtras(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        initData();
    }

    @Override
    public void onNetComplete() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onShowError(String errorMsg) {

    }

    @Override
    public void endLoadMore() {

    }


    @Override
    public void initViewModel() {
        Class instance = InstanceUtil.getInstance(this, 1);
        mViewModel = (VM)new ViewModelProvider(this).get(instance);
        //注册页面监听的变化
        registorUIChangeLiveDataCallBack();
    }

    //注册页面Ui布局的监听
    private void registorUIChangeLiveDataCallBack() {
        if (mViewModel==null){
            return;
        }
        mViewModel.getUiChangeLiveData().getShowErrorEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                onShowError(s);
            }
        });

        mViewModel.getUiChangeLiveData().getShowPageEvent().observe(this, new Observer<PageStatus>() {
            @Override
            public void onChanged(PageStatus pageStatus) {
                switch (pageStatus){
                    case EMPTYSTATUS:
                        onNoData();
                    case NONETSTATUS:
                        onNetError();
                    case CONTENTSTATUS:

                }
            }
        });

    }

}
