package com.abatra.billboard.admob.nativead;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Optional;
import java.util.function.Function;

abstract public class TextViewNativeAdField implements NativeAdField<TextView> {

    private final TextView textView;

    public TextViewNativeAdField(TextView textView) {
        this.textView = textView;
    }

    public static TextViewNativeAdField headline(TextView textView) {
        return create(textView, NativeAd::getHeadline);
    }

    @Nullable
    private static TextViewNativeAdField create(@Nullable TextView textView, Function<NativeAd, String> textValueGetter) {
        return Optional.ofNullable(textView)
                .map(t -> new TextViewNativeAdField(textView) {
                    @Override
                    public void setValue(NativeAd nativeAd) {
                        textView.setText(textValueGetter.apply(nativeAd));
                    }
                })
                .orElse(null);
    }

    public static TextViewNativeAdField callToAction(TextView callToActionTextView) {
        return create(callToActionTextView, NativeAd::getCallToAction);
    }

    public static TextViewNativeAdField store(TextView storeTextView) {
        return create(storeTextView, NativeAd::getStore);
    }

    public static TextViewNativeAdField price(TextView priceTextView) {
        return create(priceTextView, NativeAd::getPrice);
    }

    public static TextViewNativeAdField advertiser(TextView advertiserTextView) {
        return create(advertiserTextView, NativeAd::getAdvertiser);
    }

    public static TextViewNativeAdField body(TextView bodyTextView) {
        return create(bodyTextView, NativeAd::getBody);
    }

    @Override
    public TextView getView() {
        return textView;
    }

    @Override
    public boolean isSet() {
        return Optional.ofNullable(getView().getText()).isPresent();
    }
}
