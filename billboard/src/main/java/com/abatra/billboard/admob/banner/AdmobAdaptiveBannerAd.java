package com.abatra.billboard.admob.banner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.common.base.Supplier;

import bolts.Task;
import timber.log.Timber;

public class AdmobAdaptiveBannerAd extends AdmobBannerAd {

    public AdmobAdaptiveBannerAd(Context context, String id, Supplier<AdSize> adSizeSupplier) {
        super(context, id, adSizeSupplier);
    }

    public static AdmobAdaptiveBannerAd withCurrentOrientationAnchoredAdSize(Context context, String id) {
        return new AdmobAdaptiveBannerAd(context, id, () -> adaptiveAdSize(context));
    }

    private static AdSize adaptiveAdSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthByDensity = (int) (outMetrics.widthPixels / outMetrics.density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, widthByDensity);
    }

    @Override
    protected void doLoadAd(LoadAdRequest loadAdRequest) {
        Task.callInBackground(this::loadBannerContainerMinHeight).continueWith(task ->
        {
            notifyBannerContainerMinHeightLoadResult(task, loadAdRequest);
            return null;

        }).continueWith(task ->
        {
            super.doLoadAd(loadAdRequest);
            return null;

        }, Task.UI_THREAD_EXECUTOR);
    }

    private void notifyBannerContainerMinHeightLoadResult(Task<Integer> task, LoadAdRequest loadAdRequest) {
        if (task.getError() != null) {
            Timber.e(task.getError(), "Could not get banner container min height!");
        } else {
            if (task.getResult() != null) {
                loadAdRequest.getAdCallback().onAdaptiveBannerContainerMinHeightLoaded(task.getResult());
            } else {
                Timber.e("Could not get banner container min height!");
            }
        }
    }

    private int loadBannerContainerMinHeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return (int) (outMetrics.heightPixels * 0.15);
    }
}
