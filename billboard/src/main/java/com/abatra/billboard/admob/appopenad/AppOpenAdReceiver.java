package com.abatra.billboard.admob.appopenad;

import android.os.SystemClock;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

public class AppOpenAdReceiver extends AdCallbackAppOpenAdCallbackAdapter {

    private long loadTimeMillis;
    private AppOpenAd appOpenAd;

    public AppOpenAdReceiver(Ad ad, AdCallback adCallback) {
        super(ad, adCallback);
    }

    @Override
    public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
        this.appOpenAd = appOpenAd;
        loadTimeMillis = SystemClock.uptimeMillis();
        super.onAppOpenAdLoaded(appOpenAd);
    }

    boolean isLoaded() {
        return appOpenAd != null && wasLoadTimeLessThan(4, TimeUnit.HOURS);
    }

    @SuppressWarnings("SameParameterValue")
    private boolean wasLoadTimeLessThan(int duration, TimeUnit unit) {
        return (SystemClock.uptimeMillis() - loadTimeMillis) < unit.toMillis(duration);
    }

    public void destroy() {
        appOpenAd = null;
    }

    @Nullable
    public AppOpenAd getAppOpenAd() {
        return appOpenAd;
    }
}
