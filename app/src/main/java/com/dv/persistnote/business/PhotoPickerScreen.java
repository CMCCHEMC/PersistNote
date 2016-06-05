package com.dv.persistnote.business;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.HardwareUtil;
import com.dv.persistnote.base.util.Utilities;
import com.dv.persistnote.business.account.FolderAdapter;
import com.dv.persistnote.business.account.Photo;
import com.dv.persistnote.business.account.PhotoAdapter;
import com.dv.persistnote.business.account.PhotoFolder;
import com.dv.persistnote.business.account.PhotoUtils;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

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

    public final static int ACCOUNT_CONTROLLER = 0;
    public final static int ROOT_CONTROLLER = 1;

    private int which;

    private final static String ALL_PHOTO = "所有图片";

    private Map<String, PhotoFolder> mFolderMap;
    private List<Photo> mPhotoLists = new ArrayList<Photo>();
    private ArrayList<String> mSelectList = new ArrayList<String>();
    private PhotoAdapter mPhotoAdapter;

    private FrameLayout mContainer;
    private GridView mGridView;
    private ViewStub mViewStub;
    private ListView mFolderListView;
    private View mFirstDivider;

    /** 文件夹列表是否处于显示状态 */
    boolean mIsFolderViewShow = false;
    /** 文件夹列表是否被初始化，确保只被初始化一次 */
    boolean mIsFolderViewInit = false;

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
        LayoutParams lpTC1V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_et_padding_top), ResTools.getDimenInt(R.dimen.common_et_padding_top));
        lpTC1V1.addRule(CENTER_IN_PARENT);

        mCloseButtonEZTouch.addView(mCloseButton, lpTC1V1);

        mCloseButtonEZTouch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (which) {
                    case ACCOUNT_CONTROLLER:
                        mCallBacks.handleAction(ActionId.OnAccountPhotoPickerClose, null, null);
                        break;
                    case ROOT_CONTROLLER:
                        mCallBacks.handleAction(ActionId.OnRootPhotoPickerClose, null, null);
                        break;
                    default:
                        break;
                }
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
        mTitleText.setText(ResTools.getString(R.string.all_photo));
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

        mViewStub = (ViewStub) findViewById(R.id.folder_stub);
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
        mPhotoLists.addAll(mFolderMap.get(ALL_PHOTO).getPhotoList());

        mPhotoAdapter = new PhotoAdapter(getContext(), mPhotoLists);
        mGridView.setAdapter(mPhotoAdapter);
        Set<String> keys = mFolderMap.keySet();
        final List<PhotoFolder> folders = new ArrayList<PhotoFolder>();
        for (String key : keys) {
            if (ALL_PHOTO.equals(key)) {
                PhotoFolder folder = mFolderMap.get(key);
                folder.setIsSelected(true);
                folders.add(0, folder);
            }else {
                folders.add(mFolderMap.get(key));
            }
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPhotoAdapter.isShowCamera() && position == 0) {
                    showCamera();
                    return;
                }
                switch (which) {
                    case ACCOUNT_CONTROLLER:
                        mCallBacks.handleAction(ActionId.OnAccountPhotoPickerCommit, mPhotoAdapter.getItem(position).getPath(), null);
                        break;
                    case ROOT_CONTROLLER:
                        mCallBacks.handleAction(ActionId.OnRootPhotoPickerCommit, mPhotoAdapter.getItem(position).getPath(), null);
                        break;
                    default:
                        break;
                }
            }
        });
        mTitleEZTouch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFolderList(folders);
            }
        });
    }

    /**
     * 显示或者隐藏文件夹列表
     * @param folders
     */
    private void toggleFolderList(final List<PhotoFolder> folders) {
        //初始化文件夹列表
        if(!mIsFolderViewInit) {
            mViewStub.inflate();
            mFirstDivider = findViewById(R.id.first_divider);
            View dimLayout = findViewById(R.id.dim_layout);
            mFolderListView = (ListView) findViewById(R.id.listview_folder);
            final FolderAdapter adapter = new FolderAdapter(getContext(), folders);
            mFolderListView.setAdapter(adapter);
            mFolderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (PhotoFolder folder : folders) {
                        folder.setIsSelected(false);
                    }
                    PhotoFolder folder = folders.get(position);
                    folder.setIsSelected(true);
                    adapter.notifyDataSetChanged();

                    mPhotoLists.clear();
                    mPhotoLists.addAll(folder.getPhotoList());
                    if (ALL_PHOTO.equals(folder.getName())) {
                        mPhotoAdapter.setIsShowCamera(true);
                        mTitleText.setText(ResTools.getString(R.string.all_photo));
                    } else {
                        mPhotoAdapter.setIsShowCamera(false);
                        mTitleText.setText(folder.getName());
                    }
                    //这里重新设置adapter而不是直接notifyDataSetChanged，是让GridView返回顶部
                    mGridView.setAdapter(mPhotoAdapter);
                    toggle();
                }
            });
            dimLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mIsFolderViewShow) {
                        toggle();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            initAnimation(dimLayout);
            mIsFolderViewInit = true;
        }
        toggle();
    }

    /**
     * 弹出或者收起文件夹列表
     */
    private void toggle() {
        if(mIsFolderViewShow) {
            outAnimatorSet.start();
            if(mFirstDivider != null)
                mFirstDivider.setVisibility(View.GONE);
            mIsFolderViewShow = false;
        } else {
            inAnimatorSet.start();
            if(mFirstDivider != null)
                mFirstDivider.setVisibility(View.VISIBLE);
            mIsFolderViewShow = true;
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
        transInAnimator = ObjectAnimator.ofFloat(mFolderListView, "translationY", -2*height , 0);
        transOutAnimator = ObjectAnimator.ofFloat(mFolderListView, "translationY", 0, -2*height);

        LinearInterpolator linearInterpolator = new LinearInterpolator();

        inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
        inAnimatorSet.setDuration(300);
        inAnimatorSet.setInterpolator(linearInterpolator);
        outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
        outAnimatorSet.setDuration(300);
        outAnimatorSet.setInterpolator(linearInterpolator);
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
            mFolderMap = PhotoUtils.getPhotos();
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
        switch (which) {
            case ACCOUNT_CONTROLLER:
                mCallBacks.handleAction(ActionId.OnAccountShowCamera, null, null);
                break;
            case ROOT_CONTROLLER:
                mCallBacks.handleAction(ActionId.OnRootShowCamera, null, null);
                break;
            default:
                break;
        }

    }
}
