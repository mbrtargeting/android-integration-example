package com.example.bannerintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAd;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdListener;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdView;
import com.yieldlove.adIntegration.Yieldlove;
import com.yieldlove.adIntegration.exceptions.YieldloveException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup adContainer = findViewById(R.id.AdContainer);

        // Set here your APPLICATION_NAME
        Yieldlove.setApplicationName("appDfpTestMonitoring2");

        try {
            YieldloveBannerAd ad = new YieldloveBannerAd(this);

            // Set here PUBLISHER_CALL_STRING / Slot name
            ad.load("b2", new YieldloveBannerAdListener() {

                @Override
                public AdManagerAdRequest.Builder onAdRequestBuild() {
                    return null;
                }

                @Override
                public void onAdLoaded(YieldloveBannerAdView banner) {
                    adContainer.addView(banner.getAdView());
                }

                @Override
                public void onAdFailedToLoad(YieldloveBannerAdView yieldloveBannerAdView, YieldloveException e) {

                }

                @Override
                public void onAdOpened(YieldloveBannerAdView yieldloveBannerAdView) {

                }

                @Override
                public void onAdClosed(YieldloveBannerAdView yieldloveBannerAdView) {

                }

                @Override
                public void onAdClicked(YieldloveBannerAdView yieldloveBannerAdView) {

                }

                @Override
                public void onAdImpression(YieldloveBannerAdView yieldloveBannerAdView) {

                }
            });

        } catch (YieldloveException e) {
            e.printStackTrace();
        }


    }
}