package com.abatra.billboard;

import java.util.concurrent.atomic.AtomicBoolean;

abstract public class AbstractAd implements Ad {

    private final AtomicBoolean loadingAd = new AtomicBoolean(false);
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void loadAd(AdCallback adCallback) {
        if (!isLoaded() && !isLoading()) {
            doLoadAd(new AdCallback() {
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
            });
        }
    }

    private boolean isLoading() {
        return loadingAd.get();
    }

    private void onAdResponse() {
        loadingAd.set(false);
    }

    protected abstract void doLoadAd(AdCallback adCallback);

    @Override
    public boolean isLoaded() {
        return loaded.get();
    }
}
