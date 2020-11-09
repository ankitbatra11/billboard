package com.abatra.billboard.admob.appopenad;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

class AppOpenAdContext extends AppOpenAd.AppOpenAdLoadCallback implements Application.ActivityLifecycleCallbacks,
        LifecycleObserver {

    public static final String LOG_TAG = "AppOpenAdContext";
    private static boolean showingAd = false;

    private AdCallback adCallback;
    private AppOpenAdShowCondition appOpenAdShowCondition;

    @Nullable
    private AppOpenAd appOpenAd;
    private long loadTime;

    @Nullable
    private Activity currentActivity;

    void setAdCallback(AdCallback adCallback) {
        this.adCallback = adCallback;
    }

    static AppOpenAdContext initialize(Application application, AppOpenAdShowCondition appOpenAdShowCondition) {

        AppOpenAdContext appOpenAdContext = new AppOpenAdContext();
        appOpenAdContext.appOpenAdShowCondition = appOpenAdShowCondition
                .and(currentActivity -> appOpenAdContext.currentActivity != null)
                .and(currentActivity -> appOpenAdContext.isLoaded())
                .and(currentActivity -> !AppOpenAdContext.showingAd);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(appOpenAdContext);
        application.registerActivityLifecycleCallbacks(appOpenAdContext);

        return appOpenAdContext;
    }

    @Override
    public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
        super.onAppOpenAdLoaded(appOpenAd);
        Log.d(LOG_TAG, "app open ad loaded");
        this.appOpenAd = appOpenAd;
        loadTime = SystemClock.uptimeMillis();
        adCallback.adLoaded();
    }

    @Override
    public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
        super.onAppOpenAdFailedToLoad(loadAdError);
        Log.d(LOG_TAG, "app open ad failed to load error=" + loadAdError);
    }

    boolean isLoaded() {
        return appOpenAd != null && wasLoadTimeLessThan(4, TimeUnit.HOURS);
    }

    private boolean wasLoadTimeLessThan(int duration, TimeUnit unit) {
        return (SystemClock.uptimeMillis() - loadTime) < unit.toMillis(duration);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @androidx.annotation.Nullable Bundle savedInstanceState) {
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
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppStart() {
        if (appOpenAdShowCondition.showAd(currentActivity)) {
            assert appOpenAd != null;
            appOpenAd.show(currentActivity, new FullScreenContentCallback() {

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    Log.d(LOG_TAG, "onAdShowedFullScreenContent");
                    showingAd = true;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.d(LOG_TAG, "onAdDismissedFullScreenContent");
                    showingAd = false;
                    appOpenAd = null;
                    adCallback.adClosed();
                }
            });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onAppDestroy() {
        appOpenAdShowCondition = null;
        currentActivity = null;
        appOpenAd = null;
        adCallback = null;
    }
}
