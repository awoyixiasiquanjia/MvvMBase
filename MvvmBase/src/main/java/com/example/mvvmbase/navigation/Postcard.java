package com.example.mvvmbase.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.mvvmbase.R;

import java.util.Objects;

public class Postcard {
     private int pathID;
     private Bundle mBundle;
     private int enterAnim = R.anim.from_right_in;//默认切换动画
     private int exitAnim = R.anim.to_left_out;
     private Activity activity;
     private View mView;

    public static Build build(View mView){
        return new Build(mView);
    }

    public final static   class Build{
          private int pathID;
          private Bundle mBundle;
          private int enterAnim = -1;
          private int exitAnim = -1;
          private Activity activity;
          private View mView;
          public Build PathID(@IdRes int pathID){
              this.pathID =pathID;
              return this;
          }

        public Build(View mView) {
              Objects.requireNonNull(mView,"mView cannot is null");
             this.mView = mView;
        }

         public Build withBundle(@Nullable String key, @Nullable Bundle value) {
              mBundle.putBundle(key, value);
              return this;
         }

         public Build withTransition(int enterAnim, int exitAnim) {
              this.enterAnim = enterAnim;
              this.exitAnim = exitAnim;
              return this;
         }

        public  void navigation(){
              Objects.requireNonNull(this.pathID,"pathID cannot null");
             Postcard postcard = new Postcard();
             postcard.pathID = this.pathID;
             postcard.mBundle = this.mBundle;
             postcard.mView = this.mView;
             if (this.enterAnim!=-1){
                  postcard.enterAnim = this.enterAnim;
             }
             if (this.exitAnim!=-1){
                  postcard.exitAnim = this.exitAnim;
             }
            postcard.navigation();
        }

    }


     public void navigation(){
         NavController navController;
             navController = Navigation.findNavController(mView);
         NavOptions navOptions = new NavOptions.Builder().setEnterAnim(enterAnim).setExitAnim(exitAnim).build();
         navController.navigate(pathID,mBundle,navOptions);
     }



}
