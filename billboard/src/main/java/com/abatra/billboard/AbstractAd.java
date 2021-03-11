package com.abatra.billboard;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AdCallbackDelegate adCallbackDelegate = new AdCallbackDelegate();
    @Nullable
    private AdCallback requestAdCallback;

    @Override
    public void loadAd(LoadAdRequest loadAdRequest) {
        if (isLoaded()) {
            Timber.d("ad=%s is already loaded", toString());
            loadAdRequest.getAdCallback().onLoaded(this);
        } else {
            if (isLoading()) {
                Timber.d("ignoring load ad request as the ad=%s is already loading", toString());
            } else {
                Timber.d("%s: loading ad", getClass().getSimpleName());
                loading.set(true);
                requestAdCallback = loadAdRequest.getAdCallback();
                try {
                    doLoadAd(loadAdRequest.setAdCallback(adCallbackDelegate));
                } catch (Throwable error) {
                    Timber.e(error, "failed to load ad=%s", toString());
                    adCallbackDelegate.onLoadFailed(this);
                }
            }
        }
    }

    private boolean isLoading() {
        return loading.get();
    }

    protected abstract void doLoadAd(LoadAdRequest loadAdRequest);

    @Override
    @CallSuper
    public void onDestroy() {
        requestAdCallback = null;
        loading.set(false);
    }

    private class AdCallbackDelegate extends AdCallback.LogAdCallback implements AdCallback {

        @Override
        public void onLoaded(Ad ad) {
            super.onLoaded(ad);
            onAdResponse();
            getAdCallback().ifPresent(adCallback -> adCallback.onLoaded(AbstractAd.this));
        }

        private Optional<AdCallback> getAdCallback() {
            return Optional.ofNullable(requestAdCallback);
        }

        @Override
        public void onLoadFailed(Ad ad) {
            super.onLoadFailed(ad);
            onAdResponse();
            getAdCallback().ifPresent(adCallback -> adCallback.onLoadFailed(AbstractAd.this));
        }

        private void onAdResponse() {
            loading.set(false);
        }

        @Override
        public void onClosed(Ad ad) {
            super.onClosed(ad);
            getAdCallback().ifPresent(adCallback -> adCallback.onClosed(AbstractAd.this));
        }

        @Override
        public void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
            getAdCallback().ifPresent(ac -> ac.onAdaptiveBannerContainerMinHeightLoaded(minHeight));
        }

        @Override
        public void onClicked(Ad ad) {
            super.onClicked(ad);
            getAdCallback().ifPresent(adCallback -> adCallback.onClicked(AbstractAd.this));
        }

        @Override
        public void onDisplayed(Ad ad) {
            super.onDisplayed(ad);
            getAdCallback().ifPresent(adCallback -> adCallback.onDisplayed(AbstractAd.this));
        }

        @Override
        public void onFailedToShow(Ad ad, Throwable error) {
            super.onFailedToShow(ad, error);
            getAdCallback().ifPresent(adCallback -> adCallback.onFailedToShow(AbstractAd.this, error));
        }

        @Override
        public void onImpression(Ad ad) {
            super.onImpression(ad);
            getAdCallback().ifPresent(adCallback -> adCallback.onImpression(AbstractAd.this));
        }
    }
}
