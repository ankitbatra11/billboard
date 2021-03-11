package com.abatra.billboard.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.wheelie.lifecycle.ILifecycleOwner;
import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobLoadAdRequest;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobRewardedAd;
import com.abatra.billboard.admob.AdmobRewardedAdRenderer;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAd;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAdRenderer;
import com.abatra.billboard.admob.ViewAdmobNativeAdRenderer;
import com.abatra.billboard.admob.appopenad.AppOpenAdActivity;
import com.abatra.billboard.admob.banner.AdmobAdaptiveBannerAd;
import com.abatra.billboard.admob.banner.AdmobBannerAd;
import com.abatra.billboard.admob.banner.AdmobBannerAdRenderer;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.material.snackbar.Snackbar;

import java.util.Optional;

import timber.log.Timber;

import static com.abatra.android.wheelie.thread.SaferTask.uiTask;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements AppOpenAdActivity, ILifecycleOwner {

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
        admobNativeAd.loadAd(new AdmobLoadAdRequest().setAdCallback(new AdCallback() {
            @Override
            public void onLoaded(Ad ad) {
                uiTask(() -> {

                    ad.render(new ViewAdmobNativeAdRenderer(binding.unifiedNativeAdView, R.id.native_ad_title)
                            .setIconViewId(R.id.native_ad_icon)
                            .setBodyViewId(R.id.native_ad_subtitle)
                            .setCtaViewId(R.id.native_ad_cta));

                    return null;
                });

            }
        }));

        binding.interstitialBtn.setOnClickListener(v -> {
            Optional.ofNullable(admobInterstitialAd).ifPresent(AdmobInterstitialAd::onDestroy);
            admobInterstitialAd = new AdmobInterstitialAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712");
            admobInterstitialAd.observeLifecycle(this);
            admobInterstitialAd.loadAd(new AdmobLoadAdRequest().setAdCallback(new AdCallback.LogAdCallback() {
                @Override
                public void onLoaded(Ad ad) {
                    super.onLoaded(ad);
                    ad.render((AdmobInterstitialAdRenderer) interstitialAd -> interstitialAd.show(MainActivity.this));
                }
            }));
        });

        binding.rewardedBtn.setOnClickListener(v -> {
            Optional.ofNullable(admobRewardedAd).ifPresent(AdmobRewardedAd::onDestroy);
            admobRewardedAd = new AdmobRewardedAd(MainActivity.this, "ca-app-pub-3940256099942544/5224354917");
            admobRewardedAd.observeLifecycle(this);
            admobRewardedAd.loadAd(new AdmobLoadAdRequest().setAdCallback(new AdCallback.LogAdCallback() {
                @Override
                public void onLoaded(Ad ad) {
                    super.onLoaded(ad);
                    Snackbar.make(binding.getRoot(), admobRewardedAd.getReward().toString(), Snackbar.LENGTH_SHORT).show();
                    ad.render((AdmobRewardedAdRenderer) rewardedAd -> {
                        OnUserEarnedRewardListener listener = rewardItem -> Timber.d("reward=%s", rewardItem);
                        rewardedAd.show(MainActivity.this, listener);
                    });
                }
            }));
        });

        binding.rewardedInterstitialBtn.setOnClickListener(v -> loadRewardedInterstitialAd());

        admobBannerAd = AdmobAdaptiveBannerAd.withCurrentOrientationAnchoredAdSize(this, "ca-app-pub-3940256099942544/6300978111");
        admobBannerAd.observeLifecycle(this);
        admobBannerAd.loadAd(new AdmobLoadAdRequest().setAdCallback(new AdCallback() {

            @Override
            public void onLoaded(Ad ad) {
                uiTask(() -> {
                    ad.render((AdmobBannerAdRenderer) adView -> {
                        binding.bannerAdContainer.removeAllViews();
                        binding.bannerAdContainer.addView(adView);
                        binding.bannerAdContainer.setMinimumHeight(0);
                    });
                    return null;
                });
            }

            @Override
            public void onAdaptiveBannerContainerMinHeightLoaded(int minHeight) {
                uiTask(() -> {
                    binding.bannerAdContainer.setMinimumHeight(minHeight);
                    return null;
                });
            }
        }));
    }

    private void loadRewardedInterstitialAd() {
        Optional.ofNullable(admobRewardedInterstitialAd).ifPresent(AdmobRewardedInterstitialAd::onDestroy);
        admobRewardedInterstitialAd = new AdmobRewardedInterstitialAd(this, "ca-app-pub-3940256099942544/5354046379");
        admobRewardedInterstitialAd.loadAd(new AdmobLoadAdRequest().setAdCallback(new AdCallback.LogAdCallback() {
            @Override
            public void onLoaded(Ad ad) {
                super.onLoaded(ad);
                ad.render((AdmobRewardedInterstitialAdRenderer) MainActivity.this::show);
            }
        }));
    }

    private void show(RewardedInterstitialAd interstitialAd) {
        interstitialAd.show(MainActivity.this, rewardItem -> {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "User earned reward", Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
    }

    @Override
    public void render(AppOpenAd appOpenAd, FullScreenContentCallback fullScreenContentCallback) {
        appOpenAd.show(this, fullScreenContentCallback);
    }
}