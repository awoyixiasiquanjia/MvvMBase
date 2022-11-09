package com.example.mvvmbase.view.dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvmbase.R;

public class CustomDialog extends BaseDialog {
    private boolean cancelTouchout;
    private boolean cancelable;
    private View view;

    private CustomDialog(Builder builder) {
        this(builder, R.style.dialog);
    }

    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle, builder.isfullShow, builder.showTitleBar);
        cancelTouchout = builder.cancelTouchout;
        cancelable = builder.cancelable;
        view = builder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        setCancelable(cancelable);
    }

    public View getView(int viewRes) {
        return view.findViewById(viewRes);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static final class Builder {

        private Context context;
        private boolean cancelTouchout;
        private boolean cancelable;
        private View view;
        private int resStyle = -1;
        private boolean isfullShow;
        private boolean showTitleBar;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean cancelTouchout) {
            this.cancelTouchout = cancelTouchout;
            return this;
        }

        public Builder canceLable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setIsfullShow(boolean isfullShow) {
            this.isfullShow = isfullShow;
            return this;
        }

        public Builder setShowTitleBar(boolean showTitleBar) {
            this.showTitleBar = showTitleBar;
            return this;
        }

        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public Builder setTextContent(int viewRes, String value) {
            ((TextView) view.findViewById(viewRes)).setText(value);
            return this;
        }

        public Builder setTextColor(int viewRes, int value) {
            ((TextView) view.findViewById(viewRes)).setTextColor(view.getResources().getColor(value));
            return this;
        }

        public View findViewById(int viewRes) {
            return view.findViewById(viewRes);
        }

        public CustomDialog build() {
            CustomDialog customDialog = new CustomDialog(this, resStyle == -1 ? R.style.dialog : resStyle);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getLifecycle().addObserver(customDialog);
            }
            return customDialog;
        }
    }

}

