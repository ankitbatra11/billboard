package com.abatra.billboard.admob.appopenad;

import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;

public class CompositeFullScreenContentCallback extends FullScreenContentCallback {

    private static final String LOG_TAG = "CompositeCallback";

    private final List<FullScreenContentCallback> callbacks;

    public CompositeFullScreenContentCallback(List<FullScreenContentCallback> callbacks) {
        this.callbacks = callbacks;
    }

    public static CompositeFullScreenContentCallback of(FullScreenContentCallback... callbacks) {
        return new CompositeFullScreenContentCallback(Lists.newArrayList(callbacks));
    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError adError) {
        super.onAdFailedToShowFullScreenContent(adError);
        forEachConsumer(new Function<FullScreenContentCallback, Object>() {
            @NullableDecl
            @Override
            public Object apply(@NullableDecl FullScreenContentCallback input) {
                input.onAdFailedToShowFullScreenContent(adError);
                return null;
            }
        });
    }

    private void forEachConsumer(Function<FullScreenContentCallback, ?> callbackVoidFunction) {
        for (FullScreenContentCallback callback : callbacks) {
            try {
                callbackVoidFunction.apply(callback);
            } catch (Throwable t) {
                Log.e(LOG_TAG, "callback failed!", t);
            }
        }
    }

    @Override
    public void onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent();
        forEachConsumer(new Function<FullScreenContentCallback, Object>() {
            @NullableDecl
            @Override
            public Object apply(@NullableDecl FullScreenContentCallback input) {
                input.onAdShowedFullScreenContent();
                return null;
            }
        });
    }

    @Override
    public void onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent();
        forEachConsumer(new Function<FullScreenContentCallback, Object>() {
            @NullableDecl
            @Override
            public Object apply(@NullableDecl FullScreenContentCallback input) {
                input.onAdDismissedFullScreenContent();
                return null;
            }
        });
    }
}
