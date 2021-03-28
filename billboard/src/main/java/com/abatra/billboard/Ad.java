package com.abatra.billboard;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.abatra.android.wheelie.lifecycle.ILifecycleObserver;

public interface Ad extends ILifecycleObserver {

    LiveData<AdResource> loadAd(LoadAdRequest loadAdRequest);

    boolean isLoaded();

    void render(AdRenderer adRenderer);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();
}
