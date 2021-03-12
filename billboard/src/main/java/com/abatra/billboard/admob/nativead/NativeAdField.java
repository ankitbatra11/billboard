package com.abatra.billboard.admob.nativead;

import android.view.View;

public interface NativeAdField<V> {

    View getView();

    void setValue(V value);
}
