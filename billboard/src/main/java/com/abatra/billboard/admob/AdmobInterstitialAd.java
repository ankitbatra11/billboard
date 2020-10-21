package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.Nullable;

import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.InterstitialAd;

public class AdmobInterstitialAd extends AdmobAd {

    @Nullable
    private InterstitialAd interstitialAd;

    protected AdmobInterstitialAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(id);
        interstitialAd.setAdListener(new AdListenerAdapter(adCallback));
    }

    @Override
    public boolean isLoaded() {
        return interstitialAd != null && interstitialAd.isLoaded();
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (interstitialAd != null) {
            AdmobInterstitialAdRenderer interstitialAdRenderer = (AdmobInterstitialAdRenderer) adRenderer;
            interstitialAdRenderer.render(interstitialAd);
        }
    }

    @Override
    public void onDestroy() {
        interstitialAd = null;
        super.onDestroy();
    }
}
