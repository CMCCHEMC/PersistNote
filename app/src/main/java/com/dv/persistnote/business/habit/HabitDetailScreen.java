package com.dv.persistnote.business.habit;

import android.content.Context;
import android.widget.ListView;

import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

/**
 * Created by Hang on 2016/4/3.
 */
public class HabitDetailScreen extends DefaultScreen{

    private ListView mDetailListView;

//    private

    public HabitDetailScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
    }

    @Override
    protected void init() {
        super.init();

    }
}
