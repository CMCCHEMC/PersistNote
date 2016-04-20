package com.dv.persistnote.business.habit;

import android.content.Context;
import android.util.Log;

import com.dv.persistnote.FakeDataHelper;
import com.dv.persistnote.base.network.DomainConfig;
import com.dv.persistnote.base.network.TestServiceInterface;
import com.dv.persistnote.base.network.bean.ZHResult;
import com.dv.persistnote.base.util.ThreadManager;
import com.dv.persistnote.business.ConstDef;
import com.dv.persistnote.framework.model.GreenDaoManager;
import com.dv.persistnote.framework.model.IModelObserver;
import com.dv.persistnote.framework.model.ModelId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import habit.dao.CommunityRecord;
import habit.dao.CommunityRecordDao;
import habit.dao.HabitRecordDao;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Hang on 2016/4/3.
 */
public class CommunityModel {

    public static int TYPE_NEW = 0;
    public static int TYPE_HISTORY = 1;

    private static CommunityModel mInstance;

    List<CommunityRecord> mDataList = new ArrayList<>();

    private CommunityRecordDao mCommunityDao;

    public static CommunityModel getInstance() {
        if (mInstance == null) {
            mInstance = new CommunityModel();
        }
        return mInstance;
    }

    CommunityModel() {
        mCommunityDao = GreenDaoManager.getInstance().getSession().getCommunityRecordDao();
    }


    public int getCommunityCount() {
        return mDataList.size();
    }

    public CommunityRecord getCommunityData(int i) {
        return mDataList.get(i);
    }

    /**
     * 指定拉取x条历史数据
     * @param observer
     * @param forceNetwork
     * @param maxcount
     */
    public void startLoadData(final IModelObserver observer, boolean forceNetwork, final int maxcount) {
        testJHRequest("", new Callback<ZHResult>() {
            @Override
            public void success(ZHResult zhResult, Response response) {
                if(mDataList.size() < 30) {
                    List<CommunityRecord> list = CommunityDataProcesser.convertNetDataToBean(zhResult);
                    if(list.size() > maxcount) {
                        list = list.subList(0, maxcount - 1);
                    }
                    mDataList.addAll(list);
                    observer.handleData(ModelId.OnCommunityLoaded, null, null);
                } else {
                    observer.handleData(ModelId.OnCommunityNoMore, null, null);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void startLoadData(final IModelObserver observer, boolean forceNetwork) {
        ThreadManager.post(ThreadManager.THREAD_WORK, new Runnable() {
            @Override
            public void run() {
                mDataList = mCommunityDao.loadAll();

            }
        }, new Runnable() {
            @Override
            public void run() {
                if (mDataList == null || mDataList.size() <= 0) {
                    loadNetWork(observer);
                }
            }
        });
    }

    private void loadNetWork(final IModelObserver observer) {
        testJHRequest("", new Callback<ZHResult>() {
            @Override
            public void success(ZHResult zhResult, Response response) {
                mDataList = CommunityDataProcesser.convertNetDataToBean(zhResult);
                postSaveLocal();
                observer.handleData(ModelId.OnCommunityLoaded, TYPE_HISTORY, null);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void postSaveLocal() {

    }

    public void testJHRequest(String srcStr, Callback<ZHResult> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder().
                setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(DomainConfig.MAIN_DOMAIN).build();

        TestServiceInterface serviceInterface = restAdapter.create(TestServiceInterface.class);

        HashMap<String , String> params = new HashMap<>();
        params.put("info", srcStr);
        serviceInterface.getZHResult(params, callback);

    }

    public void startRefresh(final IModelObserver observer) {
        testJHRequest("", new Callback<ZHResult>() {
            @Override
            public void success(ZHResult zhResult, Response response) {
                List<CommunityRecord> list = CommunityDataProcesser.convertNetDataToBean(zhResult);
                int i = (int) (System.currentTimeMillis() % list.size());
                mDataList.add(0, list.get(i));
                observer.handleData(ModelId.OnCommunityLoaded, TYPE_NEW, null);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
