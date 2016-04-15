package com.dv.persistnote.business.habit;

import android.content.Context;

import com.dv.persistnote.FakeDataHelper;

import java.util.ArrayList;

/**
 * Created by Hang on 2016/4/3.
 */
public class CommunityModel {

    private static CommunityModel mInstance;

    ArrayList<CommunityData> mDataList = new ArrayList<>();

    public static CommunityModel getInstance() {
        if (mInstance == null) {
            mInstance = new CommunityModel();
        }
        return mInstance;
    }


    public int getCommunityCount() {
        return FakeDataHelper.COMMUTIY_COUNT;
    }

    public CommunityData getCommunityData(int i) {
        return mDataList.get(i);
    }

    public static class CommunityData {

    }
}
