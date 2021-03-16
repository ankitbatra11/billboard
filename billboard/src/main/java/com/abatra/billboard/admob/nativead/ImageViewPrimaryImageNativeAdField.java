package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.List;
import java.util.Objects;

public class ImageViewPrimaryImageNativeAdField implements PrimaryImageNativeAdField {

    private final ImageView imageView;

    public ImageViewPrimaryImageNativeAdField(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public View getView() {
        return imageView;
    }

    @Override
    public void setValue(List<NativeAd.Image> value) {
        value.stream()
                .filter(Objects::nonNull)
                .map(NativeAd.Image::getDrawable)
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(imageView::setImageDrawable);
    }
}
