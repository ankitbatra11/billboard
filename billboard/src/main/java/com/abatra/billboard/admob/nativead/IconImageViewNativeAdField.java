package com.abatra.billboard.admob.nativead;

import android.widget.ImageView;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Optional;

public class IconImageViewNativeAdField extends ImageViewNativeAdField {

    public IconImageViewNativeAdField(ImageView imageView) {
        super(imageView);
    }

    @Override
    public void setValue(NativeAd nativeAd) {
        Optional.ofNullable(nativeAd.getIcon())
                .map(NativeAd.Image::getDrawable)
                .ifPresent(getView()::setImageDrawable);
    }
}
