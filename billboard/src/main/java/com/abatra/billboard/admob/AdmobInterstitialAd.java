package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdRenderer;
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
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        InterstitialAd.load(requireContext(), id, buildAdRequest(loadAdRequest), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                AdmobInterstitialAd.this.interstitialAd = interstitialAd;
                loadAdRequest.getAdCallback().onLoaded(AdmobInterstitialAd.this);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadAdRequest.getAdCallback().onLoadFailed(AdmobInterstitialAd.this);
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
    public void onDestroy() {
        getInterstitialAd().ifPresent(interstitialAd -> interstitialAd.setFullScreenContentCallback(null));
        interstitialAd = null;
        super.onDestroy();
    }
}
