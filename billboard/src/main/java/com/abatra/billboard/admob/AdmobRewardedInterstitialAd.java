package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class AdmobRewardedInterstitialAd extends AdmobAd implements IRewardedAd {

    @Nullable
    private RewardedInterstitialAd rewardedInterstitialAd;

    public AdmobRewardedInterstitialAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        MobileAds.initialize(context, initializationStatus -> withMobileAdsInitializedLoadAd(adCallback));
    }

    private void withMobileAdsInitializedLoadAd(AdCallback adCallback) {
        RewardedInterstitialAd.load(context, id, buildAdRequest(), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = rewardedInterstitialAd;
                adCallback.adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = null;
                adCallback.adLoadFailed();
            }
        });
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (rewardedInterstitialAd != null) {
            AdmobRewardedInterstitialAdRenderer renderer = (AdmobRewardedInterstitialAdRenderer) adRenderer;
            renderer.render(rewardedInterstitialAd);
        }
    }

    @Override
    public void onDestroy() {
        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd.setFullScreenContentCallback(null);
            rewardedInterstitialAd = null;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public Reward getReward() {
        Reward reward = null;
        if (rewardedInterstitialAd != null && rewardedInterstitialAd.getRewardItem() != null) {
            reward = new AdmobReward(rewardedInterstitialAd.getRewardItem());
        }
        return reward;
    }
}
