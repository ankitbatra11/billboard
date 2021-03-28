package com.abatra.billboard.admob.banner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.abatra.billboard.AdInteraction;
import com.abatra.billboard.AdRenderer;
import com.abatra.billboard.AdResource;
import com.abatra.billboard.LoadAdRequest;
import com.abatra.billboard.admob.AdmobAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.common.base.Supplier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import static com.abatra.android.wheelie.thread.BoltsUtils.getResult;
import static com.abatra.android.wheelie.thread.SaferTask.backgroundTask;

public class AdmobBannerAd extends AdmobAd {

    private final Supplier<AdSize> adSizeSupplier;
    private final AtomicBoolean loaded = new AtomicBoolean(false);
    @Nullable
    private AdView adView;

    public AdmobBannerAd(Context context, String id, Supplier<AdSize> adSizeSupplier) {
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
    protected void tryLoadingAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        backgroundTask(adSizeSupplier::get).continueOnUiThread(task -> {
            getResult(task).ifPresent(adSize -> loadBannerAd(adSize, loadAdRequest, liveData));
            return null;
        });
    }

    private void loadBannerAd(AdSize adSize, LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData) {
        adView = new AdView(requireContext());
        adView.setAdUnitId(id);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loaded.set(true);
                liveData.setValue(LOADED);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                liveData.setValue(AdResource.interacted(AdInteraction.CLOSED));
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                liveData.setValue(AdResource.error(new RuntimeException(loadAdError.toString())));
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                liveData.setValue(AdResource.interacted(AdInteraction.OPENED));
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                liveData.setValue(AdResource.interacted(AdInteraction.CLICKED));
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                liveData.setValue(AdResource.interacted(AdInteraction.IMPRESSION));
            }
        });
        adView.setAdSize(adSize);
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
    @CallSuper
    public void onDestroy() {
        getAdView().ifPresent(adView -> {
            adView.setAdListener(null);
            adView.destroy();
        });
        adView = null;
        loaded.set(false);
        super.onDestroy();
    }
}
