package com.abatra.billboard.admob;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

public class FullScreenContentCallbackAdapter extends FullScreenContentCallback {

    private final Ad ad;
    private final AdCallback adCallback;

    public FullScreenContentCallbackAdapter(Ad ad, AdCallback adCallback) {
        this.ad = ad;
        this.adCallback = adCallback;
    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError adError) {
        super.onAdFailedToShowFullScreenContent(adError);
        adCallback.onFailedToShow(ad, new RuntimeException("Failed to show ad due to error=" + adError.toString()));
    }

    @Override
    public void onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent();
        adCallback.onDisplayed(ad);
    }

    @Override
    public void onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent();
        adCallback.onClosed(ad);
    }
}
