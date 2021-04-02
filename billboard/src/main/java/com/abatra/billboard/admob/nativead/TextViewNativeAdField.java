package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextViewNativeAdField implements TextNativeAdField {

    private final TextView textView;

    public TextViewNativeAdField(TextView textView) {
        this.textView = textView;
    }

    @Override
    public View getView() {
        return textView;
    }

    @Override
    public void setValue(@Nullable String value) {
        textView.setText(value);
    }
}
