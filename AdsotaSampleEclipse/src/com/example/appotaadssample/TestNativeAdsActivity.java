package com.example.appotaadssample;

import java.util.ArrayList;

import com.appota.ads.ADNative;
import com.appota.ads.AppotaAdsSDK;
import com.appota.ads.entity.ADInterstitialObject;
import com.appota.ads.entity.ADObject;
import com.appota.ads.entity.ADOfferWallObject;
import com.appota.ads.entity.ADViewObject;
import com.bumptech.glide.Glide;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class TestNativeAdsActivity extends Activity implements OnClickListener{
	private ImageView myCustomBannerAds;
	private ImageView myCustomFullScreenAds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<ADObject> ads = getIntent().getExtras().getParcelableArrayList("data");
		Log.i("TestActivity", "pass array length:"+ads.size());
		setContentView(R.layout.test_ads_native_layout);
		
		myCustomBannerAds = (ImageView) findViewById(R.id.myCustomBannerAds);
		myCustomFullScreenAds = (ImageView) findViewById(R.id.myCustomFullScreenAds);
		ADObject adObject = ads.get(0);
		if(adObject instanceof ADViewObject){
			String imageUrl = ((ADViewObject) adObject).getImageUrl();
			Glide.with(this).load(imageUrl).into(myCustomBannerAds);
			myCustomBannerAds.setOnClickListener(this);
			myCustomBannerAds.setTag(adObject);
		}else if(adObject instanceof ADInterstitialObject){
			String imageUrl = ((ADInterstitialObject) adObject).getImageUrl();
			Glide.with(this).load(imageUrl).into(myCustomFullScreenAds);
			myCustomFullScreenAds.setOnClickListener(this);
			myCustomFullScreenAds.setTag(adObject);
		}else if(adObject instanceof ADOfferWallObject){
			
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myCustomBannerAds:
			doProcessClickMyAds((ADObject) myCustomBannerAds.getTag());
			break;
		case R.id.myCustomFullScreenAds:
			doProcessClickMyAds((ADObject) myCustomFullScreenAds.getTag());
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

	private void doProcessClickMyAds(ADObject adObject) {
		ADNative.click(adObject);
	}
}
