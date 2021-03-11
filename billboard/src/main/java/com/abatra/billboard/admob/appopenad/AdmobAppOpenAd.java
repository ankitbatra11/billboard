package com.abatra.billboard.admob.appopenad;

import android.app.Application;

import com.abatra.billboard.AbstractAd;
import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Optional;

import javax.annotation.Nullable;

import timber.log.Timber;

import static com.google.android.gms.ads.appopen.AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT;

public class AdmobAppOpenAd extends AbstractAd {

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

        MobileAds.initialize(application, initializationStatus -> Timber.i(" MobileAds.initialized"));

        AdmobAppOpenAd admobAppOpenAd = new AdmobAppOpenAd(application, adUnitId);
        admobAppOpenAd.appOpenAdDisplayer = AppOpenAdDisplayer.create(application, admobAppOpenAd);
        return admobAppOpenAd;
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        appOpenAdReceiver = new AppOpenAdReceiver(this, loadAdRequest.getAdCallback());
        AppOpenAd.load(application, adUnitId, new AdRequest.Builder().build(), APP_OPEN_AD_ORIENTATION_PORTRAIT, appOpenAdReceiver);
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getAppOpenAdReceiver().ifPresent(receiver -> {
            AdmobAppOpenAdRender admobAppOpenAdRender = (AdmobAppOpenAdRender) adRenderer;
            admobAppOpenAdRender.render(receiver.getAppOpenAd(), appOpenAdDisplayer);
        });
    }

    private Optional<AppOpenAdReceiver> getAppOpenAdReceiver() {
        return Optional.ofNullable(appOpenAdReceiver);
    }

    @Override
    public boolean isLoaded() {
        return getAppOpenAdReceiver().map(AppOpenAdReceiver::isLoaded).orElse(false);
    }

    @Override
    public void onDestroy() {
        getAppOpenAdReceiver().ifPresent(AppOpenAdReceiver::destroy);
        appOpenAdReceiver = null;
    }
}
