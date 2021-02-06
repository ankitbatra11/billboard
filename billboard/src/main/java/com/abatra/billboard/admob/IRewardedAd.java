package com.abatra.billboard.admob;

import androidx.annotation.Nullable;

import com.abatra.billboard.Ad;

public interface IRewardedAd extends Ad {
    @Nullable
    Reward getReward();
}
