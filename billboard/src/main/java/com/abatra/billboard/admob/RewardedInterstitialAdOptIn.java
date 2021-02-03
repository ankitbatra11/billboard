package com.abatra.billboard.admob;

import android.content.Context;

import com.abatra.android.wheelie.lifecycle.ILifecycleObserver;

public interface RewardedInterstitialAdOptIn extends ILifecycleObserver {

    void show(Context context, Listener listener);

    interface Listener {

        default void onUserOptedOut() {
        }

        default void onOptOutTimeEnded() {
        }
    }
}
