package com.abatra.billboard;

import androidx.annotation.Nullable;

abstract public class LoadAdRequest {

    @Nullable
    private AdCallback adCallback;

    public LoadAdRequest setAdCallback(@Nullable AdCallback adCallback) {
        this.adCallback = adCallback;
        return this;
    }

    public AdCallback getAdCallback() {
        return adCallback != null ? adCallback : AdCallback.LOG;
    }
}
