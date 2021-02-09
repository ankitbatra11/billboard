package com.abatra.billboard.admob.banner;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import javax.annotation.Nonnull;

public interface AdmobBannerAdRenderer extends AdRenderer {
    void render(@Nonnull AdView adView);
}
