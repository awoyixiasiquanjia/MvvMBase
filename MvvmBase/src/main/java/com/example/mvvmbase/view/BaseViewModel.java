package com.example.mvvmbase.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

        private MutableLiveData<String> showEmpty;//展示空页面

        private MutableLiveData<Boolean> showLoading;//loading页面

        public MutableLiveData<String> getShowErrorEvent() {
            return showErrorEvent = createLiveData(showErrorEvent);
        }

        public MutableLiveData<Boolean> getShowLoading(){
            return showLoading = createLiveData(showLoading);
        }

        public MutableLiveData<String> getShowEmpty(){
            return showEmpty = createLiveData(showEmpty);
        }


        private <T> MutableLiveData<T> createLiveData(MutableLiveData<T> liveData) {
               if (liveData == null){
                      liveData = new MutableLiveData();
               }
               return liveData;
        }
    }

    public void postErrorEvent(String errorEvent){
             if (uiChangeLiveData!=null){
                 uiChangeLiveData.getShowErrorEvent().postValue(errorEvent);
             }
    }

    public void postshowEmpty(String emptyString){
        if (uiChangeLiveData!=null){
            uiChangeLiveData.getShowEmpty().postValue(emptyString);
        }
    }

    public void postshowLoading(Boolean showLoading){
        if (uiChangeLiveData!=null){
            uiChangeLiveData.getShowLoading().postValue(showLoading);
        }
    }

}
