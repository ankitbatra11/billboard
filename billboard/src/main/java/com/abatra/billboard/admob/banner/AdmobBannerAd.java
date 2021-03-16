package com.abatra.billboard.admob.banner;

import android.content.Context;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.admob.AdListenerAdapter;
import com.abatra.billboard.admob.AdmobAd;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.common.base.Supplier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

public class AdmobBannerAd extends AdmobAd {

    private final Supplier<AdSize> adSizeSupplier;
    private final AtomicBoolean loaded = new AtomicBoolean(false);
    @Nullable
    private AdView adView;

    protected AdmobBannerAd(Context context, String id, Supplier<AdSize> adSizeSupplier) {
        super(context, id);
        this.adSizeSupplier = adSizeSupplier;
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        adView = new AdView(requireContext());
        adView.setAdUnitId(id);
        adView.setAdListener(new AdListenerAdapter(this, loadAdRequest.getAdCallback()) {
            @Override
            public void onAdLoaded() {
                loaded.set(true);
                super.onAdLoaded();
            }
        });
        adView.setAdSize(adSizeSupplier.get());
        adView.loadAd(buildAdRequest(loadAdRequest));
    }

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }

    @Override
    public void render(AdRenderer adRenderer) {
        getAdView().ifPresent(adView -> {
            AdmobBannerAdRenderer admobBannerAdRenderer = (AdmobBannerAdRenderer) adRenderer;
            admobBannerAdRenderer.render(adView);
        });
    }

    private Optional<AdView> getAdView() {
        return Optional.ofNullable(adView);
    }

    @Override
    protected void destroyState() {
        getAdView().ifPresent(adView -> {
            adView.setAdListener(null);
            adView.destroy();
        });
        adView = null;
        loaded.set(false);
        super.destroyState();
    }
}
