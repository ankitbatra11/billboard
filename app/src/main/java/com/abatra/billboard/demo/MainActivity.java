package com.abatra.billboard.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobNativeAdRenderer;
import com.abatra.billboard.admob.AdmobRewardedAd;
import com.abatra.billboard.admob.AdmobRewardedAdRenderer;
import com.abatra.billboard.admob.appopenad.AppOpenAdActivity;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class MainActivity extends AppCompatActivity implements AppOpenAdActivity {

    private static final String LOG_TAG = "MainActivity";

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
                nativeAd.render((AdmobNativeAdRenderer) ad -> {

                    if (ad.getIcon() != null) {
                        binding.nativeAdIcon.setImageDrawable(ad.getIcon().getDrawable());
                        binding.unifiedNativeAdView.setIconView(binding.nativeAdIcon);
                    }

                    binding.nativeAdTitle.setText(ad.getHeadline());
                    binding.unifiedNativeAdView.setHeadlineView(binding.nativeAdTitle);

                    binding.nativeAdSubtitle.setText(ad.getBody());
                    binding.unifiedNativeAdView.setBodyView(binding.nativeAdSubtitle);

                    binding.nativeAdCta.setText(ad.getCallToAction());
                    binding.unifiedNativeAdView.setCallToActionView(binding.nativeAdCta);

                    binding.unifiedNativeAdView.setNativeAd(ad);
                });
            }
        });

        AdmobInterstitialAd admobInterstitialAd = new AdmobInterstitialAd(this, "ca-app-pub-3940256099942544/1033173712");
        admobInterstitialAd.loadAd(new AdCallback.LogAdCallback() {
            @Override
            public void adLoaded() {
                admobInterstitialAd.render((AdmobInterstitialAdRenderer) interstitialAd -> interstitialAd.show(MainActivity.this));
            }
        });

        AdmobRewardedAd admobRewardedAd = new AdmobRewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        admobRewardedAd.loadAd(new AdCallback() {
            @Override
            public void adLoaded() {
                admobRewardedAd.render((AdmobRewardedAdRenderer) rewardedAd -> {
                    OnUserEarnedRewardListener listener = rewardItem -> Log.d(LOG_TAG, "reward=" + rewardItem);
                    rewardedAd.show(MainActivity.this, listener);
                });
            }

            @Override
            public void adLoadFailed() {

            }

            @Override
            public void adClosed() {

            }

            @Override
            public void adClicked() {

            }

            @Override
            public void adDisplayed() {

            }

            @Override
            public void adFailedToShow() {

            }
        });
    }

    @Override
    public void render(AppOpenAd appOpenAd, FullScreenContentCallback fullScreenContentCallback) {
        appOpenAd.show(this, fullScreenContentCallback);
    }
}