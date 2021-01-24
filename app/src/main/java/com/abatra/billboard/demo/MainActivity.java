package com.abatra.billboard.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobNativeAdRenderer;
import com.abatra.billboard.admob.appopenad.AppOpenAdActivity;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class MainActivity extends AppCompatActivity implements AppOpenAdActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Ad nativeAd = new AdmobNativeAd(this, "ca-app-pub-3940256099942544/2247696110");
        getLifecycle().addObserver(nativeAd);
        nativeAd.loadAd(new AdCallback() {
            @Override
            public void adLoaded() {
                nativeAd.render((AdmobNativeAdRenderer) unifiedNativeAd -> {

                    if (unifiedNativeAd.getIcon() != null) {
                        binding.nativeAdIcon.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                        binding.unifiedNativeAdView.setIconView(binding.nativeAdIcon);
                    }

                    binding.nativeAdTitle.setText(unifiedNativeAd.getHeadline());
                    binding.unifiedNativeAdView.setHeadlineView(binding.nativeAdTitle);

                    binding.nativeAdSubtitle.setText(unifiedNativeAd.getBody());
                    binding.unifiedNativeAdView.setBodyView(binding.nativeAdSubtitle);

                    binding.nativeAdCta.setText(unifiedNativeAd.getCallToAction());
                    binding.unifiedNativeAdView.setCallToActionView(binding.nativeAdCta);

                    binding.unifiedNativeAdView.setNativeAd(unifiedNativeAd);
                });
            }
        });

        AdmobInterstitialAd admobInterstitialAd = new AdmobInterstitialAd(this, "ca-app-pub-3940256099942544/1033173712");
        admobInterstitialAd.loadAd(new AdCallback() {
            @Override
            public void adLoaded() {
                admobInterstitialAd.render((AdmobInterstitialAdRenderer) interstitialAd -> interstitialAd.show(MainActivity.this));
            }
        });
    }

    @Override
    public void render(AppOpenAd appOpenAd, FullScreenContentCallback fullScreenContentCallback) {
        appOpenAd.show(this, fullScreenContentCallback);
    }
}