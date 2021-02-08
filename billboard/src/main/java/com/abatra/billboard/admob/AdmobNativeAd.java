package com.abatra.billboard.admob;

import android.content.Context;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import javax.annotation.Nullable;

import timber.log.Timber;

public class AdmobNativeAd extends AdmobAd {

    @Nullable
    private NativeAdOptions nativeAdOptions;

    @Nullable
    private NativeAd nativeAd;

    public AdmobNativeAd(Context context, String id) {
        super(context, id);
    }

    public AdmobNativeAd setNativeAdOptions(@Nullable NativeAdOptions nativeAdOptions) {
        this.nativeAdOptions = nativeAdOptions;
        return this;
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        AdLoader.Builder builder = new AdLoader.Builder(context, id);
        if (nativeAdOptions != null) {
            builder.withNativeAdOptions(nativeAdOptions);
        }
        builder.forNativeAd(nativeAd ->
        {
            Timber.d("adLoaded!");
            this.nativeAd = nativeAd;
            loadAdRequest.getAdCallback().adLoaded();

        }).withAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Timber.i("loadAdError=%s", loadAdError.toString());
                loadAdRequest.getAdCallback().adLoadFailed();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                loadAdRequest.getAdCallback().adClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAdRequest.getAdCallback().adClosed();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAdRequest.getAdCallback().adDisplayed();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loadAdRequest.getAdCallback().adLoaded();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                loadAdRequest.getAdCallback().onAdImpression();
            }
        }).build().loadAd(buildAdRequest(loadAdRequest));
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (nativeAd != null) {
            AdmobNativeAdRenderer admobNativeAdRenderer = (AdmobNativeAdRenderer) adRenderer;
            admobNativeAdRenderer.render(nativeAd);
        }
    }

    @Override
    public void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
            nativeAd = null;
        }
        nativeAdOptions = null;
        super.onDestroy();
    }
}
