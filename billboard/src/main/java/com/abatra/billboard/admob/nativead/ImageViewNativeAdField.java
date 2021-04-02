package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Optional;

public class ImageViewNativeAdField implements ImageNativeAdField {

    private final ImageView imageView;

    public ImageViewNativeAdField(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public View getView() {
        return imageView;
    }

    @Override
    public void setValue(@Nullable NativeAd.Image value) {
        Optional.ofNullable(value)
                .map(NativeAd.Image::getDrawable)
                .ifPresent(imageView::setImageDrawable);
    }
}
