package com.abatra.billboard;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.mediation.MediationExtrasReceiver;

import java.util.HashMap;
import java.util.Map;

public class LoadAdRequest {

    @Nullable
    private AdCallback adCallback;
    private final Map<Class<? extends MediationExtrasReceiver>, Bundle> networkExtrasBundle = new HashMap<>();

    public LoadAdRequest setAdCallback(@Nullable AdCallback adCallback) {
        this.adCallback = adCallback;
        return this;
    }

    public AdCallback getAdCallback() {
        return adCallback != null ? adCallback : AdCallback.LOG;
    }

    public LoadAdRequest putNetworkExtra(Class<? extends MediationExtrasReceiver> extraReceiverClass, String param, String value) {
        Bundle bundle = networkExtrasBundle.get(extraReceiverClass);
        if (bundle == null) {
            networkExtrasBundle.put(extraReceiverClass, bundle);
        }
        bundle.putString(param, value);
        return this;
    }

    public Map<Class<? extends MediationExtrasReceiver>, Bundle> getNetworkExtrasBundle() {
        return networkExtrasBundle;
    }
}
