package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.IRewardedAd;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.Reward;
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
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        MobileAds.initialize(context, initializationStatus -> withMobileAdsInitializedLoadAd(loadAdRequest));
    }

    private void withMobileAdsInitializedLoadAd(LoadAdRequest loadAdRequest) {
        RewardedInterstitialAd.load(context, id, buildAdRequest(loadAdRequest), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = rewardedInterstitialAd;
                loadAdRequest.getAdCallback().adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = null;
                loadAdRequest.getAdCallback().adLoadFailed();
            }
        });
    }

    @Override
    public boolean isLoaded() {
        return rewardedInterstitialAd != null;
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
