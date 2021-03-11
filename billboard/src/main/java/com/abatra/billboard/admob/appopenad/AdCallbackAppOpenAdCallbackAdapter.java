package com.abatra.billboard.admob.appopenad;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import timber.log.Timber;

public class AdCallbackAppOpenAdCallbackAdapter extends AppOpenAd.AppOpenAdLoadCallback {

    private final Ad ad;
    private final AdCallback adCallback;

    public AdCallbackAppOpenAdCallbackAdapter(Ad ad, AdCallback adCallback) {
        this.ad = ad;
        this.adCallback = adCallback;
    }

    @Override
    public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
        super.onAppOpenAdLoaded(appOpenAd);
        adCallback.onLoaded(ad);
    }

    @Override
    public void onAppOpenAdFailedToLoad(int i) {
        super.onAppOpenAdFailedToLoad(i);
        Timber.d("Failed to load app open ad=%s reason=%d", ad, i);
        adCallback.onLoadFailed(ad);
    }

    @Override
    public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
        super.onAppOpenAdFailedToLoad(loadAdError);
        Timber.d("Failed to load app open ad=%s error=%s", ad, loadAdError.toString());
        adCallback.onLoadFailed(ad);
    }
}
