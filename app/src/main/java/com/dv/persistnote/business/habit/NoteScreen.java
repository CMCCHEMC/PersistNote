package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.business.habit.calendar.CheckInCalendar;
import com.dv.persistnote.business.share.ShareData;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.UICallBacks;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;

import habit.dao.HabitRecord;

/**
 * Created by Hang on 2016/4/3.
 */
public class NoteScreen extends DefaultScreen implements IUIObserver, View.OnClickListener{

    private LinearLayout mContainer;
    private EditText mEditText;
    private ImageView mAddIcon;
    private SharePreView mPreView;
    private TextView mActionButton;

    public NoteScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setBackgroundColor(ResTools.getColor(R.color.default_grey));
    }

    protected void init() {
        setTitle("记录一下");
        mTitleBar.setActionText("完成");
        mActionButton = (TextView)mTitleBar.getActionButton();

        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollContainer = new ScrollView(getContext());
        setContent(scrollContainer);
        scrollContainer.addView(mContainer);


        mEditText = new EditText(getContext());
        mEditText.setHint("记录一下吧~");
        mEditText.setMaxLines(5);
        mEditText.setGravity(Gravity.TOP);
        mEditText.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEditText.addTextChangedListener(getTextChangedListener());
        int padding = ResTools.getDimenInt(R.dimen.common_margin_16);
        mEditText.setPadding(padding,padding,padding,padding);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ResTools.dpToPx(160));
        lp.topMargin = ResTools.getDimenInt(R.dimen.common_margin_16);
        mContainer.addView(mEditText, lp);

        FrameLayout photoContainer = new FrameLayout(getContext());
        photoContainer.setBackgroundColor(ResTools.getColor(R.color.c4));
        photoContainer.setPadding(padding,padding,padding,padding);
        mAddIcon = new ImageView(getContext());
        mAddIcon.setImageDrawable(ResTools.getDrawable(R.drawable.icon_plus));
        mAddIcon.setOnClickListener(this);
        int iconWidth = (int) ResTools.dpToPx(60);
        photoContainer.addView(mAddIcon, iconWidth, iconWidth);
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ResTools.dpToPx(90));
        mContainer.addView(photoContainer, lp);

        mPreView = (SharePreView)LayoutInflater.from(getContext()).inflate(R.layout.share_preview_layout, null);
        mPreView.initViews();
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ResTools.dpToPx(500));
        lp.leftMargin = lp.rightMargin = ResTools.getDimenInt(R.dimen.common_margin_16);
        lp.topMargin = lp.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_16);
        mContainer.addView(mPreView, lp);
    }

    private TextWatcher getTextChangedListener() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mPreView.setContent(s.toString());
                int color = TextUtils.isEmpty(s) ? ResTools.getColor(R.color.c3) : ResTools.getColor(R.color.c1);
                mActionButton.setTextColor(color);
            }
        };
        return watcher;
    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        boolean handle = true;
        switch (actionId) {
            case ActionId.OnCalendarTouchState:
                break;
            default:
                handle = false;
        }
        if(!handle) {
            mCallBacks.handleAction(actionId, arg, result);
        }
        return handle;
    }

    @Override
    public void onClick(View v) {
        if(v == mAddIcon) {
            mCallBacks.handleAction(ActionId.OnNotePicSelectClick, null, null);
        }
    }

    public void setHabitInfo(HabitRecord habit) {
        mPreView.setPersistCount(habit.getPersistCount());
        mPreView.setHabitName(habit.getHabitName());
    }

    public ShareData getShareData() {
        final ShareData shareData = new ShareData();
        shareData.mTitle = "今天是坚持<天 ";
        shareData.mBitmap = getScaleSnapShot(mPreView, 0.5f);
        return shareData;
    }

    private static Bitmap getScaleSnapShot(View view, float scale) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        view.draw(canvas);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}
