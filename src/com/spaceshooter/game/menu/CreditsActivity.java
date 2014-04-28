package com.spaceshooter.game.menu;

import android.app.Activity;
import android.os.Bundle;

import com.example.se_android.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CreditsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Test ID:
				.addTestDevice("e83ab40d")										// Emulator
				.addTestDevice("0071a84d4acd309b") // Test ID: Jonas Nexus 4
				.addTestDevice("0009478f6e129f") // Test ID: Anders Galaxy S2
				.build();
		adView.loadAd(adRequest);
	}
}