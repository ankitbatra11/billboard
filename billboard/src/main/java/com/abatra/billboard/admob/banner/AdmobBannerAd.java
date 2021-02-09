package com.abatra.billboard.admob.banner;

import android.content.Context;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.admob.AdListenerAdapter;
import com.abatra.billboard.admob.AdmobAd;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.common.base.Supplier;

import javax.annotation.Nullable;

public class AdmobBannerAd extends AdmobAd {

    private final Supplier<AdSize> adSizeSupplier;
    @Nullable
    private AdView adView;

    protected AdmobBannerAd(Context context, String id, Supplier<AdSize> adSizeSupplier) {
        super(context, id);
        this.adSizeSupplier = adSizeSupplier;
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        adView = new AdView(context);
        adView.setAdUnitId(id);
        adView.setAdListener(new AdListenerAdapter(loadAdRequest.getAdCallback()));
        adView.setAdSize(adSizeSupplier.get());
        adView.loadAd(buildAdRequest(loadAdRequest));
    }

    @Override
    public void render(AdRenderer adRenderer) {
        if (adView != null) {
            AdmobBannerAdRenderer admobBannerAdRenderer = (AdmobBannerAdRenderer) adRenderer;
            admobBannerAdRenderer.render(adView);
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
        super.onDestroy();
    }
}
