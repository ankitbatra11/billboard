package com.abatra.billboard;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.abatra.android.wheelie.lifecycle.ILifecycleObserver;

public interface Ad extends ILifecycleObserver {

    void loadAd(AdCallback adCallback);

    boolean isLoaded();

    void render(AdRenderer adRenderer);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void forceLoadAd(AdCallback adCallback);
}
