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
    private AppOpenAdContext appOpenAdContext;

    private AdmobAppOpenAd(Application application, String adUnitId) {
        this.application = application;
        this.adUnitId = adUnitId;
    }

    public static void loadAd(Application application, String adUnitId, AppOpenAdShowCondition condition) {

        MobileAds.initialize(application, initializationStatus -> Log.i(LOG_TAG, " MobileAds.initialized"));

        AdmobAppOpenAd admobAppOpenAd = new AdmobAppOpenAd(application, adUnitId);
        AdCallback adCallback = new AdCallback() {
            @Override
            public void adClosed() {
                Log.d(LOG_TAG, "received adClosed callback");
                admobAppOpenAd.loadAd(this);
            }
        };
        admobAppOpenAd.appOpenAdContext = AppOpenAdContext.initialize(application, condition);
        admobAppOpenAd.loadAd(adCallback);
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        Log.d(LOG_TAG, "loading app open ad");
        appOpenAdContext.setAdCallback(adCallback);
        AppOpenAd.load(application, adUnitId,
                new AdRequest.Builder().build(),
                APP_OPEN_AD_ORIENTATION_PORTRAIT,
                appOpenAdContext);
    }

    @Override
    public void render(AdRenderer adRenderer) {
    }

    @Override
    public boolean isLoaded() {
        return appOpenAdContext != null && appOpenAdContext.isLoaded();
    }

    @Override
    public void onDestroy() {
    }
}
