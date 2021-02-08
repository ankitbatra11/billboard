package com.abatra.billboard.admob;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.common.base.Supplier;

import javax.annotation.Nullable;

public class AdmobBannerAd extends AdmobAd {

    private final Supplier<AdSize> adSizeSupplier;

    @Nullable
    private AdView adView;

    private AdmobBannerAd(Context context, String id, Supplier<AdSize> adSizeSupplier) {
        super(context, id);
        this.adSizeSupplier = adSizeSupplier;
    }

    public static AdmobBannerAd adaptive(Context context, String id) {
        return new AdmobBannerAd(context, id, () -> adaptiveAdSize(context));
    }

    private static AdSize adaptiveAdSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthByDensity = (int) (outMetrics.widthPixels / outMetrics.density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, widthByDensity);
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
