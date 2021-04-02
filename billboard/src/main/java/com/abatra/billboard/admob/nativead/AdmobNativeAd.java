package com.abatra.billboard.admob.nativead;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.abatra.billboard.AdInteraction;
import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.AdResource;
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
    protected void tryLoadingAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        AdLoader.Builder builder = new AdLoader.Builder(requireContext(), id);
        Optional.ofNullable(nativeAdOptions).ifPresent(builder::withNativeAdOptions);
        builder.forNativeAd(nativeAd -> this.nativeAd = nativeAd)
                .withAdListener(new AdResourceLiveDataUpdater(liveData))
                .build()
                .loadAd(buildAdRequest(loadAdRequest));
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
    @CallSuper
    public void onDestroy() {
        getNativeAd().ifPresent(NativeAd::destroy);
        nativeAdOptions = null;
        loaded.set(false);
        super.onDestroy();
    }

    private class AdResourceLiveDataUpdater extends AdListener {

        private final MutableLiveData<AdResource> liveData;

        private AdResourceLiveDataUpdater(MutableLiveData<AdResource> liveData) {
            this.liveData = liveData;
        }

        @Override
        public void onAdFailedToLoad(LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);
            Timber.i("Failed to load native ad=%s error=%s", AdmobNativeAd.this, loadAdError.toString());
            liveData.setValue(AdResource.error(new RuntimeException(loadAdError.toString())));
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
            liveData.setValue(AdResource.interacted(AdInteraction.CLICKED));
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            liveData.setValue(AdResource.interacted(AdInteraction.CLOSED));
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            liveData.setValue(AdResource.interacted(AdInteraction.OPENED));
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            loaded.set(true);
            liveData.setValue(AdResource.loaded());
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            liveData.setValue(AdResource.interacted(AdInteraction.IMPRESSION));
        }
    }
}
