package com.example.interstitialintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.sourcepoint.cmplibrary.model.MessageLanguage;
import com.stroeer.plugins.backfill.IInitializationCallback;
import com.stroeer.plugins.backfill.gravite.GraviteLoader;
import com.stroeer.plugins.monitoring.IAdMonitorCallback;
import com.stroeer.plugins.monitoring.confiant.ConfiantLoader;
import com.yieldlove.adIntegration.AdFormats.YieldloveInterstitialAd;
import com.yieldlove.adIntegration.AdFormats.YieldloveInterstitialAdListener;
import com.yieldlove.adIntegration.AdFormats.YieldloveInterstitialAdView;
import com.yieldlove.adIntegration.Yieldlove;
import com.yieldlove.adIntegration.YieldloveConsent;
import com.yieldlove.adIntegration.exceptions.ContextException;
import com.yieldlove.adIntegration.exceptions.YieldloveException;

public class MainActivity extends AppCompatActivity {

    YieldloveConsent consent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Yieldlove.setApplicationName("appDfpTest"); // <-- put here your application name
        this.consent = new YieldloveConsent(
                this,
                R.id.main);
        this.consent.collect(MessageLanguage.ENGLISH);

        // enable Confiant
        ConfiantLoader.getInstance().enableTestMode();      // Enable Test Mode, This function blocks all banners and interstitials.
        ConfiantLoader.getInstance().initialize("", true, new IAdMonitorCallback() { // Please inquire to get the accountId and set up the bundle id in Confiant.
            @Override
            public void onInitialized(boolean success) {

            }
        });

        // enable Gravite
        GraviteLoader.getInstance().enableDebugMode(); // Enable debug mode
        GraviteLoader.getInstance().enableTestMode("sport1.mobile.android.apps", null, true); // Enable test mode, Please inquire to get accountId and set up the bundle id in Gravite.

        GraviteLoader.getInstance().enableDirectGraviteCall(); // Enable direct Gravite call. This will bypass Stroeer SDK and call Gravite directly. Please discuss with a Stroeer dealer to use this.
        GraviteLoader.getInstance().setCacheSize(3);           // Set the cache size to 3. This is only for direct Gravite call.

        // Initialize Gravite
        GraviteLoader.getInstance().initialize(getApplication(), new IInitializationCallback() {
            @Override
            public void onInitialized(boolean success) {

            }
        });
    }

    public void btnInterstitialClick(View view) throws ContextException {
        try {
            YieldloveInterstitialAd ad = new YieldloveInterstitialAd(this);
            ad.load("interstitial", new YieldloveInterstitialAdListener(){  // <-- put here your adslot name

                @Override
                public AdManagerAdRequest.Builder onAdRequestBuild() {
                    return null;
                }

                @Override
                public void onAdLoaded(YieldloveInterstitialAdView interstitial) {
                    interstitial.show();
                }

                @Override
                public void onAdFailedToLoad(YieldloveInterstitialAdView interstitial, YieldloveException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Ad load failed", Toast.LENGTH_LONG).show();
                }
            });
        }catch (YieldloveException e) {
            e.printStackTrace();
        }
    }

    public void btnPrivacyClick(View view) {
        this.consent.showPrivacyManager(MessageLanguage.ENGLISH);
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