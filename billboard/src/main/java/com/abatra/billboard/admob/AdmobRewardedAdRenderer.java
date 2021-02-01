package com.abatra.billboard.admob;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.rewarded.RewardedAd;

import javax.annotation.Nonnull;

public interface AdmobRewardedAdRenderer extends AdRenderer {
    void render(@Nonnull RewardedAd rewardedAd);
}
