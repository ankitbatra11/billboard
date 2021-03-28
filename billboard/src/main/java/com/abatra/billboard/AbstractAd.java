package com.abatra.billboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abatra.android.wheelie.lifecycle.SingleLiveEvent;

import timber.log.Timber;

abstract public class AbstractAd implements Ad {

    public static final AdResource LOADING = AdResource.loading();
    public static final AdResource LOADED = AdResource.loaded();

    @Override
    public LiveData<AdResource> loadAd(LoadAdRequest loadAdRequest) {
        SingleLiveEvent<AdResource> liveData = new SingleLiveEvent<>();
        liveData.setValue(LOADING);
        try {
            tryLoadingAd(loadAdRequest, liveData);
        } catch (Throwable error) {
            Timber.e(error);
            liveData.setValue(AdResource.error(error));
        }
        return liveData;
    }

    protected abstract void tryLoadingAd(LoadAdRequest loadAdRequest, MutableLiveData<AdResource> liveData);
}
