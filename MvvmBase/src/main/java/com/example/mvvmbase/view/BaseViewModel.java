package com.example.mvvmbase.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel  {
    private UIChangeLiveData uiChangeLiveData;
    public BaseViewModel(@NonNull Application application) {
        super(application);

    }

    /**
     * 页面状态改变的livedata
     */
    public UIChangeLiveData getUiChangeLiveData() {
        if (null == uiChangeLiveData) {
            uiChangeLiveData = new UIChangeLiveData();
        }
        return uiChangeLiveData;
    }

    public final class UIChangeLiveData{

        private MutableLiveData<String> showErrorEvent;   //错误信息

        private MutableLiveData<PageStatus> showPageEvent;//展示页面信息

        public MutableLiveData<String> getShowErrorEvent() {
            return showErrorEvent = createLiveData(showErrorEvent);
        }

        public MutableLiveData<PageStatus> getShowPageEvent(){
            return showPageEvent = createLiveData(showPageEvent);
        }

        private <T> MutableLiveData<T> createLiveData(MutableLiveData<T> liveData) {
               if (liveData == null){
                      liveData = new MutableLiveData<>();
               }
               return liveData;
        }
    }

    public void postShowErrorEvent(String errorMsg){
               if (uiChangeLiveData!=null){
                   uiChangeLiveData.getShowErrorEvent().postValue(errorMsg);
               }
    }

    public void postShowPageEvent(PageStatus pageStatus){
             if (uiChangeLiveData!=null){
                 uiChangeLiveData.getShowPageEvent().postValue(pageStatus);
             }
    }


}
