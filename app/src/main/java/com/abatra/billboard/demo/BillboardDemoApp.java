package com.abatra.billboard.demo;

import androidx.multidex.MultiDexApplication;

import com.abatra.billboard.admob.appopenad.AdmobAppOpenAd;

public class BillboardDemoApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AdmobAppOpenAd.loadAd(this,
                "ca-app-pub-3940256099942544/3419835294",
                currentActivity -> currentActivity instanceof MainActivity);
    }
}
