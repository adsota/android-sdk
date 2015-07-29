#  Appota Ads SDK for Android

[1. Import Appota Ads SDK](#1-import-appota-ads-sdk)

[2. Set up Appota Ads SDK](#2-set-up-appota-ads-sdk)

[2.1. Configure SDK](#21-configure-sdk)

[2.2. Load Ads](#22-load-ads)

[2.3. Load Native Ads](#23-load-native-ads)

## [1. Import Appota Ads SDK]()

- Import Ads SDK into your project:

	- In Eclipse: copy jar files ( [appota_ads_publishers.jar](https://github.com/adsota/android-sdk/blob/master/appota_ads_publishers.jar) ,[play-services-analytics_7.5.0.jar](https://github.com/adsota/android-sdk/blob/master/AdsotaSampleEclipse/libs/play-services-analytics_7.5.0.jar) & [play-services-base_7.5.0.jar](https://github.com/adsota/android-sdk/blob/master/AdsotaSampleEclipse/libs/play-services-base_7.5.0.jar) ) into libs folder. If you're using play services(includes analytics), you don't have to import play-services-base_7.5.0.jar & play-services-analytics_7.5.0.jar. 

	- In Studio: 
		- Without Google analytics library:
		
			Copy jar files (appota_ads_publishers.jar,play-services-base_7.5.0.jar & play-services-analytics_7.5.0.jar) into libs of module & configure build.grandle like below:

			`dependencies {`

    		`compile fileTree(dir: 'libs', include: ['*.jar'])`

    		**compile files('libs/appota_ads_publishers.jar')**

			**compile files('libs/play_services_analytics_7.5.0.jar')**

    		**compile files('libs/play_services_base_7.5.0.jar')**

			...

			`}`

		- With Gooogle analytics library set up in your project:
			
			Copy only file appota_ads_publishers.jar into libs folder of module & configure build.grandle like below:

			`dependencies {`

    		**compile files('libs/appota_ads_publishers.jar')**

			...

			`}`

## [2. Set up Appota Ads SDK]()

## [2.1. Configure SDK]()

### [2.1.a Configure in Manifest.xml]()

		<activity
            android:name="com.appota.ads.view.AdActivity"
            android:taskAffinity="com.appota.ads" />
        
        <activity android:name="com.appota.ads.view.media.VideoAds" />
        
        <activity android:name="com.appota.ads.view.OfferActivity" />
		
		<!-- Appota API Key -->
        <meta-data
            android:name="com.appota.apiKey"
            android:value="your_apikey" />
       
	
		<!-- Using for getting Google's Android advertising ID -->
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="7571000"/>
		<!-- 7571000 is version of Play Service. If you don't use our version play service library(play-services-base_7.5.0.jar & play-services-analytics_7.5.0.jar) & use your play service version,  set value ="@integer/google_play_services_version" -->

### [2.1.b Configure in code]()

	
`AppotaAdsSDK.init(Context)` Call this before calling load ads. Should pass Application Context for initialization & only call one time.

`AppotaAdsSDK.active(Context)` : Override onResume of Activity & call this function. 

## [2.2. Load Ads]()

### [2.2.a Banner Ad]()

- Declare namespace in xml layout 
	`xmlns:appotaAds="http://schemas.android.com/apk/lib/com.appota.ads"`

		<com.appota.ads.AdView
        android:id="@+id/adview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"/>

- In code:

	AdView adView = (AdView) findViewById(R.id.adview);
	adView.loadAd(new AdRequest(this),String adUnitId,boolean status);

	//status = true -> in publish mode, else development mode for testing.

	* Add callback for loading ads:

			adView.setAdListener(new AdListener() {
			
				@Override
				public void onAdLoaded() {
				}

				@Override
				public void onAdClicked() {
				}
				
				@Override
				public void onAdClose() {
				}

				@Override
				public void onAdFailedToLoad(String message) {
				}
			});

### [2.2.b Full Screen Ad(Interstitial Ad)]()

	InterstitialAd fs = new InterstitialAd(Context);
	fs.loadAd(new AdRequest(Context),String adUnitId,boolean status);

	//status = true -> in publish mode, else development mode for testing.

	You may add callback for loading ads like banner.

### [2.2.c Offer Wall Ad]()

	OfferWall ow = new OfferWall(Context);
	ow.loadAd(new AdRequest(Context),String adUnitId,boolean status,String state,String target);
	
	//status = true -> in publish mode, else development mode for testing.

	You may add callback for loading ads like banner.

## [2.3. Load Native Ads]()

### 2.3.0 AD Object class

* ADNativeViewObject, ADNativeInterstitialObject, ADNativeOfferWallObject kế thừa từ class ADNativeObject

* ADNativeObject

	getID

	getRating - float

* ADNativeViewObject

	getImageUrl

* ADNativeInterstitialObject

	getImageUrl

	getIconUrl

	getDescription

	getName

* ADNativeOfferWallObject

	getPoint

	getIconUrl

	getName

	getAction

	getDescription

### 2.3.1 Config:

**AppotaAdsSDK.init(Context)** : Call this before calling load ads. Should pass Application Context for initialization & only call one time.


**AppotaAdsSDK.active(Context)** : Override onResume of Activity & call this function.

In the process of development, you may want to get ads for testing,viewing, enable development mode, default is publish mode.

`ADNative.enableDevelopemetMode(true);`

### 2.3.2 Load native ads:

a. Make a request for load native ads:

`ADNativeRequest adNativeRequest =  new ADNativeRequest();`

`adNativeRequest.adType = ADType.ADTypeBanner;`

`adNativeRequest.adUnitID = 10;`

- ADType(com.appota.ads.ADNative.ADType) is an enum type. It includes: ADTypeBanner, ADTypeInterstitial & ADTypeOfferWall.

b. Set callback for request.

- ADNativeRequestCallback(com.appota.ads.ADNative.ADNativeRequestCallback)

adNativeRequest.setRequestCallback(new ADNativeRequestCallback() {
			
			@Override
			public void onLoadAdsComplete(ArrayList<ADNativeObject> listAds) {
				Log.i("onLoadAdsNativeComplete", "listAds size :"+listAds.size());
			}
			
			@Override
			public void onLoadAdsFailed(String message) {
				Log.i("onLoadAdsNativeFailed", message);
			}
			
			
		});


c. Start loading native ads.

`ADNative.loadRequest(adNativeRequest);`

### 2.3.3 Process click native ads:

Once user clicks on ads, call this function for processing click ads:

`ADNative.click(adNativeObject);`
