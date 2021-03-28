package com.abatra.billboard.admob;

import android.os.Bundle;

import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.mediation.MediationExtrasReceiver;

import java.util.HashMap;
import java.util.Map;

public class AdmobLoadAdRequest implements LoadAdRequest {

    public static final AdmobLoadAdRequest EMPTY = new AdmobLoadAdRequest();

    private final Map<Class<? extends MediationExtrasReceiver>, Bundle> networkExtrasBundle = new HashMap<>();

    public AdmobLoadAdRequest putNetworkExtra(Class<? extends MediationExtrasReceiver> extraReceiverClass,
                                              String param, String value) {
        Bundle bundle = networkExtrasBundle.get(extraReceiverClass);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(param, value);
        networkExtrasBundle.put(extraReceiverClass, bundle);
        return this;
    }

    public Map<Class<? extends MediationExtrasReceiver>, Bundle> getNetworkExtrasBundle() {
        return networkExtrasBundle;
    }
}
