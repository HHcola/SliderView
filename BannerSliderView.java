package com.example.slider;

import com.example.commonuidemo.R;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BannerSliderView extends FrameLayout implements OnPageChangeListener{
	private final static String TAG = BannerSliderView.class.getSimpleName();
	private Context content;
	private ViewPager viewPager;
	private RelativeLayout hintLayout;
	private BannerSliderAdapter bannerAdapter;
	private Handler  hander = new Handler();
	private int currentPageItem = 0;
	private final int SLIDER_DELAY = 3000;
	
	public BannerSliderView(Context context) {
		super(context);
		this.content = context;
	}
	
	
	public BannerSliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.content = context;
	}
	
	public BannerSliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.content = context;
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		viewPager = (ViewPager)findViewById(R.id.viewpage_banner);
		hintLayout = (RelativeLayout)findViewById(R.id.rl_banner_num);
		viewPager.setOnPageChangeListener(this);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		try {
			boolean ret = super.dispatchTouchEvent(ev);
			if(ret) {
				((ViewGroup)viewPager.getParent()).requestDisallowInterceptTouchEvent(true);
//				ViewParent parent = getParent();
//				while(parent != null) {
//					parent.requestDisallowInterceptTouchEvent(true);
//					parent = parent.getParent();
//				}
			}
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				// start slider
				startSliding();
			} else {
				// stop slider 
				stopSliding();
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * start slider
	 */
	public void startSliding(){
		if (bannerAdapter == null || bannerAdapter.getBannerListSize() <= 1) {
			return;
		}
		
		if (hander != null) {
			hander.postDelayed(slidingRunnable, SLIDER_DELAY);
		}
	}
	
    /**
     * 停止轮播
     */
    public void stopSliding() {
        if (hander != null) {
            hander.removeCallbacks(slidingRunnable);
        }
    }
    
	private Runnable slidingRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (viewPager != null) {
				int index = viewPager.getCurrentItem();
				viewPager.setCurrentItem( ++index);
				if (hander !=null) {
					hander.postDelayed(slidingRunnable, SLIDER_DELAY);
				}
			}			
		}
	};
	public void setAdapter(BannerSliderAdapter adapter) {
		this.bannerAdapter = adapter;
		viewPager.setAdapter(adapter);
		setBannerNum(hintLayout, bannerAdapter.getBannerListSize());

		currentPageItem = 1;
		viewPager.setCurrentItem(currentPageItem, false);
		bannerAdapter.notifyDataSetChanged();
		startSliding();
	}
	
	private void setBannerNum(RelativeLayout layout, int size) {
		if (bannerAdapter.getBannerListSize() <= 1) {
			return ;
		}
		
		layout.removeAllViews();
		ImageView adSelectIv;
		RelativeLayout.LayoutParams params;
		for(int i = 0; i < size; i ++) {
			adSelectIv = new ImageView(content);
			params = new RelativeLayout.LayoutParams(13, 13);
			params.leftMargin = 5 + 20 *i;
			adSelectIv.setLayoutParams(params);
			adSelectIv.setBackgroundResource(R.drawable.banner_num_bg);
			layout.addView(adSelectIv);
		}
		
		layout.getChildAt(bannerAdapter.getRealPosition(currentPageItem)).setSelected(true);
	}
	
	private void finish(){
		if (viewPager != null) {
			viewPager.removeAllViews();
		}
		
		if(bannerAdapter != null) {
			
		}
		
		onDetachedFromWindow();
	}
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		Log.d(TAG, "onPageScrollStateChanged arg0" + arg0);
		if (arg0 == viewPager.SCROLL_STATE_IDLE) {
			if (viewPager != null && bannerAdapter != null) {
				int size = bannerAdapter.getBannerListSize();
				if (currentPageItem == 0) {
					currentPageItem = size;
					hander.post(new Runnable() {
						
						@Override
						public void run() {
							viewPager.setCurrentItem(currentPageItem, false);
						}
					});
					return ;
				}
				
				if (currentPageItem == size +1) {
					currentPageItem = 1;
					hander.post(new Runnable() {
						
						@Override
						public void run() {
							viewPager.setCurrentItem(currentPageItem, false);
						}
					});
					return ;
				}
				
				viewPager.setCurrentItem(currentPageItem, false);
			}
		}
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}


	@Override
	public void onPageSelected(int arg0) {
		currentPageItem = arg0;
		if (hintLayout != null) {
			int index = bannerAdapter.getRealPosition(currentPageItem);
			for(int i = 0; i < hintLayout.getChildCount(); i ++) {
				if (i == index) {
					hintLayout.getChildAt(index).setSelected(true);
				} else {
					hintLayout.getChildAt(i).setSelected(false);
				}
			}
		}
		
	}

	@Override
	protected void onDetachedFromWindow() {
		if (hander != null) {
			hander.removeCallbacks(slidingRunnable);
			hander = null;
		}
		
		super.onDetachedFromWindow();
	}
}
