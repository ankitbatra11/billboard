package com.abatra.billboard.admob;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ViewAdmobNativeAdRenderer implements AdmobNativeAdRenderer {

    private final NativeAdView nativeAdView;

    @IdRes
    private final int headlineViewId;

    @IdRes
    @Nullable
    private Integer bodyViewId;

    @IdRes
    @Nullable
    private Integer iconViewId;

    @IdRes
    @Nullable
    private Integer ctaViewId;

    @IdRes
    @Nullable
    private Integer mediaViewId;

    public ViewAdmobNativeAdRenderer(NativeAdView nativeAdView, @IdRes int headlineViewId) {
        this.nativeAdView = nativeAdView;
        this.headlineViewId = headlineViewId;
    }

    public ViewAdmobNativeAdRenderer setIconViewId(@Nullable Integer iconViewId) {
        this.iconViewId = iconViewId;
        return this;
    }

    public ViewAdmobNativeAdRenderer setCtaViewId(@Nullable Integer ctaViewId) {
        this.ctaViewId = ctaViewId;
        return this;
    }

    public ViewAdmobNativeAdRenderer setMediaViewId(@Nullable Integer mediaViewId) {
        this.mediaViewId = mediaViewId;
        return this;
    }

    public ViewAdmobNativeAdRenderer setBodyViewId(@Nullable Integer bodyViewId) {
        this.bodyViewId = bodyViewId;
        return this;
    }

    @Override
    public void render(@Nonnull NativeAd nativeAd) {

        if (iconViewId != null) {
            ImageView imageView = nativeAdView.findViewById(iconViewId);
            NativeAd.Image image = nativeAd.getIcon();
            if (image != null) {
                imageView.setImageDrawable(image.getDrawable());
                nativeAdView.setIconView(imageView);
            }
        }

        TextView headlineTextView = nativeAdView.findViewById(headlineViewId);
        headlineTextView.setText(nativeAd.getHeadline());
        nativeAdView.setHeadlineView(headlineTextView);

        if (bodyViewId != null) {
            TextView bodyTextView = nativeAdView.findViewById(bodyViewId);
            bodyTextView.setText(nativeAd.getBody());
            nativeAdView.setBodyView(bodyTextView);
        }

        if (ctaViewId != null) {
            TextView ctaTextView = nativeAdView.findViewById(ctaViewId);
            if (nativeAd.getCallToAction() != null) {
                ctaTextView.setText(nativeAd.getCallToAction());
                nativeAdView.setCallToActionView(ctaTextView);
            }
        }

        if (mediaViewId != null) {
            MediaView mediaView = nativeAdView.findViewById(mediaViewId);
            nativeAdView.setMediaView(mediaView);
        }

        nativeAdView.setNativeAd(nativeAd);
    }
}
