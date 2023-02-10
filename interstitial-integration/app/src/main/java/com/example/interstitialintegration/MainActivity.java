package com.example.interstitialintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.sourcepoint.cmplibrary.model.MessageLanguage;
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
}