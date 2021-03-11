package com.abatra.billboard;

import com.abatra.billboard.admob.banner.AdaptiveBannerAdCallback;

import timber.log.Timber;

public interface AdCallback extends AdaptiveBannerAdCallback, ScreenAdCallback {

    AdCallback LOG = new LogAdCallback();

    default void onLoaded(Ad ad) {
    }

    default void onLoadFailed(Ad ad) {
    }

    @Override
    default void onClosed(Ad ad) {
    }

    @Override
    default void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
    }

    default void onClicked(Ad ad) {
    }

    default void onDisplayed(Ad ad) {
    }

    default void onFailedToShow(Ad ad, Throwable error) {
    }

    default void onImpression(Ad ad) {
    }

    class LogAdCallback implements AdCallback {

        @Override
        public void onLoaded(Ad ad) {
            Timber.d("loaded ad=%s", ad);
        }

        @Override
        public void onLoadFailed(Ad ad) {
            Timber.d("load failed ad=%s", ad);
        }

        @Override
        public void onClosed(Ad ad) {
            Timber.d("closed ad=%s", ad);
        }

        @Override
        public void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
            Timber.d("onAdaptiveBannerContainerMinHeightLoaded minHeight=%d", minHeight);
        }

        @Override
        public void onClicked(Ad ad) {
            Timber.d("clicked ad=%s", ad);
        }

        @Override
        public void onDisplayed(Ad ad) {
            Timber.d("displayed ad=%s", ad);
        }

        @Override
        public void onFailedToShow(Ad ad, Throwable error) {
            Timber.i(error, "failed to show ad=%s", ad);
        }

        @Override
        public void onImpression(Ad ad) {
            Timber.d("onImpression ad=%s", ad);
        }
    }
}
