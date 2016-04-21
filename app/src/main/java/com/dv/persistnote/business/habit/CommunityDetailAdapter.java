package com.dv.persistnote.business.habit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        GeneralCommunityCard card = null;
        if(view == null) {
            card = new GeneralCommunityCard(ContextManager.getContext());
        } else {
            card = (GeneralCommunityCard)view;
        }

        card.enableSpacing(i != 0);
        card.bindData(CommunityModel.getInstance().getCommunityData(i));
        return card;
    }
}
