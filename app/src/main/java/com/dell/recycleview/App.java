package com.dell.recycleview;

import android.app.Application;
import android.os.Build;

import org.xutils.*;
import org.xutils.BuildConfig;

/**
 * 姓名：王有为
 * 时间：2017/9/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
