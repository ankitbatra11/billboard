package com.abatra.billboard;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

abstract public class AbstractAd implements Ad {

    private static final String LOG_TAG = "AbstractAd";

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void loadAd(AdCallback adCallback) {
        if (!isLoaded() && !isLoading()) {
            loading.set(true);
            doLoadAd(new AdCallbackWrapper(adCallback));
        } else {
            Log.d(LOG_TAG, "Not loading ad. loaded=" + isLoaded() + " loading=" + isLoading());
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

        private void onAdResponse() {
            loading.set(false);
        }

        @Override
        public void adClosed() {
            adCallback.adClosed();
        }
    }
}
