package com.abatra.billboard;

import androidx.annotation.Nullable;

import java.util.Optional;

abstract public class LoadAdRequest {

    @Nullable
    private AdCallback adCallback;

    public LoadAdRequest setAdCallback(@Nullable AdCallback adCallback) {
        this.adCallback = adCallback;
        return this;
    }

    public AdCallback getAdCallback() {
        return Optional.ofNullable(adCallback).orElse(AdCallback.LOG);
    }
}
