package com.example.appotaadssample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.appota.ads.AdRequest;
import com.appota.ads.AdRequest.AdListener;
import com.appota.ads.AdView;
import com.appota.ads.AppotaAdsSDK;
import com.appota.ads.InterstitialAd;
import com.appota.ads.OfferWall;

public class MainPortraitActivity extends Activity {

	private int bannerCid, screenCid, offerCid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bannerCid = getIntent().getIntExtra("b", 46);
		screenCid = getIntent().getIntExtra("s", 47);
		offerCid = getIntent().getIntExtra("o", 48);
		setContentView(R.layout.activity_main);
		AppotaAdsSDK.init(this);
		AdView adView = (AdView) findViewById(R.id.adview);
		adView.loadAd(new AdRequest(this),bannerCid,false);
		adView.setAdListener(new AdListener() {
			
			@Override
			public void onAdLoaded() {
				Log.i("MAINPORTRAIT", "onAdLoaded-banner");
			}
			@Override
			public void onAdClicked() {
				Log.i("MAINPORTRAIT", "onAdClick-banner");
			}
			@Override
			public void onAdFailedToLoad(String message) {
				Toast.makeText(MainPortraitActivity.this, message, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onAdClose() {
				
			}
		});
		Button btn1 = (Button) findViewById(R.id.openScreenAds);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InterstitialAd fs = new InterstitialAd(getApplicationContext());
				fs.loadAd(new AdRequest(getApplicationContext()),screenCid,false);
				fs.setAdListener(new AdListener() {
					
					@Override
					public void onAdLoaded() {
						Log.i("MAINPORTRAIT", "onAdLoaded-FullScreen");
					}
					@Override
					public void onAdClicked() {
						Log.i("MAINPORTRAIT", "onAdClick-FullScreen");
					}
					@Override
					public void onAdFailedToLoad(String message) {
						Toast.makeText(MainPortraitActivity.this, message, Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onAdClose() {
						Log.i("MAINPORTRAIT", "onAdClose");
					}
				});
			}
		});

		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OfferWall ow = new OfferWall(getApplicationContext());
				ow.setCustomTitle("Adsota offer wall");
				ow.setAdListener(new AdListener() {
					
					@Override
					public void onAdLoaded() {
						Log.e("MAINPORTRAIT", "onAdLoaded-Offerwall");
					}
					@Override
					public void onAdClicked() {
						Log.e("MAINPORTRAIT", "onAdClick-OfferWall");
					}
					@Override
					public void onAdFailedToLoad(String message) {
						Toast.makeText(MainPortraitActivity.this, message, Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onAdClose() {
						Log.e("MAINPORTRAIT", "onAdClose-Offerwall");
					}
				});
				ow.loadAd(new AdRequest(MainPortraitActivity.this),offerCid,false,"MaiSoTet","MaiTaGet");
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AppotaAdsSDK.active(this);
	}
}
