package com.abatra.billboard;

import androidx.lifecycle.LiveData;

import com.abatra.android.wheelie.lifecycle.ILifecycleObserver;

public interface Ad extends ILifecycleObserver {

    LiveData<AdResource> loadAd(LoadAdRequest loadAdRequest);

    boolean isLoaded();

    void render(AdRenderer adRenderer);
}
