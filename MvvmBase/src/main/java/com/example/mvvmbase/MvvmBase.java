package com.example.mvvmbase;

import android.app.Application;
import android.content.Context;

import com.example.network.HttpUtils;
import com.example.network.NetWorkAppliction;

public class MvvmBase {

    private static Context context;

    public static void init(Application context, String baseUrl){
        MvvmBase.context = context.getApplicationContext();
        NetWorkAppliction.init(context,baseUrl);
    }

    public static Context getContext() {
        return context;
    }

}
