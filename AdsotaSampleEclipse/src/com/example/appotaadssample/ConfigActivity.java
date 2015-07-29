package com.example.appotaadssample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.appota.ads.ADNative;
import com.appota.ads.ADNative.ADRequestCallback;
import com.appota.ads.ADNative.ADType;
import com.appota.ads.ADNativeRequest;
import com.appota.ads.AppotaAdsSDK;
import com.appota.ads.entity.ADObject;

public class ConfigActivity extends Activity implements OnClickListener{

	private int bannerCid, screenCid, offerCid,orientation;
	private EditText configBanner;
	private EditText configFullScreen;
	private EditText configOfferWall;
	private CheckBox orientationConfig;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppotaAdsSDK.init(this);
		ADNative.enableDevelopemetMode(true);//Default is false - Mode is publish.
		
		setContentView(R.layout.config_layout);
		configBanner = (EditText)findViewById(R.id.bannerCampainID);
		configFullScreen = (EditText)findViewById(R.id.fullScreenCampaignID);
		configOfferWall = (EditText)findViewById(R.id.offerWallCampaignID);
		orientationConfig = (CheckBox)findViewById(R.id.orientationScreen);
		Button startAds = (Button)findViewById(R.id.startAds);
		startAds.setOnClickListener(this);
		//Load Native Ads
		startGettingNativeAds();
		
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.startAds:
			doClickStartAds();
			break;

		default:
			break;
		}
		
	}
	
	private void doClickStartAds() {
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
	
	private void startGettingNativeAds(){
		ADNativeRequest adNativeRequest =  new ADNativeRequest();
		adNativeRequest.adType = ADType.ADTypeBanner;
		adNativeRequest.adUnitID = 46;
		adNativeRequest.setRequestCallback(new ADRequestCallback() {
			
			@Override
			public void onLoadAdsComplete(ArrayList<ADObject> listAds) {
				Log.e("onLoadAdsNativeComplete", "onLoadAdsNativeComplete :"+listAds.size());
				if(listAds != null && listAds.size()>0){
					Intent startAds = new Intent(ConfigActivity.this,TestNativeAdsActivity.class);
					startAds.putParcelableArrayListExtra("data", (ArrayList<ADObject>)listAds);
					startActivity(startAds);
				}
			}
			
			@Override
			public void onLoadAdsFailed(String message) {
				Log.e("onLoadAdsNativeFailed", message);
				Toast.makeText(ConfigActivity.this, message, Toast.LENGTH_LONG).show();
			}
			
			
		});
		ADNative.loadRequest(adNativeRequest);
	}
}
