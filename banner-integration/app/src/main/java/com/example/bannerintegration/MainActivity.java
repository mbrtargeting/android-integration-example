package com.example.bannerintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.sourcepoint.cmplibrary.model.MessageLanguage;
import com.stroeer.plugins.backfill.IInitializationCallback;
import com.stroeer.plugins.backfill.gravite.GraviteLoader;
import com.stroeer.plugins.monitoring.IAdMonitorCallback;
import com.stroeer.plugins.monitoring.confiant.ConfiantLoader;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAd;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdListener;
import com.yieldlove.adIntegration.AdFormats.YieldloveBannerAdView;
import com.yieldlove.adIntegration.Yieldlove;
import com.yieldlove.adIntegration.YieldloveConsent;
import com.yieldlove.adIntegration.exceptions.YieldloveException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // enable Confiant
//        ConfiantLoader.getInstance().enableTestMode();
//        ConfiantLoader.getInstance().initialize("", true, new IAdMonitorCallback() {
//            @Override
//            public void onInitialized(boolean success) {
//
//            }
//        });

        // enable Gravite
//        GraviteLoader.getInstance().enableDebugMode();
//        GraviteLoader.getInstance().enableTestMode(null, null, true);
//        GraviteLoader.getInstance().initialize(getApplication(), new IInitializationCallback() {
//            @Override
//            public void onInitialized(boolean success) {
//
//            }
//        });

        // Create a map to define custom targeting parameters
        Map<String, List<String>> map = new HashMap<>();
// Add user-specific data to the targeting map
        map.put("userAge", List.of("25")); // Example: User's age
        map.put("userInterest", List.of("sports", "technology", "music")); // Example: User's interests
        map.put("userLocation", List.of("New York")); // Example: User's location
// Apply the custom targeting globally for Gravite
        Yieldlove.setCustomTargeting(map);

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


    @Override
    protected void onResume() {
        super.onResume();
        GraviteLoader.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GraviteLoader.getInstance().onPause(this);
    }
}