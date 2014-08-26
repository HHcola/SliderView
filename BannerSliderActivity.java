package com.example.slider;

import java.util.ArrayList;
import java.util.List;

import com.example.commonuidemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BannerSliderActivity extends FragmentActivity {

	private final static String TAG = BannerSliderActivity.class.getSimpleName();
	private BannerSliderView bannerSliderView;
	private BannerSliderAdapter bannerSliderAdapter;
	private List<Integer> bannerInfo;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_slider);
		onSetBannerInfo();
		onInitUI();
	}
	
	
	private void onInitUI() {
		bannerSliderView  = (BannerSliderView)findViewById(R.id.banner_slider_view);
		bannerSliderAdapter = new BannerSliderAdapter(this, bannerInfo);
		bannerSliderView.setAdapter(bannerSliderAdapter);
	}
	
	
	private void onSetBannerInfo(){
		bannerInfo = new ArrayList<Integer>();
		bannerInfo.add(R.drawable.banner_one);
		bannerInfo.add(R.drawable.banner_two);
		bannerInfo.add(R.drawable.banner_three);
		bannerInfo.add(R.drawable.banner_four);
	}
}
