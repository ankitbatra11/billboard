package com.abatra.billboard.admob;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ViewAdmobNativeAdRenderer implements AdmobNativeAdRenderer {

    private final View nativeAdViewContainer;

    @IdRes
    private final int unifiedNativeAdViewId;

    @IdRes
    private final int headlineViewId;

    @IdRes
    private final int bodyViewId;

    @IdRes
    @Nullable
    private Integer iconViewId;

    @IdRes
    @Nullable
    private Integer ctaViewId;

    @IdRes
    @Nullable
    private Integer mediaViewId;

    public ViewAdmobNativeAdRenderer(@IdRes int headlineViewId,
                                     View nativeAdViewContainer,
                                     @IdRes int unifiedNativeAdViewId,
                                     @IdRes int bodyViewId) {
        this.headlineViewId = headlineViewId;
        this.unifiedNativeAdViewId = unifiedNativeAdViewId;
        this.nativeAdViewContainer = nativeAdViewContainer;
        this.bodyViewId = bodyViewId;
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

    @Override
    public void render(@Nonnull NativeAd nativeAd) {

        NativeAdView nativeAdView = nativeAdViewContainer.findViewById(unifiedNativeAdViewId);

        if (iconViewId != null) {
            ImageView imageView = nativeAdViewContainer.findViewById(iconViewId);
            NativeAd.Image image = nativeAd.getIcon();
            if (image != null) {
                imageView.setImageDrawable(image.getDrawable());
                nativeAdView.setIconView(imageView);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        TextView headlineTextView = nativeAdViewContainer.findViewById(headlineViewId);
        headlineTextView.setText(nativeAd.getHeadline());
        nativeAdView.setHeadlineView(headlineTextView);

        TextView bodyTextView = nativeAdViewContainer.findViewById(bodyViewId);
        bodyTextView.setText(nativeAd.getBody());
        nativeAdView.setBodyView(bodyTextView);

        if (ctaViewId != null) {
            TextView ctaTextView = nativeAdViewContainer.findViewById(ctaViewId);
            if (nativeAd.getCallToAction() != null) {
                ctaTextView.setText(nativeAd.getCallToAction());
                nativeAdView.setCallToActionView(ctaTextView);
            } else {
                ctaTextView.setVisibility(View.GONE);
            }
        }

        if (mediaViewId != null) {
            MediaView mediaView = nativeAdViewContainer.findViewById(mediaViewId);
            nativeAdView.setMediaView(mediaView);
        }

        nativeAdView.setNativeAd(nativeAd);
    }
}
