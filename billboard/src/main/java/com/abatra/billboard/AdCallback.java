package com.abatra.billboard;

import com.abatra.billboard.admob.banner.AdaptiveBannerAdCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

import timber.log.Timber;

public interface AdCallback extends AdaptiveBannerAdCallback {

    AdCallback LOG = new LogAdCallback();

    void adLoaded();

    void adLoadFailed();

    void adClosed();

    void adClicked();

    void adDisplayed();

    void adFailedToShow();

    default FullScreenContentCallback asFullScreenContentCallback() {
        return new FullScreenContentCallbackAdapter(this);
    }

    default void onAdImpression() {
    }

    class LogAdCallback implements AdCallback {

        public LogAdCallback() {
        }

        @Override
        public void adLoaded() {
            Timber.d("ad loaded");
        }

        @Override
        public void adLoadFailed() {
            Timber.d("ad load failed");
        }

        @Override
        public void adClosed() {
            Timber.d("ad closed");
        }

        @Override
        public void adClicked() {
            Timber.d("ad clicked");
        }

        @Override
        public void adDisplayed() {
            Timber.d("ad displayed");
        }

        @Override
        public void adFailedToShow() {
            Timber.d("ad failed to show");
        }

        @Override
        public void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
            Timber.d("onAdaptiveBannerContainerMinHeightLoaded minHeight=%d", minHeight);
        }
    }

    class FullScreenContentCallbackAdapter extends FullScreenContentCallback {

        private final AdCallback adCallback;

        public FullScreenContentCallbackAdapter(AdCallback adCallback) {
            this.adCallback = adCallback;
        }

        @Override
        public void onAdFailedToShowFullScreenContent(AdError adError) {
            super.onAdFailedToShowFullScreenContent(adError);
            adCallback.adFailedToShow();
        }

        @Override
        public void onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent();
            adCallback.adDisplayed();
        }

        @Override
        public void onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent();
            adCallback.adClosed();
        }
    }
}
