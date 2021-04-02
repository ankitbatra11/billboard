package com.abatra.billboard.admob.nativead;

import android.view.View;

import androidx.annotation.Nullable;

public interface NativeAdField<V> {
    /**
     * @return View backing native ad field.
     */
    View getView();

    /**
     * @param value to set to native ad field view.
     */
    void setValue(@Nullable V value);
}
