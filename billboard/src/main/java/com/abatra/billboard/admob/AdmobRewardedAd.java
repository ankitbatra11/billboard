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

import java.util.Optional;

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
                loadAdRequest.getAdCallback().onLoaded(AdmobRewardedAd.this);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdmobRewardedAd.this.rewardedAd = null;
                loadAdRequest.getAdCallback().onLoadFailed(AdmobRewardedAd.this);
            }
        });
    }

    @Override
    public boolean isLoaded() {
        return getRewardedAd().isPresent();
    }

    private Optional<RewardedAd> getRewardedAd() {
        return Optional.ofNullable(rewardedAd);
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getRewardedAd().ifPresent(rewardedAd -> {
            AdmobRewardedAdRenderer renderer = (AdmobRewardedAdRenderer) adRenderer;
            renderer.render(rewardedAd);
        });
    }

    @Override
    public Optional<Reward> getReward() {
        return getRewardedAd()
                .flatMap(ad -> Optional.ofNullable(ad.getRewardItem()))
                .map(AdmobReward::new);
    }

    @Override
    public void onDestroy() {
        getRewardedAd().ifPresent(rewardedAd -> rewardedAd.setFullScreenContentCallback(null));
        rewardedAd = null;
        super.onDestroy();
    }
}
