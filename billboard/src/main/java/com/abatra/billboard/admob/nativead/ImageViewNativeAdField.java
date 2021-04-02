package com.abatra.billboard.admob.nativead;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.Optional;

abstract public class ImageViewNativeAdField implements NativeAdField<ImageView> {

    private final ImageView imageView;

    public ImageViewNativeAdField(ImageView imageView) {
        this.imageView = imageView;
    }

    @Nullable
    public static NativeAdField<ImageView> goneIfMissingIconOrPrimaryImage(@Nullable ImageView imageView) {
        return Optional.ofNullable(imageView)
                .map(iv -> new GoneIfNotSetNativeAdField<>(new IconOrPrimaryImageViewNativeAdField(imageView)))
                .orElse(null);
    }

    @Override
    public ImageView getView() {
        return imageView;
    }

    @Override
    public boolean isSet() {
        return Optional.ofNullable(getView().getDrawable()).isPresent();
    }
}
