package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.nativead.NativeAd;

public class ImageViewImageNativeAdField implements ImageNativeAdField {

    private final ImageView imageView;

    public ImageViewImageNativeAdField(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public View getView() {
        return imageView;
    }

    @Override
    public void setValue(NativeAd.Image value) {
        imageView.setImageDrawable(value.getDrawable());
    }
}
