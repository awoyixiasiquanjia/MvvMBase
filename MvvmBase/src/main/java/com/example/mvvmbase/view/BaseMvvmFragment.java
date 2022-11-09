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

import com.airbnb.lottie.LottieAnimationView;
import com.example.mvvmbase.R;
import com.example.mvvmbase.view.dialog.CustomDialog;
import com.example.mvvmbase.view.utils.InstanceUtil;


/**
 * @author sujinbiao
 * @date 2022/10/26
 * @description  Fragment的基类
 */

public abstract class BaseMvvmFragment <VB extends ViewDataBinding,VM extends BaseViewModel> extends Fragment implements BaseMvvMView {

    protected VB mBinding;
    protected VM mViewModel;
    private CustomDialog loadDialog;
    private LottieAnimationView lvLoading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding==null){
            mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
            initView();
            initViewModel();
            initData();
        }
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
    public void onNoData(String tips) {

    }

    @Override
    public void onShowError(String errorMsg) {

    }


    @Override
    public void showLoadingDialog() {
        try {
            if (loadDialog == null) {
                loadDialog = new CustomDialog.Builder(getContext()).view(R.layout.dialog_loading).style(R.style.no_shade_dialog).build();
                lvLoading = (LottieAnimationView) loadDialog.getView(R.id.lav_loading);
                loadDialog.show();
                if (lvLoading != null) {
                    lvLoading.playAnimation();
                }
            }
        } catch (Exception ex) {

        }

    }

    @Override
    public void dismissLoadingDialog() {
        if (loadDialog != null && this != null) {
            try {
                lvLoading.cancelAnimation();
                loadDialog.dismiss();
                loadDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        mViewModel.getUiChangeLiveData().getShowErrorEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                onShowError(s);
            }
        });

        mViewModel.getUiChangeLiveData().getShowLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    showLoadingDialog();
                }else{
                    dismissLoadingDialog();
                }
            }
        });

        mViewModel.getUiChangeLiveData().getShowEmpty().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                       onNoData(s);
            }
        });

    }

}
