package com.abatra.billboard.demo;

import androidx.multidex.MultiDexApplication;

import com.abatra.billboard.Ad;
import com.abatra.billboard.admob.AdmobLoadAdRequest;
import com.abatra.billboard.admob.appopenad.AdmobAppOpenAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class BillboardDemoApp extends MultiDexApplication {

    private static final String ONE_PLUS_DEVICE = "98612BBF1433B7833F375AE392714233";
    private static final String SAMSUNG_TABLET = "57175DAD99C62E3DAE3F2B62DA19D4FB";

    private Ad appOpenAd;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(ONE_PLUS_DEVICE, SAMSUNG_TABLET))
                .build());

        if (appOpenAd == null) {
            appOpenAd = AdmobAppOpenAd.create(this, "ca-app-pub-3940256099942544/3419835294");
            appOpenAd.loadAd(new AdmobLoadAdRequest());
        }
    }
}
