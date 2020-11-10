package com.abatra.billboard;

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

    default void adFailedToShow() {}
}
