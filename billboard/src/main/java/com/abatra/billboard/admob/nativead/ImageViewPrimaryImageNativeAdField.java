package com.abatra.billboard.admob.nativead;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void setValue(@Nullable List<NativeAd.Image> value) {
        getFirstDrawable(value).ifPresent(imageView::setImageDrawable);
    }

    protected Optional<Drawable> getFirstDrawable(@Nullable List<NativeAd.Image> images) {
        return Optional.ofNullable(images)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(NativeAd.Image::getDrawable)
                .filter(Objects::nonNull)
                .findFirst();
    }
}
