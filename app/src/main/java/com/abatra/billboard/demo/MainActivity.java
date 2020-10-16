package com.abatra.billboard.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobNativeAdRenderer;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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

                    binding.nativeAdIcon.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                    binding.unifiedNativeAdView.setIconView(binding.nativeAdIcon);

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
    }
}