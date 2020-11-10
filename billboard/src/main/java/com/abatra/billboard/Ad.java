package com.abatra.billboard;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface Ad extends LifecycleObserver {

    void loadAd(AdCallback adCallback);

    boolean isLoaded();

    void render(AdRenderer adRenderer);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void forceLoadAd(AdCallback adCallback);
}
