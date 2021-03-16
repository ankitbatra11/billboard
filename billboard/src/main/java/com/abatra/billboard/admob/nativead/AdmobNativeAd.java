package com.abatra.billboard.admob.nativead;

import android.content.Context;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.admob.AdmobAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import timber.log.Timber;

public class AdmobNativeAd extends AdmobAd {

    private final AtomicBoolean loaded = new AtomicBoolean(false);

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
        AdLoader.Builder builder = new AdLoader.Builder(getContext().orElseThrow(IllegalStateException::new), id);
        if (nativeAdOptions != null) {
            builder.withNativeAdOptions(nativeAdOptions);
        }
        builder.forNativeAd(nativeAd -> this.nativeAd = nativeAd);
        builder.withAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Timber.i("Failed to load native ad=%s error=%s", AdmobNativeAd.this, loadAdError.toString());
                loadAdRequest.getAdCallback().onLoadFailed(AdmobNativeAd.this);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                loadAdRequest.getAdCallback().onClicked(AdmobNativeAd.this);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAdRequest.getAdCallback().onClosed(AdmobNativeAd.this);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAdRequest.getAdCallback().onDisplayed(AdmobNativeAd.this);
            }

            @Override
            public void onAdLoaded() {
                loaded.set(true);
                super.onAdLoaded();
                loadAdRequest.getAdCallback().onLoaded(AdmobNativeAd.this);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                loadAdRequest.getAdCallback().onImpression(AdmobNativeAd.this);
            }
        }).build().loadAd(buildAdRequest(loadAdRequest));
    }

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getNativeAd().ifPresent(nativeAd -> {
            AdmobNativeAdRenderer admobNativeAdRenderer = (AdmobNativeAdRenderer) adRenderer;
            admobNativeAdRenderer.render(nativeAd);
        });
    }

    private Optional<NativeAd> getNativeAd() {
        return Optional.ofNullable(nativeAd);
    }

    @Override
    protected void destroyState() {
        getNativeAd().ifPresent(NativeAd::destroy);
        loaded.set(false);
        super.destroyState();
    }

    @Override
    public void onDestroy() {
        nativeAdOptions = null;
        super.onDestroy();
    }
}
