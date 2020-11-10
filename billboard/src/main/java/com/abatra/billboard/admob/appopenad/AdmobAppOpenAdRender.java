package com.abatra.billboard.admob.appopenad;

import com.abatra.billboard.AdRenderer;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;

public interface AdmobAppOpenAdRender extends AdRenderer {
    void render(AppOpenAd appOpenAd, FullScreenContentCallback fullScreenContentCallback);
}
