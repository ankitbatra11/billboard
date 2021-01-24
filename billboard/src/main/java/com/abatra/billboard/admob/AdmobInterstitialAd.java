package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
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
    protected void doLoadAd(AdCallback adCallback) {
        InterstitialAd.load(context, id, buildAdRequest(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                AdmobInterstitialAd.this.interstitialAd = interstitialAd;
                adCallback.adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adCallback.adLoadFailed();
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
        interstitialAd = null;
        super.onDestroy();
    }
}
