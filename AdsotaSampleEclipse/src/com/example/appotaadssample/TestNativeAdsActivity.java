package com.example.appotaadssample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.appota.ads.ADNative;
import com.appota.ads.AppotaAdsSDK;
import com.appota.ads.entity.ADNativeInterstitialObject;
import com.appota.ads.entity.ADNativeObject;
import com.appota.ads.entity.ADNativeOfferWallObject;
import com.appota.ads.entity.ADNativeViewObject;
import com.bumptech.glide.Glide;

public class TestNativeAdsActivity extends Activity implements OnClickListener{
	private ImageView myCustomBannerAds;
	private ImageView myCustomFullScreenAds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<ADNativeObject> ads = getIntent().getExtras().getParcelableArrayList("data");
		Log.i("TestActivity", "pass array length:"+ads.size());
		setContentView(R.layout.test_ads_native_layout);
		
		myCustomBannerAds = (ImageView) findViewById(R.id.myCustomBannerAds);
		myCustomFullScreenAds = (ImageView) findViewById(R.id.myCustomFullScreenAds);
		ADNativeObject ADNativeObject = ads.get(0);
		if(ADNativeObject instanceof ADNativeViewObject){
			String imageUrl = ((ADNativeViewObject) ADNativeObject).getImageUrl();
			Glide.with(this).load(imageUrl).into(myCustomBannerAds);
			myCustomBannerAds.setOnClickListener(this);
			myCustomBannerAds.setTag(ADNativeObject);
		}else if(ADNativeObject instanceof ADNativeInterstitialObject){
			String imageUrl = ((ADNativeInterstitialObject) ADNativeObject).getImageUrl();
			Glide.with(this).load(imageUrl).into(myCustomFullScreenAds);
			myCustomFullScreenAds.setOnClickListener(this);
			myCustomFullScreenAds.setTag(ADNativeObject);
		}else if(ADNativeObject instanceof ADNativeOfferWallObject){
			
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myCustomBannerAds:
			doProcessClickMyAds((ADNativeObject) myCustomBannerAds.getTag());
			break;
		case R.id.myCustomFullScreenAds:
			doProcessClickMyAds((ADNativeObject) myCustomFullScreenAds.getTag());
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AppotaAdsSDK.active(this);
	}

	private void doProcessClickMyAds(ADNativeObject ADNativeObject) {
		ADNative.click(ADNativeObject);
	}
}
