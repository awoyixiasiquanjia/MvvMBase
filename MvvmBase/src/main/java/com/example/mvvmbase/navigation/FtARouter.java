package com.example.mvvmbase.navigation;
import android.view.View;

/**
 * @author sujinbiao
 * @date 2022/10/25
 * @description fragment的跳转工具类
 */

public class FtARouter {
    private volatile static FtARouter instance = null;

    private FtARouter() {

    }

    public static FtARouter getInstance() {
            if (instance == null) {
                synchronized (FtARouter.class) {
                    if (instance == null) {
                        instance = new FtARouter();
                    }
                }
            }
            return instance;
    }

    public Postcard.Build build(View mView) {
           return Postcard.build(mView);
    }

}
