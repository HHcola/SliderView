package com.example.slider;

import java.util.List;

import com.example.commonuidemo.R;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BannerSliderAdapter extends PagerAdapter {

	private final static String TAG = BannerSliderAdapter.class.getSimpleName();
	private final LayoutInflater layoutInflater;
	private final Context context;
	private final List<Integer> bannerInfo;
	
	public BannerSliderAdapter(Context context, List<Integer> bannerInfo) {
		this.context = context;
		this.bannerInfo = bannerInfo;
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		if (bannerInfo == null || bannerInfo.size() == 0 ) {
			return 0;
		} else {
			int size = bannerInfo.size() + 2 ;
			Log.d(TAG, "getCount size :" + size);
			return size; // why +2
		}
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
    	Log.d(TAG, "instantiateItem :" + position);
        final FrameLayout viewParent = (FrameLayout) layoutInflater.inflate(R.layout.viewpager_banner_item_view, null);
        ImageView view = (ImageView) viewParent.findViewById(R.id.iv_banner);
        final int pos = getRealPosition(position);
        final int resID = bannerInfo.get(pos);
        if (resID != 0) {
        	view.setImageResource(resID);
        } else {
        	view.setVisibility(View.GONE);
        }
        
        container.addView(viewParent);
		return viewParent;
    	
    }
    
    
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	   @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((View) object);
	}

    public int getRealPosition(int position) {
        int realPosition = 0;
        int size = getBannerListSize();
        if (position == 0) {
            realPosition = size - 1;
        } else if (position == size + 1) {
            realPosition = 0;
        } else {
            realPosition = position - 1;
        }

        return realPosition;
    }
    
    public int getBannerListSize() {
        if (bannerInfo == null) {
            return 0;
        }
        return bannerInfo.size();
    }

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	

    
}
