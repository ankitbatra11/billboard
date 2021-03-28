package com.abatra.billboard.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.wheelie.lifecycle.ILifecycleOwner;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobLoadAdRequest;
import com.abatra.billboard.admob.AdmobRewardedAd;
import com.abatra.billboard.admob.AdmobRewardedAdRenderer;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAd;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAdRenderer;
import com.abatra.billboard.admob.banner.AdmobBannerAd;
import com.abatra.billboard.admob.banner.AdmobBannerAdRenderer;
import com.abatra.billboard.admob.nativead.AdmobNativeAd;
import com.abatra.billboard.admob.nativead.DefaultAdmobNativeAdRenderer;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.material.snackbar.Snackbar;

import java.util.Optional;

import timber.log.Timber;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements ILifecycleOwner {

    private ActivityMainBinding binding;
    private AdmobNativeAd admobNativeAd;
    private AdmobBannerAd admobBannerAd;
    private AdmobInterstitialAd admobInterstitialAd;
    private AdmobRewardedAd admobRewardedAd;
    private AdmobRewardedInterstitialAd admobRewardedInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        admobNativeAd = new AdmobNativeAd(this, "ca-app-pub-3940256099942544/2247696110");
        admobNativeAd.observeLifecycle(this);
        admobNativeAd.loadAd(AdmobLoadAdRequest.EMPTY).observe(this, adResource -> {
            if (adResource.isLoaded()) {
                admobNativeAd.render(new DefaultAdmobNativeAdRenderer(binding.unifiedNativeAdView, binding.nativeAdTitle)
                        .setIconImageView(binding.nativeAdIcon)
                        .setBodyTextView(binding.nativeAdSubtitle)
                        .setCallToActionTextView(binding.nativeAdCta));
            }
        });

        binding.interstitialBtn.setOnClickListener(v -> {
            Optional.ofNullable(admobInterstitialAd).ifPresent(AdmobInterstitialAd::onDestroy);
            admobInterstitialAd = new AdmobInterstitialAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712");
            admobInterstitialAd.observeLifecycle(this);
            admobInterstitialAd.loadAd(AdmobLoadAdRequest.EMPTY).observe(this, adResource -> {
                if (adResource.isLoaded()) {
                    admobInterstitialAd.render((AdmobInterstitialAdRenderer) interstitialAd -> interstitialAd.show(MainActivity.this));
                }
            });
        });

        binding.rewardedBtn.setOnClickListener(v -> {
            Optional.ofNullable(admobRewardedAd).ifPresent(AdmobRewardedAd::onDestroy);
            admobRewardedAd = new AdmobRewardedAd(MainActivity.this, "ca-app-pub-3940256099942544/5224354917");
            admobRewardedAd.observeLifecycle(this);
            admobRewardedAd.loadAd(AdmobLoadAdRequest.EMPTY).observe(this, adResource -> {
                if (adResource.isLoaded()) {
                    Snackbar.make(binding.getRoot(), requireNonNull(admobRewardedAd.getReward()).toString(), Snackbar.LENGTH_SHORT).show();
                    admobRewardedAd.render((AdmobRewardedAdRenderer) rewardedAd -> {
                        OnUserEarnedRewardListener listener = rewardItem -> Timber.d("reward=%s", rewardItem);
                        rewardedAd.show(MainActivity.this, listener);
                    });
                }
            });
        });

        binding.rewardedInterstitialBtn.setOnClickListener(v -> loadRewardedInterstitialAd());

        admobBannerAd = AdmobBannerAd.adaptive(this, "ca-app-pub-3940256099942544/6300978111");
        admobBannerAd.observeLifecycle(this);
        admobBannerAd.loadAd(AdmobLoadAdRequest.EMPTY).observe(this, adResource -> {
            if (adResource.isLoaded()) {
                admobBannerAd.render((AdmobBannerAdRenderer) adView -> {
                    binding.bannerAdContainer.removeAllViews();
                    binding.bannerAdContainer.addView(adView);
                    binding.bannerAdContainer.setMinimumHeight(0);
                });
            }
        });
    }

    private void loadRewardedInterstitialAd() {
        Optional.ofNullable(admobRewardedInterstitialAd).ifPresent(AdmobRewardedInterstitialAd::onDestroy);
        admobRewardedInterstitialAd = new AdmobRewardedInterstitialAd(this, "ca-app-pub-3940256099942544/5354046379");
        admobRewardedInterstitialAd.loadAd(AdmobLoadAdRequest.EMPTY).observe(this, adResource -> {
            if (adResource.isLoaded()) {
                admobRewardedInterstitialAd.render((AdmobRewardedInterstitialAdRenderer) MainActivity.this::show);
            }
        });
    }

    private void show(RewardedInterstitialAd interstitialAd) {
        interstitialAd.show(MainActivity.this, rewardItem -> {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "User earned reward", Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
    }
}