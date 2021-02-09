package com.abatra.billboard;

import com.abatra.billboard.admob.banner.AdaptiveBannerAdCallback;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loading = new AtomicBoolean(false);

    private boolean isLoading() {
        return loading.get();
    }

    @Override
    public void loadAd(LoadAdRequest loadAdRequest) {
        if (isLoaded()) {
            loadAdRequest.getAdCallback().adLoaded();
        } else {
            if (isLoading()) {
                Timber.d("%s: ignoring load ad request as the ad is already loading", getClass().getName());
            } else {
                loading.set(true);
                AdCallbackWrapper adCallbackWrapper = new AdCallbackWrapper(loadAdRequest.getAdCallback());
                try {
                    doLoadAd(loadAdRequest.setAdCallback(adCallbackWrapper));
                } catch (Throwable error) {
                    Timber.e(error, "%s: Loading ad failed", getClass().getName());
                    adCallbackWrapper.adLoadFailed();
                }
            }
        }
    }

    protected abstract void doLoadAd(LoadAdRequest loadAdRequest);

    @Override
    public void onDestroy() {
        loading.set(false);
    }

    private class AdCallbackWrapper extends AdCallback.LogAdCallback implements AdaptiveBannerAdCallback {

        private final AdCallback adCallback;

        private AdCallbackWrapper(AdCallback adCallback) {
            this.adCallback = adCallback;
        }

        @Override
        public void adLoaded() {
            super.adLoaded();
            onAdResponse();
            adCallback.adLoaded();
        }

        @Override
        public void adLoadFailed() {
            super.adLoadFailed();
            onAdResponse();
            adCallback.adLoadFailed();
        }

        private void onAdResponse() {
            loading.set(false);
        }

        @Override
        public void adClosed() {
            super.adClosed();
            adCallback.adClosed();
        }

        @Override
        public void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
            adCallback.onAdaptiveBannerContainerMinHeightLoaded(minHeight);
        }

        @Override
        public void adClicked() {
            super.adClicked();
            adCallback.adClicked();
        }

        @Override
        public void adDisplayed() {
            super.adDisplayed();
            adCallback.adDisplayed();
        }

        @Override
        public void adFailedToShow() {
            super.adFailedToShow();
            adCallback.adFailedToShow();
        }
    }
}
