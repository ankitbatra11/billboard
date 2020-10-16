package com.abatra.billboard.admob;

import android.content.Context;

import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import javax.annotation.Nullable;

public class AdmobNativeAd extends AdmobAd {

    @Nullable
    private NativeAdOptions nativeAdOptions;

    @Nullable
    private UnifiedNativeAd unifiedNativeAd;

    public AdmobNativeAd(Context context, String id) {
        super(context, id);
    }

    public AdmobNativeAd setNativeAdOptions(@Nullable NativeAdOptions nativeAdOptions) {
        this.nativeAdOptions = nativeAdOptions;
        return this;
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        AdLoader.Builder builder = new AdLoader.Builder(context, id);
        if (nativeAdOptions != null) {
            builder.withNativeAdOptions(nativeAdOptions);
        }
        builder.forUnifiedNativeAd(unifiedNativeAd ->
        {
            this.unifiedNativeAd = unifiedNativeAd;
            adCallback.adLoaded();

        }).withAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adCallback.adLoadFailed();
            }

        }).build().loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (unifiedNativeAd != null) {
            AdmobNativeAdRenderer admobNativeAdRenderer = (AdmobNativeAdRenderer) adRenderer;
            admobNativeAdRenderer.render(unifiedNativeAd);
        }
    }

    @Override
    public void onDestroy() {
        if (unifiedNativeAd != null) {
            unifiedNativeAd.destroy();
            unifiedNativeAd = null;
        }
        nativeAdOptions = null;
        super.onDestroy();
    }
}
