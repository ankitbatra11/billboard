package com.abatra.billboard.admob;

import com.google.android.gms.ads.InterstitialAd;

import javax.annotation.Nonnull;

public class ShowAdmobInterstitialAdRenderer implements AdmobInterstitialAdRenderer {

    private static final ShowAdmobInterstitialAdRenderer INSTANCE = new ShowAdmobInterstitialAdRenderer();

    private ShowAdmobInterstitialAdRenderer() {
    }

    public static ShowAdmobInterstitialAdRenderer getInstance() {
        return INSTANCE;
    }

    @Override
    public void render(@Nonnull InterstitialAd interstitialAd) {
        interstitialAd.show();
    }
}
