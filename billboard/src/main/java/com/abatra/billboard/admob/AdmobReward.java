package com.abatra.billboard.admob;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.common.base.MoreObjects;

public class AdmobReward implements Reward {

    private final RewardItem rewardItem;

    public AdmobReward(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }

    @Override
    public int getAmount() {
        return rewardItem.getAmount();
    }

    @Override
    public String getType() {
        return rewardItem.getType();
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("amount", getAmount())
                .add("type", getType())
                .toString();
    }
}
