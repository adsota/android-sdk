package com.example.appotaadssample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appota.ads.AppotaAdsSDK;

public class ConfigActivity extends Activity {

	private int bannerCid, screenCid, offerCid,orientation;
	private EditText configBanner;
	private EditText configFullScreen;
	private EditText configOfferWall;
	private EditText configVideo;
	private CheckBox orientationConfig;
	private SharedPreferences preferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppotaAdsSDK.init(this);
		
		setContentView(R.layout.config_layout);
		
		configBanner = (EditText)findViewById(R.id.bannerCampainID);
		configFullScreen = (EditText)findViewById(R.id.fullScreenCampaignID);
		configOfferWall = (EditText)findViewById(R.id.offerWallCampaignID);
		preferences = getPreferences(MODE_PRIVATE);
		orientationConfig = (CheckBox)findViewById(R.id.orientationScreen);
		
		Button startAds = (Button)findViewById(R.id.startAds);
		startAds.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bannerCid = Integer.parseInt(configBanner.getText().toString());
				screenCid = Integer.parseInt(configFullScreen.getText().toString());
				offerCid = Integer.parseInt(configOfferWall.getText().toString());
				Intent intent = null;
				if(orientationConfig.isChecked()){
					orientation = 1;
					intent = new Intent(ConfigActivity.this,MainPortraitActivity.class);
				}else {
					orientation = 0;
					intent = new Intent(ConfigActivity.this,MainLandscapeActivity.class);
				}
				intent.putExtra("b", bannerCid);
				intent.putExtra("s", screenCid);
				intent.putExtra("o", offerCid);
				intent.putExtra("ori", orientation);
				startActivity(intent);
			}
		});
		
	}
}
