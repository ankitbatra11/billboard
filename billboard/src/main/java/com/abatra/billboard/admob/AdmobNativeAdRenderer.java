package com.abatra.billboard.admob;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import javax.annotation.Nonnull;

public interface AdmobNativeAdRenderer extends AdRenderer {
    void render(@Nonnull UnifiedNativeAd unifiedNativeAd);
}
