package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public class DefaultAdmobNativeAdRenderer implements AdmobNativeAdRenderer {

    private final NativeAdView nativeAdView;
    private final TextNativeAdField headlineTextNativeAdField;
    @Nullable
    private TextNativeAdField bodyTextNativeAdField;
    @Nullable
    private ImageNativeAdField iconImageNativeAdField;
    @Nullable
    private TextNativeAdField callToActionTextNativeAdField;
    @Nullable
    private StarRatingNativeAdField starRatingNativeAdField;
    @Nullable
    private TextNativeAdField storeTextNativeAdField;
    @Nullable
    private TextNativeAdField priceTextNativeAdField;
    @Nullable
    private TextNativeAdField advertiserTextNativeAdField;
    @Nullable
    private MediaView mediaView;

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, TextNativeAdField headlineTextNativeAdField) {
        this.nativeAdView = nativeAdView;
        this.headlineTextNativeAdField = headlineTextNativeAdField;
    }

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, TextView headlineTextView) {
        this(nativeAdView, new TextViewTextNativeAdField(headlineTextView));
    }

    public DefaultAdmobNativeAdRenderer setStarRatingNativeAdField(StarRatingNativeAdField starRatingNativeAdField) {
        this.starRatingNativeAdField = starRatingNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setMediaView(MediaView mediaView) {
        this.mediaView = mediaView;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextNativeAdField(TextNativeAdField bodyTextNativeAdField) {
        this.bodyTextNativeAdField = bodyTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextView(TextView bodyTextView) {
        return setBodyTextNativeAdField(new TextViewTextNativeAdField(bodyTextView));
    }

    public DefaultAdmobNativeAdRenderer setIconImageNativeAdField(ImageNativeAdField iconImageNativeAdField) {
        this.iconImageNativeAdField = iconImageNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setIconImageView(ImageView iconImageView) {
        return setIconImageNativeAdField(new ImageViewImageNativeAdField(iconImageView));
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextNativeAdField(TextNativeAdField callToActionTextNativeAdField) {
        this.callToActionTextNativeAdField = callToActionTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextView(TextView callToActionTextView) {
        return setCallToActionTextNativeAdField(new TextViewTextNativeAdField(callToActionTextView));
    }

    public DefaultAdmobNativeAdRenderer setStoreTextNativeAdField(TextNativeAdField storeTextNativeAdField) {
        this.storeTextNativeAdField = storeTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setStoreTextView(TextView storeTextView) {
        return setStoreTextNativeAdField(new TextViewTextNativeAdField(storeTextView));
    }

    public DefaultAdmobNativeAdRenderer setPriceTextNativeAdField(TextNativeAdField priceTextNativeAdField) {
        this.priceTextNativeAdField = priceTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setPriceTextView(TextView priceTextView) {
        return setPriceTextNativeAdField(new TextViewTextNativeAdField(priceTextView));
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextNativeAdField(TextNativeAdField advertiserTextNativeAdField) {
        this.advertiserTextNativeAdField = advertiserTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextView(TextView advertiserTextView) {
        return setAdvertiserTextNativeAdField(new TextViewTextNativeAdField(advertiserTextView));
    }

    @Override
    public void render(@Nonnull NativeAd nativeAd) {

        setValue(NativeAdView::getHeadlineView,
                nativeAd::getHeadline,
                headlineTextNativeAdField,
                NativeAdView::setHeadlineView);

        setValue(NativeAdView::getBodyView,
                nativeAd::getBody,
                bodyTextNativeAdField,
                NativeAdView::setBodyView);

        setValue(NativeAdView::getCallToActionView,
                nativeAd::getCallToAction,
                callToActionTextNativeAdField,
                NativeAdView::setCallToActionView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getIconView,
                nativeAd::getIcon,
                iconImageNativeAdField,
                NativeAdView::setIconView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getStarRatingView,
                nativeAd::getStarRating,
                starRatingNativeAdField,
                NativeAdView::setStarRatingView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getStoreView,
                nativeAd::getStore,
                storeTextNativeAdField,
                NativeAdView::setStoreView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getPriceView,
                nativeAd::getPrice,
                priceTextNativeAdField,
                NativeAdView::setPriceView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getAdvertiserView,
                nativeAd::getAdvertiser,
                advertiserTextNativeAdField,
                NativeAdView::setAdvertiserView);

        if (mediaView != null) {
            nativeAdView.setMediaView(mediaView);
        }
        nativeAdView.setNativeAd(nativeAd);
    }

    protected <V> void ifNativeAdFieldNotNullSetValue(Function<NativeAdView, View> nativeAdTextViewGetter,
                                                      Supplier<V> textSupplier,
                                                      NativeAdField<V> nativeAdField,
                                                      BiConsumer<NativeAdView, View> fieldViewSetter) {
        if (nativeAdField != null) {
            setValue(nativeAdTextViewGetter, textSupplier, nativeAdField, fieldViewSetter);
        }
    }

    protected <V> void setValue(Function<NativeAdView, View> fieldViewGetter,
                                Supplier<V> valueSupplier,
                                NativeAdField<V> nativeAdField,
                                BiConsumer<NativeAdView, View> fieldViewSetter) {
        if (fieldViewGetter.apply(nativeAdView) == null) {
            nativeAdField.setValue(valueSupplier.get());
            fieldViewSetter.accept(nativeAdView, nativeAdField.getView());
        }
    }
}
