package com.abatra.billboard.admob;

import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;

public class AdListenerAdapter extends AdListener {

    private final AdCallback adCallback;

    public AdListenerAdapter(AdCallback adCallback) {
        this.adCallback = adCallback;
    }

    @Override
    public void onAdFailedToLoad(LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        adCallback.adLoadFailed();
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        adCallback.adClosed();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        adCallback.adLoaded();
    }
}
