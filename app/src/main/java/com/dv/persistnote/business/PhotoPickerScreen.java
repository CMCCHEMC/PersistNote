package com.dv.persistnote.business;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.HardwareUtil;
import com.dv.persistnote.base.util.Utilities;
import com.dv.persistnote.business.account.FloderAdapter;
import com.dv.persistnote.business.account.Photo;
import com.dv.persistnote.business.account.PhotoAdapter;
import com.dv.persistnote.business.account.PhotoFloder;
import com.dv.persistnote.business.account.PhotoUtils;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by QinZheng on 2016/4/06.
 */
public class PhotoPickerScreen extends AbstractScreen{

    private RelativeLayout mTitleBar;
    private ImageView mCloseButton;
    private RelativeLayout mCloseButtonEZTouch;
    private TextView mTitleText;
    private ImageView mTitleToggle;
    private RelativeLayout mTitleEZTouch;

    public final static String KEY_RESULT = "picker_result";
    public final static int REQUEST_CAMERA = 1;

    public final static int ACCOUNT_CONTROLLER = 0;
    public final static int ROOT_CONTROLLER = 1;

    private int which;

    /** 是否显示相机 */
    public final static String EXTRA_SHOW_CAMERA = "is_show_camera";

    private final static String ALL_PHOTO = "所有图片";


    private Map<String, PhotoFloder> mFloderMap;
    private List<Photo> mPhotoLists = new ArrayList<Photo>();
    private ArrayList<String> mSelectList = new ArrayList<String>();
    private PhotoAdapter mPhotoAdapter;

    private FrameLayout mContainer;
    private GridView mGridView;
    private ViewStub mViewStub;
    private ListView mFloderListView;

    private TextView mPhotoNumTV;
    private TextView mPhotoNameTV;
    private Button mCommitBtn;
    /** 文件夹列表是否处于显示状态 */
    boolean mIsFloderViewShow = false;
    /** 文件夹列表是否被初始化，确保只被初始化一次 */
    boolean mIsFloderViewInit = false;

    public PhotoPickerScreen(Context context, UICallBacks callBacks, int which) {
        super(context, callBacks);
        initTitleBar();
        init();
        this.which = which;
        getPhotosTask.execute();
    }

