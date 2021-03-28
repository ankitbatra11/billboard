package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.AdResource;
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
    protected void tryLoadingAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        MobileAds.initialize(requireContext(), initializationStatus -> withMobileAdsInitializedLoadAd(loadAdRequest, liveData));
    }

    private void withMobileAdsInitializedLoadAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        getContext().ifPresent(context -> {
            AdRequest adRequest = buildAdRequest(loadAdRequest);
            RewardedInterstitialAd.load(context, id, adRequest, new RewardedInterstitialAdLoadCallback() {

                @Override
                public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                    super.onAdLoaded(rewardedInterstitialAd);
                    AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = rewardedInterstitialAd;
                    liveData.setValue(LOADED);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdmobRewardedInterstitialAd.this.rewardedInterstitialAd = null;
                    liveData.setValue(AdResource.error(new RuntimeException(loadAdError.toString())));
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
    @CallSuper
    public void onDestroy() {
        getRewardedInterstitialAd().ifPresent(ad -> ad.setFullScreenContentCallback(null));
        rewardedInterstitialAd = null;
        super.onDestroy();
    }
}
