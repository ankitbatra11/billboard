package com.abatra.billboard.admob;

import android.os.Build;

import com.abatra.billboard.LoadAdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class AdmobRewardedInterstitialAdTest {

    private static final String AD_ID = "ad_id";

    private AdmobRewardedInterstitialAd admobRewardedInterstitialAd;

    @Mock
    private LoadAdRequest mockedLoadAdRequest;

    @Captor
    private ArgumentCaptor<OnInitializationCompleteListener> initializationCompleteListenerArgumentCaptor;

    @Mock
    private InitializationStatus mockedInitializationStatus;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        admobRewardedInterstitialAd = new AdmobRewardedInterstitialAd(getApplicationContext(), AD_ID);
    }

    @Test
    public void testNullContextWhenMobileAdsIsInitializedDoesNotThrowException() {

        try (MockedStatic<MobileAds> mobileAdsMockedStatic = mockStatic(MobileAds.class)) {

            admobRewardedInterstitialAd.doLoadAd(mockedLoadAdRequest);

            mobileAdsMockedStatic.verify(() -> MobileAds.initialize(same(getApplicationContext()),
                    initializationCompleteListenerArgumentCaptor.capture()));

            admobRewardedInterstitialAd.setContext(null);

            initializationCompleteListenerArgumentCaptor.getValue().onInitializationComplete(mockedInitializationStatus);

            verifyNoInteractions(mockedLoadAdRequest);
        }
    }
}
