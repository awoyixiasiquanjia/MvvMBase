package com.example.mvvmbase.view;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.mvvmbase.view.utils.InstanceUtil;
/**
 * @author sujinbiao
 * @date 2022/10/26
 * @description  Activity的基类
 */

public abstract class BaseMvvmActivity<VB extends ViewDataBinding,VM extends BaseViewModel> extends AppCompatActivity  implements BaseMvvMView {

    protected VB mBinding;
    protected VM mViewModel;

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



    @Override
    public void onNetComplete() {

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



}
