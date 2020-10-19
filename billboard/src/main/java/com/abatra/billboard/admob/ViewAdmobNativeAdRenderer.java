package com.abatra.billboard.admob;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ViewAdmobNativeAdRenderer implements AdmobNativeAdRenderer {

    private final UnifiedNativeAdView unifiedNativeAdView;

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

    public ViewAdmobNativeAdRenderer(@IdRes int headlineViewId,
                                     UnifiedNativeAdView unifiedNativeAdView,
                                     @IdRes int bodyViewId) {
        this.unifiedNativeAdView = unifiedNativeAdView;
        this.headlineViewId = headlineViewId;
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

    @Override
    public void render(@Nonnull UnifiedNativeAd unifiedNativeAd) {

        if (iconViewId != null) {
            ImageView imageView = unifiedNativeAdView.findViewById(iconViewId);
            if (unifiedNativeAd.getIcon() != null) {
                imageView.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.setIconView(imageView);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        TextView headlineTextView = unifiedNativeAdView.findViewById(headlineViewId);
        headlineTextView.setText(unifiedNativeAd.getHeadline());
        unifiedNativeAdView.setHeadlineView(headlineTextView);

        TextView bodyTextView = unifiedNativeAdView.findViewById(bodyViewId);
        bodyTextView.setText(unifiedNativeAd.getBody());
        unifiedNativeAdView.setBodyView(bodyTextView);

        if (ctaViewId != null) {
            TextView ctaTextView = unifiedNativeAdView.findViewById(ctaViewId);
            if (unifiedNativeAd.getCallToAction() != null) {
                ctaTextView.setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.setCallToActionView(ctaTextView);
            } else {
                ctaTextView.setVisibility(View.GONE);
            }
        }

        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }
}
