package com.abatra.billboard.admob.nativead;

import android.view.View;

import com.google.android.gms.ads.nativead.NativeAd;

public class GoneIfNotSetNativeAdField<VIEW extends View> implements NativeAdField<VIEW> {

    private final NativeAdField<VIEW> delegate;

    public GoneIfNotSetNativeAdField(NativeAdField<VIEW> delegate) {
        this.delegate = delegate;
    }

    @Override
    public VIEW getView() {
        return delegate.getView();
    }

    @Override
    public boolean isSet() {
        return delegate.isSet();
    }

    @Override
    public void setValue(NativeAd nativeAd) {
        delegate.setValue(nativeAd);
        if (!isSet()) {
            getView().setVisibility(View.GONE);
        }
    }
}
