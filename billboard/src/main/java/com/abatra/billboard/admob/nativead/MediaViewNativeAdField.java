package com.abatra.billboard.admob.nativead;

import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.MediaContent;
import com.google.android.gms.ads.nativead.MediaView;

import java.util.Optional;

public class MediaViewNativeAdField implements NativeAdField<MediaContent> {

    private final MediaView mediaView;

    public MediaViewNativeAdField(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    public View getView() {
        return mediaView;
    }

    @Override
    public void setValue(@Nullable MediaContent value) {
        Optional.ofNullable(value).ifPresent(mediaView::setMediaContent);
    }
}
