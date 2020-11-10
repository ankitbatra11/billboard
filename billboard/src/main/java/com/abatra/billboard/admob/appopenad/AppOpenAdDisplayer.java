package com.abatra.billboard.admob.appopenad;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.abatra.android.wheelie.lifecycle.ActivityLifecycleCallbackObserver;
import com.abatra.android.wheelie.lifecycle.AppLifecycleObserver;
import com.abatra.billboard.Ad;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

class AppOpenAdDisplayer extends FullScreenContentCallback implements AppLifecycleObserver, ActivityLifecycleCallbackObserver {

    public static final String LOG_TAG = "AppOpenAdDisplayer";

    private static final AtomicBoolean showingAd = new AtomicBoolean(false);

    private final Application application;
    private final Ad appOpenAd;

    @Nullable
    private Activity currentActivity;

    private AppOpenAdDisplayer(Application application, Ad appOpenAd) {
        this.application = application;
        this.appOpenAd = appOpenAd;
    }

    static AppOpenAdDisplayer create(Application application, Ad appOpenAd) {
        AppOpenAdDisplayer appOpenAdDisplayer = new AppOpenAdDisplayer(application, appOpenAd);
        appOpenAdDisplayer.observeAppLifecycle();
        return appOpenAdDisplayer;
    }

    @Override
    public void onAppCreated() {
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }

    @Override
    public void onAppStarted() {
        Log.d(LOG_TAG, "onAppStarted");
        if (!showingAd.get() && currentActivity instanceof AppOpenAdActivity && appOpenAd.isLoaded()) {
            appOpenAd.render((AppOpenAdActivity) currentActivity);
        }
    }

    @Override
    public void onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent();
        Log.d(LOG_TAG, "onAdShowedFullScreenContent");
        showingAd.set(true);
    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError adError) {
        super.onAdFailedToShowFullScreenContent(adError);
        Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent error=" + adError);
        showingAd.set(false);
    }

    @Override
    public void onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent();
        Log.d(LOG_TAG, "onAdDismissedFullScreenContent");
        showingAd.set(false);
    }

    @Override
    public void onAppDestroyed() {
        Log.d(LOG_TAG, "onAppDestroyed");
        application.unregisterActivityLifecycleCallbacks(this);
        appOpenAd.onDestroy();
    }
}
