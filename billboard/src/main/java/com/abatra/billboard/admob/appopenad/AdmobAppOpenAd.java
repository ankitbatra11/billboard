package com.abatra.billboard.admob.appopenad;

import android.app.Application;
import android.util.Log;

import com.abatra.billboard.AbstractAd;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

import javax.annotation.Nullable;

import static com.google.android.gms.ads.appopen.AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT;

public class AdmobAppOpenAd extends AbstractAd {

    private static final String LOG_TAG = "AdmobAppOpenAd";

    private final Application application;
    private final String adUnitId;
    private AppOpenAdDisplayer appOpenAdDisplayer;

    @Nullable
    private AppOpenAdReceiver appOpenAdReceiver;

    private AdmobAppOpenAd(Application application, String adUnitId) {
        this.application = application;
        this.adUnitId = adUnitId;
    }

    public static AdmobAppOpenAd create(Application application, String adUnitId) {

        MobileAds.initialize(application, initializationStatus -> Log.i(LOG_TAG, " MobileAds.initialized"));

        AdmobAppOpenAd admobAppOpenAd = new AdmobAppOpenAd(application, adUnitId);
        admobAppOpenAd.appOpenAdDisplayer = AppOpenAdDisplayer.create(application, admobAppOpenAd);
        return admobAppOpenAd;
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        appOpenAdReceiver = new AppOpenAdReceiver(adCallback);
        AppOpenAd.load(application, adUnitId, new AdRequest.Builder().build(), APP_OPEN_AD_ORIENTATION_PORTRAIT, appOpenAdReceiver);
    }

    @Override
    public void render(AdRenderer adRenderer) {
        AdmobAppOpenAdRender admobAppOpenAdRender = (AdmobAppOpenAdRender) adRenderer;
        admobAppOpenAdRender.render(appOpenAdReceiver.getAppOpenAd(), appOpenAdDisplayer);
    }

    @Override
    public boolean isLoaded() {
        return appOpenAdReceiver != null && appOpenAdReceiver.isLoaded();
    }

    @Override
    public void onDestroy() {
        if (appOpenAdReceiver != null) {
            appOpenAdReceiver.destroy();
            appOpenAdReceiver = null;
        }
    }
}
