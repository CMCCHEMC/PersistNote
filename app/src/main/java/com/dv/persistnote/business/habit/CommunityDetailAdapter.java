package com.dv.persistnote.business.habit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Hang on 2016/4/3.
 */
public class CommunityDetailAdapter extends BaseAdapter
{
    @Override
    public int getCount() {
        return CommunityModel.getInstance().getCommunityCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
