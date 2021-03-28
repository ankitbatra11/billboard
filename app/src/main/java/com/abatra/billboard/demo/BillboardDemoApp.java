package com.abatra.billboard.demo;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class BillboardDemoApp extends MultiDexApplication {

    private static final String ONE_PLUS_DEVICE = "98612BBF1433B7833F375AE392714233";
    private static final String SAMSUNG_TABLET = "57175DAD99C62E3DAE3F2B62DA19D4FB";

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(ONE_PLUS_DEVICE, SAMSUNG_TABLET))
                .build());
    }
}
