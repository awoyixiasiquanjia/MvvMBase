package com.example.mvvmbase;


import android.app.Application;
import android.content.Context;

import com.example.network.HttpUtils;

public class MvvmBase {

    private static Context context;

    public static void init(Application context, String baseUrl){
        MvvmBase.context = context.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
