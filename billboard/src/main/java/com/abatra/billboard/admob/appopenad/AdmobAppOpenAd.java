package com.abatra.billboard.admob.appopenad;

import android.app.Application;
import android.util.Log;

import com.abatra.billboard.AbstractAd;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

import static com.google.android.gms.ads.appopen.AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT;

public class AdmobAppOpenAd extends AbstractAd {

    private static final String LOG_TAG = "AdmobAppOpenAd";

    private final Application application;
    private final String adUnitId;
    private AppOpenAdManager appOpenAdManager;

    private AdmobAppOpenAd(Application application, String adUnitId) {
        this.application = application;
        this.adUnitId = adUnitId;
    }

    public static AdmobAppOpenAd loadAd(Application application, String adUnitId, AppOpenAdShowCondition condition) {

        MobileAds.initialize(application, initializationStatus -> Log.i(LOG_TAG, " MobileAds.initialized"));

        AdmobAppOpenAd admobAppOpenAd = new AdmobAppOpenAd(application, adUnitId);
        AdCallback adCallback = new AdCallback() {
            @Override
            public void adClosed() {
                Log.d(LOG_TAG, "received adClosed callback");
                admobAppOpenAd.loadAd(this);
            }
        };
        admobAppOpenAd.appOpenAdManager = AppOpenAdManager.initialize(application, condition);
        admobAppOpenAd.loadAd(adCallback);

        return admobAppOpenAd;
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        Log.d(LOG_TAG, "loading app open ad");
        appOpenAdManager.setAdCallback(adCallback);
        AppOpenAd.load(application, adUnitId,
                new AdRequest.Builder().build(),
                APP_OPEN_AD_ORIENTATION_PORTRAIT,
                appOpenAdManager);
    }

    @Override
    public void render(AdRenderer adRenderer) {
    }

    @Override
    public boolean isLoaded() {
        return appOpenAdManager != null && appOpenAdManager.isLoaded();
    }

    @Override
    public void onDestroy() {
        appOpenAdManager.destroy();
    }
}
