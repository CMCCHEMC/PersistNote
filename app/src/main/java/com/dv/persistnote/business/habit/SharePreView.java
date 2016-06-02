package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;

import org.w3c.dom.Text;

/**
 * Created by Hang on 2016/5/30.
 */
public class SharePreView extends RelativeLayout {

    private ImageView mPhotoView;
    private TextView mDateView;
    private TextView mSourceHabitView;
    private TextView mContentView;
    private TextView mCountView;

    public SharePreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initViews() {
        mPhotoView = (ImageView) findViewById(R.id.preview_photo);
        mContentView = (TextView) findViewById(R.id.preview_content);
        mDateView = (TextView) findViewById(R.id.preview_date);
        mSourceHabitView = (TextView) findViewById(R.id.preview_habit);
        mCountView = (TextView) findViewById(R.id.preview_persist_count);
    }


    public void setBitmapData(Bitmap bitmap) {

    }

    public void setContent(String content) {
        mContentView.setText(content);
    }

    public void setHabitName(String habitName) {
        mSourceHabitView.setText("来自坚持手记#"+habitName+"#");
    }

    public void setDate(String date) {
        mDateView.setText(date);
    }

    public void setPersistCount(int count) {
        mCountView.setText("第"+count+"天");
    }
}
