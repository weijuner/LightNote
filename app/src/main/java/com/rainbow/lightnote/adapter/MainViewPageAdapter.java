package com.rainbow.lightnote.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.rainbow.lightnote.ui.view.JazzyViewPager;

import java.util.List;

/**
 * Created by weijuner on 2015/9/8.
 */
public class MainViewPageAdapter extends PagerAdapter {
    private List<View> list;
    JazzyViewPager mJazzy;
    public MainViewPageAdapter(List<View> list,JazzyViewPager mJazzy) {
        this.list = list;
        this.mJazzy = mJazzy;
    }

    @Override
    public int getCount() {

        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        mJazzy.setObjectForPosition(list.get(position), position);
        return list.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
