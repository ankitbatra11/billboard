package com.abatra.billboard.admob.nativead;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.Optional;
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
    private MediaViewNativeAdField mediaViewNativeAdField;
    @Nullable
    private PrimaryImageNativeAdField primaryImageNativeAdField;

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, TextNativeAdField headlineTextNativeAdField) {
        this.nativeAdView = nativeAdView;
        this.headlineTextNativeAdField = headlineTextNativeAdField;
    }

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, TextView headlineTextView) {
        this(nativeAdView, new TextViewNativeAdField(headlineTextView));
    }

    public DefaultAdmobNativeAdRenderer setStarRatingNativeAdField(StarRatingNativeAdField starRatingNativeAdField) {
        this.starRatingNativeAdField = starRatingNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setMediaView(MediaView mediaView) {
        mediaViewNativeAdField = new MediaViewNativeAdField(mediaView);
        return this;
    }

    public DefaultAdmobNativeAdRenderer setMediaViewNativeAdField(@Nullable MediaViewNativeAdField mediaViewNativeAdField) {
        this.mediaViewNativeAdField = mediaViewNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextNativeAdField(TextNativeAdField bodyTextNativeAdField) {
        this.bodyTextNativeAdField = bodyTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextView(TextView bodyTextView) {
        return setBodyTextNativeAdField(new TextViewNativeAdField(bodyTextView));
    }

    public DefaultAdmobNativeAdRenderer setIconImageNativeAdField(ImageNativeAdField iconImageNativeAdField) {
        this.iconImageNativeAdField = iconImageNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setIconImageView(ImageView iconImageView) {
        return setIconImageNativeAdField(new ImageViewNativeAdField(iconImageView));
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextNativeAdField(TextNativeAdField callToActionTextNativeAdField) {
        this.callToActionTextNativeAdField = callToActionTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextView(TextView callToActionTextView) {
        return setCallToActionTextNativeAdField(new TextViewNativeAdField(callToActionTextView));
    }

    public DefaultAdmobNativeAdRenderer setStoreTextNativeAdField(TextNativeAdField storeTextNativeAdField) {
        this.storeTextNativeAdField = storeTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setStoreTextView(TextView storeTextView) {
        return setStoreTextNativeAdField(new TextViewNativeAdField(storeTextView));
    }

    public DefaultAdmobNativeAdRenderer setPriceTextNativeAdField(TextNativeAdField priceTextNativeAdField) {
        this.priceTextNativeAdField = priceTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setPriceTextView(TextView priceTextView) {
        return setPriceTextNativeAdField(new TextViewNativeAdField(priceTextView));
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextNativeAdField(TextNativeAdField advertiserTextNativeAdField) {
        this.advertiserTextNativeAdField = advertiserTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextView(TextView advertiserTextView) {
        return setAdvertiserTextNativeAdField(new TextViewNativeAdField(advertiserTextView));
    }

    public DefaultAdmobNativeAdRenderer setPrimaryImageNativeAdField(@Nullable PrimaryImageNativeAdField primaryImageNativeAdField) {
        this.primaryImageNativeAdField = primaryImageNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setPrimaryImageView(ImageView primaryImageView) {
        this.primaryImageNativeAdField = new ImageViewPrimaryImageNativeAdField(primaryImageView);
        return this;
    }

    @Override
    public void render(@Nonnull NativeAd nativeAd) {

        setValue(NativeAdView::getHeadlineView,
                nativeAd::getHeadline,
                headlineTextNativeAdField,
                NativeAdView::setHeadlineView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getBodyView,
                nativeAd::getBody,
                bodyTextNativeAdField,
                NativeAdView::setBodyView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getCallToActionView,
                nativeAd::getCallToAction,
                callToActionTextNativeAdField,
                NativeAdView::setCallToActionView);

        ifNativeAdFieldNotNullSetValue(NativeAdView::getImageView,
                nativeAd::getImages,
                primaryImageNativeAdField,
                NativeAdView::setImageView);

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

        ifNativeAdFieldNotNullSetValue(NativeAdView::getMediaView,
                nativeAd::getMediaContent,
                mediaViewNativeAdField,
                (nativeAdView, view) -> {
                    MediaView mediaView = (MediaView) view;
                    nativeAdView.setMediaView(mediaView);
                });

        nativeAdView.setNativeAd(nativeAd);
    }

    protected <VALUE> void ifNativeAdFieldNotNullSetValue(Function<NativeAdView, View> nativeAdFieldViewGetter,
                                                          Supplier<VALUE> valueSupplier,
                                                          @Nullable NativeAdField<VALUE> nativeAdField,
                                                          BiConsumer<NativeAdView, View> nativeAdFieldViewSetter) {
        //noinspection CodeBlock2Expr
        Optional.ofNullable(nativeAdField).ifPresent(field -> {
            setValue(nativeAdFieldViewGetter, valueSupplier, nativeAdField, nativeAdFieldViewSetter);
        });
    }

    protected <VALUE> void setValue(Function<NativeAdView, View> nativeAdFieldViewGetter,
                                    Supplier<VALUE> valueSupplier,
                                    @NonNull NativeAdField<VALUE> nativeAdField,
                                    BiConsumer<NativeAdView, View> nativeAdFieldViewSetter) {
        if (nativeAdFieldViewGetter.apply(nativeAdView) == null) {
            nativeAdField.setValue(valueSupplier.get());
            Optional.ofNullable(valueSupplier.get()).ifPresent(v -> {
                View nativeAdFieldView = nativeAdField.getView();
                nativeAdFieldViewSetter.accept(nativeAdView, nativeAdFieldView);
            });
        }
    }
}
