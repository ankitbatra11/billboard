package com.abatra.billboard.admob.appopenad;

import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AdCallbackAppOpenAdCallbackAdapter extends AppOpenAd.AppOpenAdLoadCallback {

    private final AdCallback adCallback;

    public AdCallbackAppOpenAdCallbackAdapter(AdCallback adCallback) {
        this.adCallback = adCallback;
    }

    @Override
    public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
        super.onAppOpenAdLoaded(appOpenAd);
        adCallback.adLoaded();
    }

    @Override
    public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
        super.onAppOpenAdFailedToLoad(loadAdError);
        adCallback.adLoadFailed();
    }
}
