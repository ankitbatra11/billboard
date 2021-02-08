package com.abatra.billboard;

import androidx.annotation.Nullable;

public interface IRewardedAd extends Ad {
    @Nullable
    Reward getReward();
}
