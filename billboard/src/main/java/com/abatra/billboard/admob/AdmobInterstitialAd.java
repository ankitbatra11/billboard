package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.AdResource;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Optional;

public class AdmobInterstitialAd extends AdmobAd {

    @Nullable
    private InterstitialAd interstitialAd;

    public AdmobInterstitialAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void tryLoadingAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        InterstitialAd.load(requireContext(), id, buildAdRequest(loadAdRequest), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                AdmobInterstitialAd.this.interstitialAd = interstitialAd;
                liveData.setValue(LOADED);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                liveData.setValue(AdResource.error(new RuntimeException(loadAdError.toString())));
            }
        });
    }

    @Override
    public boolean isLoaded() {
        return getInterstitialAd().isPresent();
    }

    private Optional<InterstitialAd> getInterstitialAd() {
        return Optional.ofNullable(interstitialAd);
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getInterstitialAd().ifPresent(interstitialAd -> {
            AdmobInterstitialAdRenderer interstitialAdRenderer = (AdmobInterstitialAdRenderer) adRenderer;
            interstitialAdRenderer.render(interstitialAd);
        });
    }

    @Override
    @CallSuper
    public void onDestroy() {
        getInterstitialAd().ifPresent(interstitialAd -> interstitialAd.setFullScreenContentCallback(null));
        interstitialAd = null;
        super.onDestroy();
    }
}
