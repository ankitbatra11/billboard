package com.abatra.billboard.admob;

import android.content.Context;
import android.os.Bundle;

import com.abatra.billboard.AbstractAd;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationExtrasReceiver;

import java.util.Map;
import java.util.Set;

abstract public class AdmobAd extends AbstractAd {

    protected Context context;
    protected final String id;

    protected AdmobAd(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public void onDestroy() {
        context = null;
    }

    protected AdRequest buildAdRequest(LoadAdRequest loadAdRequest) {
        AdRequest.Builder builder = new AdRequest.Builder();
        AdmobLoadAdRequest admobLoadAdRequest = (AdmobLoadAdRequest) loadAdRequest;
        Set<Map.Entry<Class<? extends MediationExtrasReceiver>, Bundle>> entries = admobLoadAdRequest.getNetworkExtrasBundle().entrySet();
        for (Map.Entry<Class<? extends MediationExtrasReceiver>, Bundle> extrasByAdapter : entries) {
            builder.addNetworkExtrasBundle(extrasByAdapter.getKey(), extrasByAdapter.getValue());
        }
        return builder.build();
    }
}
