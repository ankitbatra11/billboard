package com.abatra.billboard.nativead;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.abatra.billboard.admob.nativead.DefaultAdmobNativeAdRenderer;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAdmobNativeAdRendererTest {

    public static final String HEADLINE = "headline";
    public static final String BODY = "body";
    public static final String CTA = "cta";
    public static final String STORE = "store";
    public static final String PRICE = "price";
    public static final String ADVERTISER = "advertiser";
    @Mock
    private NativeAdView mockedNativeAdView;

    @Mock
    private TextView mockedHeadlineTextView;

    @Mock
    private NativeAd mockedNativeAd;

    @Mock
    private NativeAd.Image mockedImageWithDrawable;

    @Mock
    private Drawable mockedDrawable;

    @Mock
    private NativeAd.Image mockedImageWithoutDrawable;

    private DefaultAdmobNativeAdRenderer renderer;

    @Mock
    private TextView mockedBodyTextView;

    @Mock
    private TextView mockedCtaTextView;

    @Mock
    private ImageView mockedPrimaryImageView;

    @Mock
    private ImageView mockedIconImageView;

    @Mock
    private TextView mockedStoreTextView;

    @Mock
    private TextView mockedPriceTextView;

    @Mock
    private TextView mockedAdvertiserTextView;

    @Before
    public void setup() {

        when(mockedImageWithDrawable.getDrawable()).thenReturn(mockedDrawable);

        when(mockedNativeAd.getHeadline()).thenReturn(HEADLINE);
        when(mockedNativeAd.getBody()).thenReturn(BODY);
        when(mockedNativeAd.getCallToAction()).thenReturn(CTA);
        when(mockedNativeAd.getImages()).thenReturn(Arrays.asList(null, mockedImageWithDrawable, mockedImageWithoutDrawable));
        when(mockedNativeAd.getIcon()).thenReturn(mockedImageWithDrawable);
        when(mockedNativeAd.getStore()).thenReturn(STORE);
        when(mockedNativeAd.getPrice()).thenReturn(PRICE);
        when(mockedNativeAd.getAdvertiser()).thenReturn(ADVERTISER);

        renderer = new DefaultAdmobNativeAdRenderer(mockedNativeAdView, mockedHeadlineTextView);
        renderer.setBodyTextView(mockedBodyTextView);
        renderer.setCallToActionTextView(mockedCtaTextView);
        renderer.setPrimaryImageView(mockedPrimaryImageView);
        renderer.setIconImageView(mockedIconImageView);
        renderer.setStoreTextView(mockedStoreTextView);
        renderer.setPriceTextView(mockedPriceTextView);
        renderer.setAdvertiserTextView(mockedAdvertiserTextView);
    }

    @Test
    public void test_render() {

        renderer.render(mockedNativeAd);

        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_bodyFieldNotSet() {

        renderer.setBodyTextNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutBody();
    }

    private void verifyRenderWithoutBody() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, never()).setText(anyString());
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_ctaFieldNotSet() {

        renderer.setCallToActionTextNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutCta();
    }

    private void verifyRenderWithoutCta() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, never()).setText(anyString());
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_primaryImageNotSet() {

        renderer.setPrimaryImageNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutPrimaryImage();
    }

    private void verifyRenderWithoutPrimaryImage() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, never()).setImageDrawable(any());
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(1)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_iconNotSet() {

        renderer.setIconImageNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutIcon();
    }

    private void verifyRenderWithoutIcon() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, never()).setImageDrawable(any());
        verify(mockedImageWithDrawable, times(1)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_storeNotSet() {

        renderer.setStoreTextNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutStore();
    }

    private void verifyRenderWithoutStore() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, never()).setText(anyString());
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_priceNotSet() {

        renderer.setPriceTextNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutPrice();
    }

    private void verifyRenderWithoutPrice() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, never()).setText(anyString());
        verify(mockedAdvertiserTextView, times(1)).setText(ADVERTISER);
    }

    @Test
    public void test_render_advertiserNotSet() {

        renderer.setAdvertiserTextNativeAdField(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutAdvertiser();
    }

    private void verifyRenderWithoutAdvertiser() {
        verify(mockedHeadlineTextView, times(1)).setText(HEADLINE);
        verify(mockedBodyTextView, times(1)).setText(BODY);
        verify(mockedCtaTextView, times(1)).setText(CTA);
        verify(mockedPrimaryImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedIconImageView, times(1)).setImageDrawable(mockedDrawable);
        verify(mockedImageWithDrawable, times(2)).getDrawable();
        verify(mockedStoreTextView, times(1)).setText(STORE);
        verify(mockedPriceTextView, times(1)).setText(PRICE);
        verify(mockedAdvertiserTextView, never()).setText(anyString());
    }

    @Test
    public void test_render_nativeAdMissingBody() {

        when(mockedNativeAd.getBody()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutBody();
    }

    @Test
    public void test_render_nativeAdMissingCta() {

        when(mockedNativeAd.getCallToAction()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutCta();
    }

    @Test
    public void test_render_nativeAdMissingPrimaryImage() {

        when(mockedNativeAd.getImages()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutPrimaryImage();
    }

    @Test
    public void test_render_nativeAdMissingIcon() {

        when(mockedNativeAd.getIcon()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutIcon();
    }

    @Test
    public void test_render_nativeAdMissingStore() {

        when(mockedNativeAd.getStore()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutStore();
    }

    @Test
    public void test_render_nativeAdMissingPrice() {

        when(mockedNativeAd.getPrice()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutPrice();
    }

    @Test
    public void test_render_nativeAdMissingAdvertiser() {

        when(mockedNativeAd.getAdvertiser()).thenReturn(null);

        renderer.render(mockedNativeAd);

        verifyRenderWithoutAdvertiser();
    }

}
