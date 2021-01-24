package com.abatra.billboard.admob;

import android.app.Activity;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import javax.annotation.Nonnull;

public class ShowAdmobInterstitialAdRenderer implements AdmobInterstitialAdRenderer {

    private final Activity activity;

    public ShowAdmobInterstitialAdRenderer(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void render(@Nonnull InterstitialAd interstitialAd) {
        interstitialAd.show(activity);
    }

}
