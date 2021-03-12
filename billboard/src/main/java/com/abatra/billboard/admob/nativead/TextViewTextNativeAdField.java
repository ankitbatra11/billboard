package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.TextView;

public class TextViewTextNativeAdField implements TextNativeAdField {

    private final TextView textView;

    public TextViewTextNativeAdField(TextView textView) {
        this.textView = textView;
    }

    @Override
    public View getView() {
        return textView;
    }

    @Override
    public void setValue(String value) {
        textView.setText(value);
    }
}
