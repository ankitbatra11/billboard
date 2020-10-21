package com.abatra.billboard;

import java.util.concurrent.atomic.AtomicBoolean;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void loadAd(AdCallback adCallback) {
        if (!isLoaded() && !isLoading()) {
            loading.set(true);
            doLoadAd(new AdCallbackWrapper(adCallback));
        }
    }

    private boolean isLoading() {
        return loading.get();
    }

    private void onAdResponse() {
        loading.set(false);
    }

    protected abstract void doLoadAd(AdCallback adCallback);

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }

    private class AdCallbackWrapper implements AdCallback {

        private final AdCallback adCallback;

        private AdCallbackWrapper(AdCallback adCallback) {
            this.adCallback = adCallback;
        }

        @Override
        public void adLoaded() {
            onAdResponse();
            loaded.set(true);
            adCallback.adLoaded();
        }

        @Override
        public void adLoadFailed() {
            onAdResponse();
            adCallback.adLoadFailed();
        }
    }
}
