package com.abatra.billboard;

public interface AdCallback {

    default void adLoaded() {
    }

    default void adLoadFailed() {
    }
}
