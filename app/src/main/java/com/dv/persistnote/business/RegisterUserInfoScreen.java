package com.dv.persistnote.business;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.CircleView;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.lang.reflect.Field;

/**
 * Created by Admin2 on 2016/4/5.
 */
public class RegisterUserInfoScreen extends DefaultScreen implements View.OnClickListener{

    private RelativeLayout mContainer;

    private RelativeLayout containerUserName;

    private RelativeLayout containerUserSex;

    private RelativeLayout containerOKButton;

    private EditText etUserName;

    private View lineUserName;

    private View backgroundMale;

    private View backgroundFemale;

    private ImageView ivMale;

    private ImageView ivFemale;

    private CircleView okButton;

    private ImageView okButtonArrow;

    public RegisterUserInfoScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** containerUserName ******************/

        containerUserName = new RelativeLayout(getContext());
        containerUserName.setId(R.id.register_u_rl_user_name);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_user_name_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        etUserName = new EditText(getContext());
        etUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etUserName.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etUserName.setId(R.id.register_u_et_user_name);
        etUserName.setBackgroundColor(ResTools.getColor(R.color.c4));
        etUserName.setHint(R.string.register_u_et_hint_user_name);
        etUserName.setSingleLine(true);
        etUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etUserName.setHint(R.string.common_et_hint_phone_number);
                } else {
                    etUserName.setHint(null);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etUserName, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerUserName.addView(etUserName, lpC1V1);

        lineUserName = new View(getContext());
        lineUserName.setId(R.id.register_u_line_user_name);
        lineUserName.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_u_et_user_name);

        containerUserName.addView(lineUserName, lpC1V2);

        mContainer.addView(containerUserName, lpC1);

    }

    @Override
    public void onClick(View v) {

    }
}
