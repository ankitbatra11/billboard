package com.abatra.billboard.admob.appopenad;

import android.app.Activity;

public interface AppOpenAdShowCondition {

    boolean showAd(Activity currentActivity);

    default AppOpenAdShowCondition and(AppOpenAdShowCondition condition) {
        return currentActivity -> AppOpenAdShowCondition.this.showAd(currentActivity) && condition.showAd(currentActivity);
    }
}
