package com.abatra.billboard.admob.nativead;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;

public class MediaViewNativeAdField implements NativeAdField<MediaView> {

    private final MediaView mediaView;

    public MediaViewNativeAdField(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    public MediaView getView() {
        return mediaView;
    }

    @Override
    public void setValue(NativeAd nativeAd) {
        mediaView.setMediaContent(nativeAd.getMediaContent());
    }

    @Override
    public boolean isSet() {
        return true;
    }
}
