package com.example.mvvmbase.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

public class BaseDialog extends Dialog implements DefaultLifecycleObserver {
    public Context context;
    private HashMap<Integer, String> tvMap = new HashMap();
    private boolean isfullShow;
    private boolean showTitleBar;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId, boolean isfullShow, boolean showTitleBar) {
        super(context, themeResId);
        this.context = context;
        this.isfullShow = isfullShow;
        this.showTitleBar = showTitleBar;
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!showTitleBar){
            applyCompat();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setView();
    }

    public void setTVValue(int id, String value) {
        tvMap.put(id, value);
    }

    private void setView() {
        setText();
    }

    public void setText() {
        for (Map.Entry<Integer, String> entry : tvMap.entrySet()) {
            View view = findViewById(entry.getKey());
            ((TextView) view).setText(entry.getValue());
        }
    }

    @Override
    public void show() {
        if (context != null && context instanceof AppCompatActivity && ((AppCompatActivity) context).isFinishing()) {
            return;
        }
        if (!this.isShowing()) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            super.show();
            if (!showTitleBar){
                fullScreenImmersive(getWindow().getDecorView());
            }
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            if (isfullShow) {
                Window window = this.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(layoutParams);
            }
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            try {
                super.dismiss();
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        tvMap.clear();
    }

    public void applyCompat() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void fullScreenImmersive() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

}
