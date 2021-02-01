package com.abatra.billboard.admob;

import android.content.Context;

import com.abatra.billboard.AbstractAd;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;

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

    protected AdRequest buildAdRequest() {
        return new AdRequest.Builder().build();
    }
}
