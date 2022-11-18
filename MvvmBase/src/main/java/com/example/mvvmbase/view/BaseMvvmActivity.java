package com.example.mvvmbase.view;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mvvmbase.R;
import com.example.mvvmbase.view.dialog.CustomDialog;
import com.example.mvvmbase.view.utils.InstanceUtil;

/**
 * @author sujinbiao
 * @date 2022/10/26
 * @description  Activity的基类
 */

public abstract class BaseMvvmActivity<VB extends ViewDataBinding,VM extends BaseViewModel> extends AppCompatActivity  implements BaseMvvMView {

    protected VB mBinding;
    protected VM mViewModel;
    private CustomDialog loadDialog;
    private LottieAnimationView lvLoading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutRes()==0){
             throw new SecurityException("getLayoutRes not 0!!");
        }
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutRes(), null, false);
        mBinding.getRoot().setFitsSystemWindows(true);
        setContentView(mBinding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//竖屏
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
             getBundleExtras(extras);
        }
       initView();
       initViewModel();
       initData();
    }

    /**
     * 创建viewModel对象
     */
    public void initViewModel(){
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

        mViewModel.getUiChangeLiveData().getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                 if (aBoolean){
                     showLoadingDialog();
                 }else{
                     dismissLoadingDialog();
                 }
            }
        });

        mViewModel.getUiChangeLiveData().getShowEmpty().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    onNoData(s);
            }
        });

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
                loadDialog = new CustomDialog.Builder(this).view(R.layout.dialog_loading).style(R.style.no_shade_dialog).build();
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
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
