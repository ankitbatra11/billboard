package com.abatra.billboard.admob;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.nativead.NativeAd;

import javax.annotation.Nonnull;

public interface AdmobNativeAdRenderer extends AdRenderer {

    void render(@Nonnull NativeAd nativeAd);

    class Builder {

        public Builder() {
        }
    }
}
