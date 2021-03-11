package com.abatra.billboard;

import androidx.annotation.Nullable;

import java.util.Optional;

public interface IRewardedAd extends Ad {
    @Nullable
    Optional<Reward> getReward();
}
