package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

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
                loadAdRequest.getAdCallback().adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadAdRequest.getAdCallback().adLoadFailed();
            }
        });
    }

    @Override
    public boolean isLoaded() {
        return interstitialAd != null;
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (interstitialAd != null) {
            AdmobInterstitialAdRenderer interstitialAdRenderer = (AdmobInterstitialAdRenderer) adRenderer;
            interstitialAdRenderer.render(interstitialAd);
        }
    }

    @Override
    public void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(null);
            interstitialAd = null;
        }
        super.onDestroy();
    }
}
