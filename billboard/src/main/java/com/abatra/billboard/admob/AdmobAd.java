package com.abatra.billboard.admob;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abatra.billboard.AbstractAd;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationExtrasReceiver;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

abstract public class AdmobAd extends AbstractAd {

    @Nullable
    Context context;
    protected final String id;

    protected AdmobAd(@NonNull Context context, String id) {
        this.context = context;
        this.id = id;
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

    protected Optional<Context> getContext() {
        return Optional.ofNullable(context);
    }

    protected Context requireContext() {
        return getContext().orElseThrow(IllegalStateException::new);
    }

    @Override
    @CallSuper
    public void onDestroy() {
        context = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "AdmobAd{" +
                "class=" + getClass().getSimpleName() +
                ", id='" + id + '\'' +
                '}';
    }
}
