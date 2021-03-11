package com.abatra.billboard.admob;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;

import timber.log.Timber;

public class AdListenerAdapter extends AdListener {

    private final Ad ad;
    private final AdCallback adCallback;

    public AdListenerAdapter(Ad ad, AdCallback adCallback) {
        this.ad = ad;
        this.adCallback = adCallback;
    }

    @Override
    public void onAdFailedToLoad(LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        Timber.i("ad=%s load failed due to error=%s", ad, loadAdError.toString());
        adCallback.onLoadFailed(ad);
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        adCallback.onClosed(ad);
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        adCallback.onLoaded(ad);
    }

    @Override
    public void onAdClicked() {
        super.onAdClicked();
        adCallback.onClicked(ad);
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
        adCallback.onDisplayed(ad);
    }

    @Override
    public void onAdImpression() {
        super.onAdImpression();
        adCallback.onImpression(ad);
    }
}
