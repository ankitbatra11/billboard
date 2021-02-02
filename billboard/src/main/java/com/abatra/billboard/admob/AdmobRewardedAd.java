package com.abatra.billboard.admob;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AdCallback;
import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdmobRewardedAd extends AdmobAd {

    @Nullable
    private RewardedAd rewardedAd;

    public AdmobRewardedAd(Context context, String id) {
        super(context, id);
    }

    @Override
    protected void doLoadAd(AdCallback adCallback) {
        RewardedAd.load(context, id, buildAdRequest(), new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                AdmobRewardedAd.this.rewardedAd = rewardedAd;
                adCallback.adLoaded();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                AdmobRewardedAd.this.rewardedAd = null;
                adCallback.adLoadFailed();
            }
        });
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (rewardedAd != null) {
            AdmobRewardedAdRenderer renderer = (AdmobRewardedAdRenderer) adRenderer;
            renderer.render(rewardedAd);
        }
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
