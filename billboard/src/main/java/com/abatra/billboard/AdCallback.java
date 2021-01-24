package com.abatra.billboard;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

public interface AdCallback {

    AdCallback NO_OP = new AdCallback() {
    };

    default void adLoaded() {
    }

    default void adLoadFailed() {
    }

    default void adClosed() {
    }

    default void adClicked() {
    }

    default void adDisplayed() {
    }

    default void adFailedToShow() {
    }

    default FullScreenContentCallback asFullScreenContentCallback() {
        return new FullScreenContentCallbackAdapter(this);
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
