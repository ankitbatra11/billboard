package com.abatra.billboard.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.wheelie.lifecycle.ILifecycleOwner;
import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobRewardedAd;
import com.abatra.billboard.admob.AdmobRewardedAdRenderer;
import com.abatra.billboard.admob.ViewAdmobNativeAdRenderer;
import com.abatra.billboard.admob.appopenad.AppOpenAdActivity;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.material.snackbar.Snackbar;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements AppOpenAdActivity, ILifecycleOwner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Ad nativeAd = new AdmobNativeAd(this, "ca-app-pub-3940256099942544/2247696110");
        getLifecycle().addObserver(nativeAd);
        nativeAd.loadAd(new AdCallback.LogAdCallback() {
            @Override
            public void adLoaded() {
                nativeAd.render(new ViewAdmobNativeAdRenderer(binding.unifiedNativeAdView, R.id.native_ad_title)
                        .setIconViewId(R.id.native_ad_icon)
                        .setBodyViewId(R.id.native_ad_subtitle)
                        .setCtaViewId(R.id.native_ad_cta));
            }
        });

        binding.interstitialBtn.setOnClickListener(v -> {
            AdmobInterstitialAd admobInterstitialAd = new AdmobInterstitialAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712");
            admobInterstitialAd.loadAd(new AdCallback.LogAdCallback() {
                @Override
                public void adLoaded() {
                    admobInterstitialAd.render((AdmobInterstitialAdRenderer) interstitialAd -> interstitialAd.show(MainActivity.this));
                }
            });
        });

        binding.rewardedBtn.setOnClickListener(v -> {
            AdmobRewardedAd admobRewardedAd = new AdmobRewardedAd(MainActivity.this, "ca-app-pub-3940256099942544/5224354917");
            admobRewardedAd.loadAd(new AdCallback.LogAdCallback() {
                @Override
                public void adLoaded() {
                    Snackbar.make(binding.getRoot(), admobRewardedAd.getReward().toString(), Snackbar.LENGTH_INDEFINITE).show();
                    admobRewardedAd.render((AdmobRewardedAdRenderer) rewardedAd -> {
                        OnUserEarnedRewardListener listener = rewardItem -> Timber.d("reward=%s", rewardItem);
                        rewardedAd.show(MainActivity.this, listener);
                    });
                }
            });
        });
    }

    @Override
    public void render(AppOpenAd appOpenAd, FullScreenContentCallback fullScreenContentCallback) {
        appOpenAd.show(this, fullScreenContentCallback);
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}