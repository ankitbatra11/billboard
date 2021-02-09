package com.abatra.billboard;

import com.abatra.billboard.admob.AdmobLoadAdRequest;
import com.abatra.billboard.admob.banner.AdaptiveBannerAdCallback;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void loadAd(AdCallback adCallback) {
        loadAd(new AdmobLoadAdRequest().setAdCallback(adCallback));
    }

    @Override
    public void forceLoadAd(AdCallback adCallback) {
        loaded.set(false);
        loading.set(false);
        loadAd(adCallback);
    }

    private boolean isLoading() {
        return loading.get();
    }

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }

    @Override
    public void loadAd(LoadAdRequest loadAdRequest) {
        if (!isLoaded() && !isLoading()) {
            loading.set(true);
            doLoadAd(loadAdRequest.setAdCallback(new AdCallbackWrapper(loadAdRequest.getAdCallback())));
        } else {
            Timber.d("Not loading ad. loaded=" + isLoaded() + " loading=" + isLoading());
        }
    }

    protected abstract void doLoadAd(LoadAdRequest loadAdRequest);

    private class AdCallbackWrapper extends AdCallback.LogAdCallback implements AdaptiveBannerAdCallback {

        private final AdCallback adCallback;

        private AdCallbackWrapper(AdCallback adCallback) {
            this.adCallback = adCallback;
        }

        @Override
        public void adLoaded() {
            super.adLoaded();
            onAdResponse();
            loaded.set(true);
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
