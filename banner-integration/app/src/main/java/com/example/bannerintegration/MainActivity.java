package com.example.bannerintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.sourcepoint.cmplibrary.model.MessageLanguage;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAd;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdListener;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdView;
import com.yieldlove.adIntegration.Yieldlove;
import com.yieldlove.adIntegration.YieldloveConsent;
import com.yieldlove.adIntegration.exceptions.YieldloveException;

public class MainActivity extends AppCompatActivity {

    YieldloveConsent consent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup adContainer = findViewById(R.id.AdContainer);

        Yieldlove.setApplicationName("appDfpTest");
        Yieldlove.developerModeEnabled = true;
        Yieldlove.debug = true;

        this.consent = new YieldloveConsent(
                this,
                R.id.main);

        this.consent.collect(MessageLanguage.ENGLISH);

        try {
            YieldloveBannerAd ad = new YieldloveBannerAd(this);

            ad.load("banner", new YieldloveBannerAdListener() {

                @Override
                public AdManagerAdRequest.Builder onAdRequestBuild() {
                    return null;
                }

                @Override
                public void onAdLoaded(YieldloveBannerAdView banner) {
                    adContainer.removeAllViews();
                    adContainer.addView(banner.getAdView());
                }

                @Override
                public void onAdFailedToLoad(YieldloveBannerAdView yieldloveBannerAdView, YieldloveException e) {
                    Toast.makeText(getApplicationContext(), "Ad load failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAdOpened(YieldloveBannerAdView yieldloveBannerAdView) {
                    Toast.makeText(getApplicationContext(), "Ad opened", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed(YieldloveBannerAdView yieldloveBannerAdView) {
                    Toast.makeText(getApplicationContext(), "Ad closed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClicked(YieldloveBannerAdView yieldloveBannerAdView) {
                    Toast.makeText(getApplicationContext(), "Ad clicked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdImpression(YieldloveBannerAdView yieldloveBannerAdView) {
                    Toast.makeText(getApplicationContext(), "Ad impression", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (YieldloveException e) {
            e.printStackTrace();
        }

    }

    public void btnPrivacyClick(View view) {
        this.consent.showPrivacyManager(MessageLanguage.JAPANESE);
    }
}