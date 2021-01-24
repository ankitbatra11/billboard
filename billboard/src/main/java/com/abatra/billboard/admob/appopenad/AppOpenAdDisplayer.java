package com.abatra.billboard.admob.appopenad;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.abatra.android.wheelie.lifecycle.ActivityLifecycleCallbackObserver;
import com.abatra.android.wheelie.lifecycle.AppLifecycleObserver;
import com.abatra.billboard.Ad;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import timber.log.Timber;

class AppOpenAdDisplayer extends FullScreenContentCallback implements AppLifecycleObserver, ActivityLifecycleCallbackObserver {

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
    public void onCreate() {
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Timber.v("onActivityStarted=%s", activity);
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Timber.v("onActivityResumed=%s", activity);
        currentActivity = activity;
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Timber.v("onActivityDestroyed=%s", activity);
        currentActivity = null;
    }

    @Override
    public void onStart() {
        Timber.v("onAppStarted currentActivity=" + currentActivity + " showingAd=" + showingAd);
        if (!showingAd.get() && currentActivity instanceof AppOpenAdActivity && appOpenAd.isLoaded()) {
            appOpenAd.render((AppOpenAdActivity) currentActivity);
        }
    }

    @Override
    public void onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent();
        Timber.d("onAdShowedFullScreenContent");
        showingAd.set(true);
    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError adError) {
        super.onAdFailedToShowFullScreenContent(adError);
        Timber.d("onAdFailedToShowFullScreenContent error=%s", adError);
        showingAd.set(false);
    }

    @Override
    public void onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent();
        Timber.d("onAdDismissedFullScreenContent");
        showingAd.set(false);
    }

    @Override
    public void onDestroy() {
        Timber.d("onAppDestroyed");
        application.unregisterActivityLifecycleCallbacks(this);
        appOpenAd.onDestroy();
    }
}
