package com.abatra.billboard;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void loadAd(AdCallback adCallback) {
        if (!isLoaded() && !isLoading()) {
            loading.set(true);
            doLoadAd(new AdCallbackWrapper(adCallback));
        } else {
            Timber.d("Not loading ad. loaded=" + isLoaded() + " loading=" + isLoading());
        }
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

    protected abstract void doLoadAd(AdCallback adCallback);

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }

    private class AdCallbackWrapper extends AdCallback.LogAdCallback {

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
    }
}
