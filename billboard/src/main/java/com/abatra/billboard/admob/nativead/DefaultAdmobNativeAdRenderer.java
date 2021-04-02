package com.abatra.billboard.admob.nativead;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.Optional;

import javax.annotation.Nonnull;

public class DefaultAdmobNativeAdRenderer implements AdmobNativeAdRenderer {

    private final NativeAdView nativeAdView;
    private final NativeAdField<TextView> headlineTextNativeAdField;
    @Nullable
    private NativeAdField<TextView> bodyTextNativeAdField;
    @Nullable
    private NativeAdField<ImageView> iconImageNativeAdField;
    @Nullable
    private NativeAdField<TextView> callToActionTextNativeAdField;
    @Nullable
    private NativeAdField<TextView> starRatingNativeAdField;
    @Nullable
    private NativeAdField<TextView> storeTextNativeAdField;
    @Nullable
    private NativeAdField<TextView> priceTextNativeAdField;
    @Nullable
    private NativeAdField<TextView> advertiserTextNativeAdField;
    @Nullable
    private NativeAdField<MediaView> mediaViewNativeAdField;
    @Nullable
    private NativeAdField<ImageView> primaryImageNativeAdField;

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, NativeAdField<TextView> headlineTextNativeAdField) {
        this.nativeAdView = nativeAdView;
        this.headlineTextNativeAdField = headlineTextNativeAdField;
    }

    public DefaultAdmobNativeAdRenderer(NativeAdView nativeAdView, TextView headlineTextView) {
        this(nativeAdView, TextViewNativeAdField.headline(headlineTextView));
    }

    public DefaultAdmobNativeAdRenderer setStarRatingNativeAdField(@Nullable NativeAdField<TextView> starRatingNativeAdField) {
        this.starRatingNativeAdField = starRatingNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setMediaView(@Nullable MediaView mediaView) {
        mediaViewNativeAdField = Optional.ofNullable(mediaView).map(MediaViewNativeAdField::new).orElse(null);
        return this;
    }

    public DefaultAdmobNativeAdRenderer setMediaViewNativeAdField(@Nullable MediaViewNativeAdField mediaViewNativeAdField) {
        this.mediaViewNativeAdField = mediaViewNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextNativeAdField(@Nullable NativeAdField<TextView> bodyTextNativeAdField) {
        this.bodyTextNativeAdField = bodyTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setBodyTextView(@Nullable TextView bodyTextView) {
        return setBodyTextNativeAdField(TextViewNativeAdField.body(bodyTextView));
    }

    public DefaultAdmobNativeAdRenderer setIconImageNativeAdField(@Nullable NativeAdField<ImageView> iconImageNativeAdField) {
        this.iconImageNativeAdField = iconImageNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setIconImageView(@Nullable ImageView iconImageView) {
        return setIconImageNativeAdField(Optional.ofNullable(iconImageView).map(IconImageViewNativeAdField::new).orElse(null));
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextNativeAdField(@Nullable NativeAdField<TextView> callToActionTextNativeAdField) {
        this.callToActionTextNativeAdField = callToActionTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setCallToActionTextView(@Nullable TextView callToActionTextView) {
        return setCallToActionTextNativeAdField(TextViewNativeAdField.callToAction(callToActionTextView));
    }

    public DefaultAdmobNativeAdRenderer setStoreTextNativeAdField(@Nullable NativeAdField<TextView> storeTextNativeAdField) {
        this.storeTextNativeAdField = storeTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setStoreTextView(@Nullable TextView storeTextView) {
        return setStoreTextNativeAdField(TextViewNativeAdField.store(storeTextView));
    }

    public DefaultAdmobNativeAdRenderer setPriceTextNativeAdField(@Nullable NativeAdField<TextView> priceTextNativeAdField) {
        this.priceTextNativeAdField = priceTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setPriceTextView(@Nullable TextView priceTextView) {
        return setPriceTextNativeAdField(TextViewNativeAdField.price(priceTextView));
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextNativeAdField(@Nullable NativeAdField<TextView> advertiserTextNativeAdField) {
        this.advertiserTextNativeAdField = advertiserTextNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setAdvertiserTextView(@Nullable TextView advertiserTextView) {
        return setAdvertiserTextNativeAdField(TextViewNativeAdField.advertiser(advertiserTextView));
    }

    public DefaultAdmobNativeAdRenderer setPrimaryImageNativeAdField(@Nullable NativeAdField<ImageView> primaryImageNativeAdField) {
        this.primaryImageNativeAdField = primaryImageNativeAdField;
        return this;
    }

    public DefaultAdmobNativeAdRenderer setPrimaryImageView(ImageView primaryImageView) {
        this.primaryImageNativeAdField = Optional.ofNullable(primaryImageView).map(PrimaryImageViewNativeAdField::new).orElse(null);
        return this;
    }

    @Override
    public void render(@Nonnull NativeAd nativeAd) {

        if (nativeAdView.getHeadlineView() == null) {
            headlineTextNativeAdField.setValue(nativeAd);
            nativeAdView.setHeadlineView(headlineTextNativeAdField.getView());
        }

        Optional.ofNullable(bodyTextNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getBodyView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setBodyView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(callToActionTextNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getCallToActionView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setCallToActionView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(iconImageNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getIconView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setIconView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(primaryImageNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getImageView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setImageView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(starRatingNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getStarRatingView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setStarRatingView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(storeTextNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getStoreView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setStoreView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(priceTextNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getPriceView() == null) {
                nativeAdField.setValue(nativeAd);
                nativeAdView.setPriceView(nativeAdField.getView());
            }
        });

        Optional.ofNullable(advertiserTextNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getAdvertiserView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setAdvertiserView(nativeAdField.getView());
                }
            }
        });

        Optional.ofNullable(mediaViewNativeAdField).ifPresent(nativeAdField -> {
            if (nativeAdView.getMediaView() == null) {
                nativeAdField.setValue(nativeAd);
                if (nativeAdField.isSet()) {
                    nativeAdView.setMediaView(nativeAdField.getView());
                }
            }
        });

        nativeAdView.setNativeAd(nativeAd);
    }
}
