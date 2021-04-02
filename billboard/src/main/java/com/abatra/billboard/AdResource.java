package com.abatra.billboard;

import androidx.annotation.Nullable;

import com.abatra.android.wheelie.lifecycle.Resource;

public class AdResource extends Resource<AdInteraction> {

    protected AdResource(Status status, @Nullable AdInteraction data, @Nullable Throwable error) {
        super(status, data, error);
    }

    public static AdResource loading() {
        return new AdResource(Status.LOADING, null, null);
    }

    public static AdResource loaded() {
        return new AdResource(Status.LOADED, null, null);
    }

    public static AdResource interacted(AdInteraction adInteraction) {
        return new AdResource(Status.LOADED, adInteraction, null);
    }

    public static AdResource error(Throwable error) {
        return new AdResource(Status.FAILED, null, error);
    }

    public boolean isLoaded() {
        return getStatus() == Status.LOADED && getData() == null;
    }
}
