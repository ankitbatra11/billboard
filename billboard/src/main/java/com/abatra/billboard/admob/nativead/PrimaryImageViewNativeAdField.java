package com.abatra.billboard.admob.nativead;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PrimaryImageViewNativeAdField extends ImageViewNativeAdField {

    public PrimaryImageViewNativeAdField(ImageView imageView) {
        super(imageView);
    }

    @Override
    public void setValue(NativeAd nativeAd) {
        getFirstDrawable(nativeAd.getImages()).ifPresent(getView()::setImageDrawable);
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
