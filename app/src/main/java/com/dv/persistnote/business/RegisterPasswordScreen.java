package com.dv.persistnote.business;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.CircleView;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.lang.reflect.Field;

/**
 * Created by QinZheng on 2016/4/5.
 */
public class RegisterPasswordScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout mContainerPassword1;

    private RelativeLayout mContainerPassword2;

    private RelativeLayout mContainerOKButton;

    private RelativeLayout mContainerPasswordHideEZTouch1;

    private RelativeLayout mContainerPasswordRemoveEZTouch1;

    private RelativeLayout mContainerPasswordHideEZTouch2;

    private RelativeLayout mContainerPasswordRemoveEZTouch2;

    private EditText mEtPassword1;

    private EditText mEtPassword2;

    private View mLinePassword1;

    private View mLinePassword2;

    private View mDividerPassword1;

    private View mDividerPassword2;

    private CircleView mOkButton;

    private ImageView mOkButtonArrow;

    private ImageView mHidePassword1;

    private ImageView mHidePassword2;

    private ImageView mRemovePassword1;

    private ImageView mRemovePassword2;

    private Boolean mIsHidden1 = true;

    private Boolean mIsHidden2 = true;

    private Boolean mIsOkButtonAvailable = false;

    private TextView mWrongPassword1;

    private TextView mWrongPassword2;

    public RegisterPasswordScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** mContainerPassword1 ******************/

        mContainerPassword1 = new RelativeLayout(getContext());
        mContainerPassword1.setId(R.id.register_p_rl_password_1);
        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_p_rl_password_margin_top_1);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        mEtPassword1 = new EditText(getContext());
        mEtPassword1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPassword1.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPassword1.setId(R.id.register_p_et_password_1);
        mEtPassword1.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtPassword1.setHint(R.string.common_et_hint_password);
        mEtPassword1.setSingleLine(true);
        mEtPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPassword1.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtPassword1.setHint(R.string.common_et_hint_password);
                    mDividerPassword1.setVisibility(View.INVISIBLE);
                    mRemovePassword1.setVisibility(View.GONE);
                } else {
                    mEtPassword1.setHint(null);
                    if (mEtPassword1.getText().toString().length() > 0) {
                        mDividerPassword1.setVisibility(View.VISIBLE);
                        mRemovePassword1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtPassword1, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        mEtPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 6 && s.toString().length() <= 16) {
                    mLinePassword1.setBackgroundColor(ResTools.getColor(R.color.c1));
                    mWrongPassword1.setText(null);
                } else {
                    if (s.toString().length() < 6) {
                        mWrongPassword1.setText(ResTools.getString(R.string.register_p_tv_wrong_password_short));
                    } else {
                        mWrongPassword1.setText(ResTools.getString(R.string.register_p_tv_wrong_password_long));
                    }
                    mLinePassword1.setBackgroundColor(ResTools.getColor(R.color.c10));
                }
                if (s.toString().length() > 0) {
                    mDividerPassword1.setVisibility(View.VISIBLE);
                    mRemovePassword1.setVisibility(View.VISIBLE);
                } else {
                    mDividerPassword1.setVisibility(View.INVISIBLE);
                    mRemovePassword1.setVisibility(View.GONE);
                }
                if (s.toString().length() > 0 && mEtPassword2.getText().toString().length() > 0) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
            }
        });

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_et_remove_hide_margin_right);

        mContainerPassword1.addView(mEtPassword1, lpC1V1);

        mLinePassword1 = new View(getContext());
        mLinePassword1.setId(R.id.register_p_line_password_1);
        mLinePassword1.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_p_et_password_1);

        mContainerPassword1.addView(mLinePassword1, lpC1V2);

        /** EZ Touch start **/

        mContainerPasswordHideEZTouch1 = new RelativeLayout(getContext());
        mContainerPasswordHideEZTouch1.setId(R.id.register_p_rl_password_hide_1);
        mContainerPasswordHideEZTouch1.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerPassword1.addView(mContainerPasswordHideEZTouch1, lpC1C1);

        mHidePassword1 = new ImageView(getContext());
        mHidePassword1.setId(R.id.register_p_iv_password_hide_1);
        mHidePassword1.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));

        RelativeLayout.LayoutParams lpC1C1V1 =
                new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPasswordHideEZTouch1.addView(mHidePassword1, lpC1C1V1);

        mDividerPassword1 = new View(getContext());
        mDividerPassword1.setId(R.id.register_p_divider_password_1);
        mDividerPassword1.setBackgroundColor(ResTools.getColor(R.color.c7));
        mDividerPassword1.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams lpC1V3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_line_height),
                ResTools.getDimenInt(R.dimen.common_divider_height));
        lpC1V3.addRule(RelativeLayout.LEFT_OF, R.id.register_p_rl_password_hide_1);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPassword1.addView(mDividerPassword1, lpC1V3);

        mContainerPasswordRemoveEZTouch1 = new RelativeLayout(getContext());
        mContainerPasswordRemoveEZTouch1.setId(R.id.register_p_rl_password_remove_1);
        mContainerPasswordRemoveEZTouch1.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1C2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_hide_width),
                LayoutParams.MATCH_PARENT);
        lpC1C2.addRule(RelativeLayout.LEFT_OF, R.id.register_p_divider_password_1);

        mContainerPassword1.addView(mContainerPasswordRemoveEZTouch1, lpC1C2);

        mRemovePassword1 = new ImageView(getContext());
        mRemovePassword1.setId(R.id.register_p_iv_password_remove_1);
        mRemovePassword1.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePassword1.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC1C2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC1C2V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C2V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);
        lpC1C2V1.rightMargin = ResTools.getDimenInt(R.dimen.h2);

        mContainerPasswordRemoveEZTouch1.addView(mRemovePassword1, lpC1C2V1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPassword1, lpC1);

        /************** mContainerPassword2 ******************/

        mContainerPassword2 = new RelativeLayout(getContext());
        mContainerPassword2.setId(R.id.register_p_rl_password_2);
        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.register_p_rl_password_margin_top_2);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.register_p_rl_password_1);

        mEtPassword2 = new EditText(getContext());
        mEtPassword2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPassword2.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPassword2.setId(R.id.register_p_et_password_2);
        mEtPassword2.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtPassword2.setHint(R.string.register_p_et_hint_password_2);
        mEtPassword2.setSingleLine(true);
        mEtPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtPassword2.setHint(R.string.register_p_et_hint_password_2);
                    mDividerPassword2.setVisibility(View.INVISIBLE);
                    mRemovePassword2.setVisibility(View.GONE);
                } else {
                    mEtPassword2.setHint(null);
                    if (mEtPassword2.getText().toString().length() > 0) {
                        mDividerPassword2.setVisibility(View.VISIBLE);
                        mRemovePassword2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtPassword2, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        mEtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 6 && s.toString().length() <= 16) {
                    mLinePassword2.setBackgroundColor(ResTools.getColor(R.color.c1));
                    mWrongPassword2.setText(null);
                } else {
                    if (s.toString().length() < 6) {
                        mWrongPassword2.setText(ResTools.getString(R.string.register_p_tv_wrong_password_short));
                    } else {
                        mWrongPassword2.setText(ResTools.getString(R.string.register_p_tv_wrong_password_long));
                    }
                    mLinePassword2.setBackgroundColor(ResTools.getColor(R.color.c10));
                }
                if (s.toString().length() > 0) {
                    mDividerPassword2.setVisibility(View.VISIBLE);
                    mRemovePassword2.setVisibility(View.VISIBLE);
                } else {
                    mDividerPassword2.setVisibility(View.INVISIBLE);
                    mRemovePassword2.setVisibility(View.GONE);
                }
                if (s.toString().length() > 0 && mEtPassword1.getText().toString().length() > 0) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
            }
        });

        RelativeLayout.LayoutParams lpC2V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC2V1.rightMargin = ResTools.getDimenInt(R.dimen.common_et_remove_hide_margin_right);

        mContainerPassword2.addView(mEtPassword2, lpC2V1);

        mLinePassword2 = new View(getContext());
        mLinePassword2.setId(R.id.register_p_line_password_2);
        mLinePassword2.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC2V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC2V2.addRule(RelativeLayout.BELOW, R.id.register_p_et_password_2);

        mContainerPassword2.addView(mLinePassword2, lpC2V2);

        /** EZ Touch start **/

        mContainerPasswordHideEZTouch2 = new RelativeLayout(getContext());
        mContainerPasswordHideEZTouch2.setId(R.id.register_p_rl_password_hide_2);
        mContainerPasswordHideEZTouch2.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC2C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerPassword2.addView(mContainerPasswordHideEZTouch2, lpC2C1);

        mHidePassword2 = new ImageView(getContext());
        mHidePassword2.setId(R.id.register_p_iv_password_hide_2);
        mHidePassword2.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));

        RelativeLayout.LayoutParams lpC2C1V1 =
                new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPasswordHideEZTouch2.addView(mHidePassword2, lpC2C1V1);

        mDividerPassword2 = new View(getContext());
        mDividerPassword2.setId(R.id.register_p_divider_password_2);
        mDividerPassword2.setBackgroundColor(ResTools.getColor(R.color.c7));
        mDividerPassword2.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_line_height),
                ResTools.getDimenInt(R.dimen.common_divider_height));
        lpC2V3.addRule(RelativeLayout.LEFT_OF, R.id.register_p_rl_password_hide_2);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPassword2.addView(mDividerPassword2, lpC2V3);

        mContainerPasswordRemoveEZTouch2 = new RelativeLayout(getContext());
        mContainerPasswordRemoveEZTouch2.setId(R.id.register_p_rl_password_remove_2);
        mContainerPasswordRemoveEZTouch2.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2C2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_hide_width),
                LayoutParams.MATCH_PARENT);
        lpC2C2.addRule(RelativeLayout.LEFT_OF, R.id.register_p_divider_password_2);

        mContainerPassword2.addView(mContainerPasswordRemoveEZTouch2, lpC2C2);

        mRemovePassword2 = new ImageView(getContext());
        mRemovePassword2.setId(R.id.register_p_iv_password_remove_2);
        mRemovePassword2.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePassword2.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC2C2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC2C2V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C2V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);
        lpC2C2V1.rightMargin = ResTools.getDimenInt(R.dimen.h2);

        mContainerPasswordRemoveEZTouch2.addView(mRemovePassword2, lpC2C2V1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPassword2, lpC2);

        /************** mContainerOKButton ******************/

        mContainerOKButton = new RelativeLayout(getContext());
        mContainerOKButton.setId(R.id.register_p_rl_ok);
        mContainerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.register_p_rl_password_2);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mOkButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButton.setId(R.id.register_p_v_ok);
        mOkButton.setColor(ResTools.getColor(R.color.c1));
        mOkButton.setAlpha(0.3f);

        mContainerOKButton.addView(mOkButton);

        mOkButtonArrow = new ImageView(getContext());
        mOkButtonArrow.setId(R.id.register_p_iv_ok);
        mOkButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        mContainerOKButton.addView(mOkButtonArrow, lpC3V1);

        mContainer.addView(mContainerOKButton, lpC3);

        /************** mWrongPassword1 ******************/

        mWrongPassword1 = new TextView(getContext());
        mWrongPassword1.setId(R.id.register_p_tv_password_wrong_1);
        mWrongPassword1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongPassword1.setTextColor(ResTools.getColor(R.color.c10));
        mWrongPassword1.setText(null);

        RelativeLayout.LayoutParams lpV1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV1.addRule(RelativeLayout.BELOW, R.id.register_p_rl_password_1);
        lpV1.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongPassword1, lpV1);

        /************** mWrongPassword2 ******************/

        mWrongPassword2 = new TextView(getContext());
        mWrongPassword2.setId(R.id.register_p_tv_password_wrong_2);
        mWrongPassword2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongPassword2.setTextColor(ResTools.getColor(R.color.c10));
        mWrongPassword2.setText(null);

        RelativeLayout.LayoutParams lpV2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV2.addRule(RelativeLayout.BELOW, R.id.register_p_rl_password_2);
        lpV2.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongPassword2, lpV2);

    }

    @Override
    public void onClick(View v) {
        if(v == mContainerPasswordHideEZTouch1) {
            if (mIsHidden1) {
                mEtPassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mHidePassword1.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));
                mIsHidden1 = false;
            } else {
                mEtPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mHidePassword1.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
                mIsHidden1 = true;
            }
            mEtPassword1.postInvalidate();
            CharSequence charSequence = mEtPassword1.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
        if(v == mContainerPasswordHideEZTouch2) {
            if (mIsHidden2) {
                mEtPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mHidePassword2.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));
                mIsHidden2 = false;
            } else {
                mEtPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mHidePassword2.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
                mIsHidden2 = true;
            }
            mEtPassword2.postInvalidate();
            CharSequence charSequence = mEtPassword2.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
        if (v == mContainerPasswordRemoveEZTouch1) {
            mEtPassword1.setText(null);
        }
        if (v == mContainerPasswordRemoveEZTouch2) {
            mEtPassword2.setText(null);
        }
        if (v == mContainerOKButton) {
            if(mIsOkButtonAvailable)
                mCallBacks.handleAction(ActionId.CommitRegisterPasswordClick, null, null);
        }
    }
}
