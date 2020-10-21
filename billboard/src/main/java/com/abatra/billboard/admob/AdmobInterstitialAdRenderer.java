package com.abatra.billboard.admob;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.InterstitialAd;

import javax.annotation.Nonnull;

public interface AdmobInterstitialAdRenderer extends AdRenderer {
    void render(@Nonnull InterstitialAd interstitialAd);
}
