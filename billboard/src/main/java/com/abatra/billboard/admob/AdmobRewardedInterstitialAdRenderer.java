package com.abatra.billboard.admob;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

import javax.annotation.Nonnull;

public interface AdmobRewardedInterstitialAdRenderer extends AdRenderer {
    void render(@Nonnull RewardedInterstitialAd rewardedInterstitialAd);
}
