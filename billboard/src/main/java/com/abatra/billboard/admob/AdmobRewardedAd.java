package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.IRewardedAd;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.Reward;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdmobRewardedAd extends AdmobAd implements IRewardedAd {

    @Nullable
    private RewardedAd rewardedAd;

    public AdmobRewardedAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        RewardedAd.load(requireContext(), id, buildAdRequest(loadAdRequest), new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                AdmobRewardedAd.this.rewardedAd = rewardedAd;
                loadAdRequest.getAdCallback().adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdmobRewardedAd.this.rewardedAd = null;
                loadAdRequest.getAdCallback().adLoadFailed();
            }
        });
    }

    @Override
    public boolean isLoaded() {
        return rewardedAd != null;
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (rewardedAd != null) {
            AdmobRewardedAdRenderer renderer = (AdmobRewardedAdRenderer) adRenderer;
            renderer.render(rewardedAd);
        }
    }

    @Override
    public Reward getReward() {
        return rewardedAd != null
                ? rewardedAd.getRewardItem() != null ? new AdmobReward(rewardedAd.getRewardItem()) : null
                : null;

    }

    @Override
    public void onDestroy() {
        if (rewardedAd != null) {
            rewardedAd.setFullScreenContentCallback(null);
            rewardedAd = null;
        }
        super.onDestroy();
    }
}
