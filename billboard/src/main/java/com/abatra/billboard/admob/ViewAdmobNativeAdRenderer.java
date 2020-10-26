package com.abatra.billboard.admob;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

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
    public void render(@Nonnull UnifiedNativeAd unifiedNativeAd) {

        UnifiedNativeAdView unifiedNativeAdView = nativeAdViewContainer.findViewById(unifiedNativeAdViewId);

        if (iconViewId != null) {
            ImageView imageView = nativeAdViewContainer.findViewById(iconViewId);
            NativeAd.Image image = unifiedNativeAd.getIcon();
            if (image != null) {
                imageView.setImageDrawable(image.getDrawable());
                unifiedNativeAdView.setIconView(imageView);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        TextView headlineTextView = nativeAdViewContainer.findViewById(headlineViewId);
        headlineTextView.setText(unifiedNativeAd.getHeadline());
        unifiedNativeAdView.setHeadlineView(headlineTextView);

        TextView bodyTextView = nativeAdViewContainer.findViewById(bodyViewId);
        bodyTextView.setText(unifiedNativeAd.getBody());
        unifiedNativeAdView.setBodyView(bodyTextView);

        if (ctaViewId != null) {
            TextView ctaTextView = nativeAdViewContainer.findViewById(ctaViewId);
            if (unifiedNativeAd.getCallToAction() != null) {
                ctaTextView.setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.setCallToActionView(ctaTextView);
            } else {
                ctaTextView.setVisibility(View.GONE);
            }
        }

        if (mediaViewId != null) {
            MediaView mediaView = nativeAdViewContainer.findViewById(mediaViewId);
            unifiedNativeAdView.setMediaView(mediaView);
        }

        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }
}