    public void initTitleBar() {
        mTitleBar = new RelativeLayout(getContext());
        mTitleBar.setId(R.id.photo_picker_tb);
        LayoutParams titleLp = new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.title_bar_height));
        addView(mTitleBar, titleLp);

        mCloseButtonEZTouch = new RelativeLayout(getContext());
        LayoutParams lpTC1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_icon_width), ResTools.getDimenInt(R.dimen.common_icon_width));
        lpTC1.addRule(ALIGN_PARENT_LEFT);
        lpTC1.addRule(CENTER_VERTICAL);

        mTitleBar.addView(mCloseButtonEZTouch, lpTC1);

        mCloseButton = new ImageView(getContext());
        mCloseButton.setImageDrawable(ResTools.getDrawable(R.drawable.icon2_close));
        LayoutParams lpTC1V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_icon_width), ResTools.getDimenInt(R.dimen.common_icon_width));
        lpTC1V1.addRule(CENTER_IN_PARENT);

        mCloseButtonEZTouch.addView(mCloseButton, lpTC1V1);

        mCloseButtonEZTouch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTitleEZTouch = new RelativeLayout(getContext());
        LayoutParams lpTC2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lpTC2.addRule(CENTER_IN_PARENT);

        mTitleBar.addView(mTitleEZTouch, lpTC2);

        mTitleText = new TextView(getContext());
        mTitleText.setId(R.id.photo_picker_tb_tv);
        mTitleText.setTextColor(ResTools.getColor(R.color.c2));
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResTools.getDimenInt(R.dimen.title_bar_text));
        LayoutParams lpTC2V1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpTC2V1.addRule(CENTER_IN_PARENT);

        mTitleEZTouch.addView(mTitleText, lpTC2V1);

        mTitleToggle = new ImageView(getContext());
        mTitleToggle.setImageDrawable(ResTools.getDrawable(R.drawable.icon3_arrow_down));
        LayoutParams lpTC2V2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpTC2V2.addRule(CENTER_VERTICAL);
        lpTC2V2.addRule(RIGHT_OF, R.id.photo_picker_tb_tv);
        lpTC2V2.leftMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);

        mTitleEZTouch.addView(mTitleToggle, lpTC2V2);

        mTitleBar.setBackgroundColor(ResTools.getColor(R.color.c4));
        int padding = ResTools.getDimenInt(R.dimen.common_margin_16);
        mTitleBar.setPadding(padding, 0, padding, 0);
    }

    protected void init() {
        LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentLp.addRule(BELOW, R.id.photo_picker_tb);
        mContainer = new FrameLayout(getContext());
        addView(mContainer, contentLp);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.photo_picker, this, true);

        mViewStub = (ViewStub) findViewById(R.id.floder_stub);
        mGridView = (GridView) findViewById(R.id.photo_gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ViewGroup parent = (ViewGroup) mViewStub.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        parent = (ViewGroup) mGridView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }


        mContainer.addView(mGridView);
        mContainer.addView(mViewStub);
    }

    private void getPhotosSuccess() {
        mPhotoLists.addAll(mFloderMap.get(ALL_PHOTO).getPhotoList());

        mPhotoAdapter = new PhotoAdapter(getContext(), mPhotoLists);
        mGridView.setAdapter(mPhotoAdapter);
        Set<String> keys = mFloderMap.keySet();
        final List<PhotoFloder> floders = new ArrayList<PhotoFloder>();
        for (String key : keys) {
            if (ALL_PHOTO.equals(key)) {
                PhotoFloder floder = mFloderMap.get(key);
                floder.setIsSelected(true);
                floders.add(0, floder);
            }else {
                floders.add(mFloderMap.get(key));
            }
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPhotoAdapter.isShowCamera() && position == 0) {
                    showCamera();
                    return;
                }
                Toast.makeText(getContext(),mPhotoAdapter.getItem(position).getPath().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        mTitleEZTouch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFloderList(floders);
            }
        });
    }

    /**
     * 显示或者隐藏文件夹列表
     * @param floders
     */
    private void toggleFloderList(final List<PhotoFloder> floders) {
        //初始化文件夹列表
        if(!mIsFloderViewInit) {
            mViewStub.inflate();
            View dimLayout = findViewById(R.id.dim_layout);
            mFloderListView = (ListView) findViewById(R.id.listview_floder);
            final FloderAdapter adapter = new FloderAdapter(getContext(), floders);
            mFloderListView.setAdapter(adapter);
            mFloderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (PhotoFloder floder : floders) {
                        floder.setIsSelected(false);
                    }
                    PhotoFloder floder = floders.get(position);
                    floder.setIsSelected(true);
                    mPhotoAdapter.notifyDataSetChanged();

                    mPhotoLists.clear();
                    mPhotoLists.addAll(floder.getPhotoList());
                    if (ALL_PHOTO.equals(floder.getName())) {
                        mPhotoAdapter.setIsShowCamera(true);
                    } else {
                        mPhotoAdapter.setIsShowCamera(false);
                    }
                    //这里重新设置adapter而不是直接notifyDataSetChanged，是让GridView返回顶部
                    mGridView.setAdapter(mPhotoAdapter);
                    mTitleText.setText(floder.getName());
                    toggle();
                }
            });
            dimLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mIsFloderViewShow) {
                        toggle();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            initAnimation(dimLayout);
            mIsFloderViewInit = true;
        }
        toggle();
    }

    /**
     * 弹出或者收起文件夹列表
     */
    private void toggle() {
        if(mIsFloderViewShow) {
            outAnimatorSet.start();
            mIsFloderViewShow = false;
        } else {
            inAnimatorSet.start();
            mIsFloderViewShow = true;
        }
    }

    /**
     * 初始化文件夹列表的显示隐藏动画
     */
    AnimatorSet inAnimatorSet = new AnimatorSet();
    AnimatorSet outAnimatorSet = new AnimatorSet();
    private void initAnimation(View dimLayout) {
        ObjectAnimator alphaInAnimator, alphaOutAnimator, transInAnimator, transOutAnimator;
        //获取actionBar的高
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        actionBarHeight = Utilities.dip2px(ContextManager.getContext(),ResTools.getDimenInt(R.dimen.title_bar_height));

        /**
         * 这里的高度是，屏幕高度减去上、下tab栏，并且上面留有一个tab栏的高度
         * 所以这里减去3个actionBarHeight的高度
         */
        int height = HardwareUtil.getDeviceHeight() - 2*actionBarHeight;
        alphaInAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0f, 0.7f);
        alphaOutAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0.7f, 0f);
        transInAnimator = ObjectAnimator.ofFloat(mFloderListView, "translationY", -2*height , 0);
        transOutAnimator = ObjectAnimator.ofFloat(mFloderListView, "translationY", 0, -2*height);

        LinearInterpolator linearInterpolator = new LinearInterpolator();

        inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
        inAnimatorSet.setDuration(300);
        inAnimatorSet.setInterpolator(linearInterpolator);
        outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
        outAnimatorSet.setDuration(300);
        outAnimatorSet.setInterpolator(linearInterpolator);
    }

    /**
     * 选择文件夹
     * @param photoFloder
     */
    public void selectFloder(PhotoFloder photoFloder) {
        mPhotoAdapter.setDatas(photoFloder.getPhotoList());
        mPhotoAdapter.notifyDataSetChanged();
    }

    /**
     * 获取照片的异步任务
     */
    private AsyncTask getPhotosTask = new AsyncTask() {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] params) {
            mFloderMap = PhotoUtils.getPhotos();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            getPhotosSuccess();
        }
    };

    /**
     * 选择相机
     */
    private void showCamera() {
        // 跳转到系统照相机
        mCallBacks.handleAction(ActionId.ShowCamera, null, null);
    }
}
