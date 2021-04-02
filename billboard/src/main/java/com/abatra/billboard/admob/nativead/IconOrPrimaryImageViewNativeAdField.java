package com.abatra.billboard.admob.nativead;

import android.widget.ImageView;

import com.google.android.gms.ads.nativead.NativeAd;

public class IconOrPrimaryImageViewNativeAdField extends ImageViewNativeAdField {

    private final IconImageViewNativeAdField iconImageViewNativeAdField;
    private final PrimaryImageViewNativeAdField primaryImageViewNativeAdField;

    public IconOrPrimaryImageViewNativeAdField(ImageView imageView) {
        super(imageView);
        this.iconImageViewNativeAdField = new IconImageViewNativeAdField(imageView);
        this.primaryImageViewNativeAdField = new PrimaryImageViewNativeAdField(imageView);
    }

    @Override
    public void setValue(NativeAd nativeAd) {
        iconImageViewNativeAdField.setValue(nativeAd);
        if (iconImageViewNativeAdField.getView().getDrawable() == null) {
            primaryImageViewNativeAdField.setValue(nativeAd);
        }
    }
}
