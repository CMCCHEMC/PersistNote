package com.dv.persistnote.business;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.SystemUtil;
import com.dv.persistnote.base.util.Utilities;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.CircleView;
import com.dv.persistnote.framework.ui.SpreadCircleView;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.lang.reflect.Field;

/**
 * Created by QinZheng on 2016/4/5.
 */
public class RegisterUserInfoScreen extends DefaultScreen implements View.OnClickListener{

    private RelativeLayout mContainer;

    private RelativeLayout mContainerUserName;

    private RelativeLayout mContainerUserSex;

    private RelativeLayout mContainerOKButton;

    private RelativeLayout mContainerUserNameRemoveEZTouch;

    private EditText mEtUserName;

    private View mLineUserName;

    private TextView mWrongUserName;

    private View mBackgroundMale;

    private View mBackgroundFemale;

    private ImageView mIvMale;

    private ImageView mIvFemale;

    private ImageView mRemoveUserName;

    private CircleView mOkButton;

    private ImageView mOkButtonArrow;

    private SpreadCircleView mAnimation;

    private boolean mIsOkButtonAvailable = false;

    private boolean mIsRemoveUserName = false;

    private int mUserSex = 0;

    public RegisterUserInfoScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** mContainerUserName ******************/

        mContainerUserName = new RelativeLayout(getContext());
        mContainerUserName.setId(R.id.register_u_rl_user_name);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_user_name_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        mEtUserName = new EditText(getContext());
        mEtUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtUserName.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtUserName.setId(R.id.register_u_et_user_name);
        mEtUserName.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtUserName.setHint(R.string.register_u_et_hint_user_name);
        mEtUserName.setSingleLine(true);
        mEtUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtUserName.setHint(R.string.common_et_hint_phone_number);
                    mRemoveUserName.setVisibility(View.GONE);
                    mIsRemoveUserName = false;
                } else {
                    mEtUserName.setHint(null);
                    if (mEtUserName.getText().toString().length() > 0)
                        mRemoveUserName.setVisibility(View.VISIBLE);
                        mIsRemoveUserName = true;
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtUserName, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }
        mEtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (s.toString().length() > 0) {
                   mRemoveUserName.setVisibility(View.VISIBLE);
                   mIsRemoveUserName = true;
                   if (mUserSex > 0) {
                       mOkButton.setAlpha(1.0f);
                       mIsOkButtonAvailable = true;
                   }
               } else {
                   mRemoveUserName.setVisibility(View.GONE);
                   mIsRemoveUserName = false;
                   mOkButton.setAlpha(0.3f);
                   mIsOkButtonAvailable = false;
               }
            }
        });

        mEtUserName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_remove_width);

        mContainerUserName.addView(mEtUserName, lpC1V1);

        mLineUserName = new View(getContext());
        mLineUserName.setId(R.id.register_u_line_user_name);
        mLineUserName.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_u_et_user_name);

        mContainerUserName.addView(mLineUserName, lpC1V2);

        /** EZ Touch start **/

        mContainerUserNameRemoveEZTouch = new RelativeLayout(getContext());
        mContainerUserNameRemoveEZTouch.setId(R.id.register_u_rl_user_name_remove);
        mContainerUserNameRemoveEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mRemoveUserName = new ImageView(getContext());
        mRemoveUserName.setId(R.id.register_u_iv_password_remove);
        mRemoveUserName.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemoveUserName.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC1C1V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);

        mContainerUserNameRemoveEZTouch.addView(mRemoveUserName, lpC1C1V1);

        mContainerUserName.addView(mContainerUserNameRemoveEZTouch, lpC1C1);

        /** EZ Touch end **/

        mContainer.addView(mContainerUserName, lpC1);

        /************** mContainerUserSex ******************/

        mContainerUserSex = new RelativeLayout(getContext());
        mContainerUserSex.setId(R.id.register_u_rl_user_sex);

        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.register_u_rl_user_name);

        mBackgroundMale = new View(getContext());
        mBackgroundMale.setId(R.id.register_u_v_user_sex_male);
        mBackgroundMale.setBackgroundColor(Color.parseColor("#7dd8ef"));
        mBackgroundMale.setAlpha(0.3f);
        mBackgroundMale.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height),
                        ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2V1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        mContainerUserSex.addView(mBackgroundMale, lpC2V1);

        mIvMale = new ImageView(getContext());
        mIvMale.setImageDrawable(ResTools.getDrawable(R.drawable.male));
        mIvMale.setId(R.id.register_u_iv_user_sex_male);

        RelativeLayout.LayoutParams lpC2V2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height),
                ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height));
        lpC2V2.addRule(RelativeLayout.CENTER_VERTICAL);
        lpC2V2.leftMargin = ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height);

        mContainerUserSex.addView(mIvMale, lpC2V2);

        mBackgroundFemale = new View(getContext());
        mBackgroundFemale.setId(R.id.register_u_v_user_sex_female);
        mBackgroundFemale.setBackgroundColor(Color.parseColor("#f9a7b8"));
        mBackgroundFemale.setAlpha(0.3f);
        mBackgroundFemale.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height),
                ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerUserSex.addView(mBackgroundFemale, lpC2V3);

        mIvFemale = new ImageView(getContext());
        mIvFemale.setImageDrawable(ResTools.getDrawable(R.drawable.female));
        mIvFemale.setId(R.id.register_u_iv_user_sex_female);

        RelativeLayout.LayoutParams lpC2V4 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height),
                ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height));
        lpC2V4.addRule(RelativeLayout.CENTER_VERTICAL);
        lpC2V4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2V4.rightMargin = ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height);

        mContainerUserSex.addView(mIvFemale, lpC2V4);

        mContainer.addView(mContainerUserSex, lpC2);

        /************** mContainerOKButton ******************/

        mContainerOKButton = new RelativeLayout(getContext());
        mContainerOKButton.setId(R.id.register_u_rl_ok);
        mContainerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.register_u_rl_user_sex);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mOkButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButton.setId(R.id.register_u_v_ok);
        mOkButton.setColor(ResTools.getColor(R.color.c1));
        mOkButton.setAlpha(0.3f);

        mContainerOKButton.addView(mOkButton);

        mOkButtonArrow = new ImageView(getContext());
        mOkButtonArrow.setId(R.id.register_u_iv_ok);
        mOkButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        mContainerOKButton.addView(mOkButtonArrow, lpC3V1);

        mContainer.addView(mContainerOKButton, lpC3);

        /************** mWrongUserName ******************/

        mWrongUserName = new TextView(getContext());
        mWrongUserName.setId(R.id.register_u_tv_user_name_wrong);
        mWrongUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongUserName.setTextColor(ResTools.getColor(R.color.c10));
        mWrongUserName.setText(null);

        RelativeLayout.LayoutParams lpV1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV1.addRule(RelativeLayout.BELOW, R.id.register_u_rl_user_name);
        lpV1.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongUserName, lpV1);

    }

    @Override
    public void onClick(View v) {
        if (v == mContainerOKButton) {
            if(mIsOkButtonAvailable) {
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

                float width = wm.getDefaultDisplay().getWidth()/2;

                float height = Utilities.dip2px(getContext(), 390f);

                mAnimation = new SpreadCircleView(getContext(), width, height, mCallBacks);

                addView(mAnimation);
            }
        }
        if (v == mContainerUserNameRemoveEZTouch) {
            if (mIsRemoveUserName)
                mEtUserName.setText(null);
        }
        if (v == mBackgroundMale) {
            if (mUserSex == 2) {
                mBackgroundFemale.setAlpha(0.3f);
            }
            mUserSex = 1;
            mBackgroundMale.setAlpha(1.0f);
            if (!mIsOkButtonAvailable && mEtUserName.getText().toString().length() > 0) {
                mIsOkButtonAvailable = true;
                mOkButton.setAlpha(1.0f);
            }
        }
        if (v == mBackgroundFemale) {
            if (mUserSex == 1) {
                mBackgroundMale.setAlpha(0.3f);
            }
            mUserSex = 2;
            mBackgroundFemale.setAlpha(1.0f);
            if (!mIsOkButtonAvailable && mEtUserName.getText().toString().length() > 0) {
                mIsOkButtonAvailable = true;
                mOkButton.setAlpha(1.0f);
            }
        }
    }
}
