package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.IRewardedAd;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.Reward;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

import java.util.Optional;

public class AdmobRewardedInterstitialAd extends AdmobAd implements IRewardedAd {

    @Nullable
    private RewardedInterstitialAd rewardedInterstitialAd;

    public AdmobRewardedInterstitialAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        MobileAds.initialize(requireContext(), initializationStatus -> withMobileAdsInitializedLoadAd(loadAdRequest));
    }

    private void withMobileAdsInitializedLoadAd(LoadAdRequest loadAdRequest) {
        getContext().ifPresent(context -> {
            AdRequest adRequest = buildAdRequest(loadAdRequest);
            RewardedInterstitialAd.load(context, id, adRequest, new RewardedInterstitialAdLoadCallback() {

                @Override
                public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                    super.onAdLoaded(rewardedInterstitialAd);
                    AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = rewardedInterstitialAd;
                    loadAdRequest.getAdCallback().onLoaded(AdmobRewardedInterstitialAd.this);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = null;
                    loadAdRequest.getAdCallback().onLoadFailed(AdmobRewardedInterstitialAd.this);
                }
            });
        });
    }

    @Override
    public boolean isLoaded() {
        return getRewardedInterstitialAd().isPresent();
    }

    private Optional<RewardedInterstitialAd> getRewardedInterstitialAd() {
        return Optional.ofNullable(rewardedInterstitialAd);
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getRewardedInterstitialAd().ifPresent(rewardedInterstitialAd -> {
            AdmobRewardedInterstitialAdRenderer renderer = (AdmobRewardedInterstitialAdRenderer) adRenderer;
            renderer.render(rewardedInterstitialAd);
        });
    }

    @Nullable
    @Override
    public Optional<Reward> getReward() {
        return getRewardedInterstitialAd()
                .map(RewardedInterstitialAd::getRewardItem)
                .map(AdmobReward::new);
    }

    @Override
    protected void destroyState() {
        getRewardedInterstitialAd().ifPresent(ad -> ad.setFullScreenContentCallback(null));
        rewardedInterstitialAd = null;
        super.destroyState();
    }
}
