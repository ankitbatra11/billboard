package com.abatra.billboard.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.wheelie.lifecycle.ILifecycleOwner;
import com.abatra.billboard.Ad;
import com.abatra.billboard.AdCallback;
import com.abatra.billboard.admob.AdmobInterstitialAd;
import com.abatra.billboard.admob.AdmobInterstitialAdRenderer;
import com.abatra.billboard.admob.AdmobNativeAd;
import com.abatra.billboard.admob.AdmobNativeAdRenderer;
import com.abatra.billboard.admob.AdmobRewardedAd;
import com.abatra.billboard.admob.AdmobRewardedAdRenderer;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAd;
import com.abatra.billboard.admob.AdmobRewardedInterstitialAdRenderer;
import com.abatra.billboard.admob.BottomSheetDialogRewardedInterstitialAdOptIn;
import com.abatra.billboard.admob.RewardedInterstitialAdOptIn;
import com.abatra.billboard.admob.RewardedInterstitialAdOptOutTimer;
import com.abatra.billboard.admob.appopenad.AppOpenAdActivity;
import com.abatra.billboard.demo.databinding.ActivityMainBinding;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

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
                    admobRewardedAd.render((AdmobRewardedAdRenderer) rewardedAd -> {
                        OnUserEarnedRewardListener listener = rewardItem -> Timber.d("reward=%s", rewardItem);
                        rewardedAd.show(MainActivity.this, listener);
                    });
                }
            });
        });

        binding.rewardedInterstitialBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String adUnitId = "ca-app-pub-3940256099942544/5354046379";
                AdmobRewardedInterstitialAd rewardedInterstitialAd = new AdmobRewardedInterstitialAd(MainActivity.this, adUnitId);
                rewardedInterstitialAd.loadAd(new AdCallback.LogAdCallback() {

                    @Override
                    public void adLoaded() {
                        super.adLoaded();
                        RewardedInterstitialAdOptOutTimer optOutTimer = new RewardedInterstitialAdOptOutTimer(5);
                        BottomSheetDialogRewardedInterstitialAdOptIn optIn = new BottomSheetDialogRewardedInterstitialAdOptIn(optOutTimer);
                        optIn.setTitle("Free Premium Upgrade")
                                .setRewardDetails("Watch an ad to try premium upgrade for free")
                                .setOptOutActionText("No thanks")
                                .setOptOutTimeLeftMessageFactory(new BottomSheetDialogRewardedInterstitialAdOptIn.OptOutTimeLeftMessageFactory() {
                                    @Override
                                    public String createMessage(int seconds) {
                                        return String.format(Locale.ENGLISH, "Video starts in %d second(s)", seconds);
                                    }
                                })
                                .show(MainActivity.this, new RewardedInterstitialAdOptIn.Listener() {

                                    @Override
                                    public void onUserOptedOut() {
                                        Snackbar.make(binding.getRoot(), "Opted out", Snackbar.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onOptOutTimeEnded() {
                                        rewardedInterstitialAd.render((AdmobRewardedInterstitialAdRenderer) ad -> {
                                            MainActivity activity = MainActivity.this;
                                            ad.show(activity, rewardItem -> {
                                                String message = "User earned reward=" + rewardItem;
                                                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                                            });
                                        });
                                    }
                                });
                    }
                });
            }
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