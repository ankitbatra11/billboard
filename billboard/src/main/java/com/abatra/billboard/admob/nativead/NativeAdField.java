package com.abatra.billboard.admob.nativead;

import android.view.View;

import com.google.android.gms.ads.nativead.NativeAd;

public interface NativeAdField<VIEW extends View> {
    /**
     * @return View backing native ad field.
     */
    VIEW getView();

    /**
     * @param nativeAd to use to set native ad field view.
     */
    void setValue(NativeAd nativeAd);

    /**
     * @return True if the value is set for this field.
     */
    boolean isSet();
}
