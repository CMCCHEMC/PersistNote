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
 * Created by QinZheng on 2016/3/29.
 */
public class LoginScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout mContainerPhoneNumber;

    private RelativeLayout mContainerPassword;

    private RelativeLayout mContainerOKButton;

    private RelativeLayout mContainerPhoneNumberRemoveEZTouch;

    private RelativeLayout mContainerPasswordRemoveEZTouch;

    private RelativeLayout mContainerPasswordHideEZTouch;

    private EditText mEtPhoneNumber;

    private EditText mEtPassword;

    private View mLinePhoneNumber;

    private View mLinePassword;

    private View mDividerPassword;

    private CircleView mOkButton;

    private ImageView mOkButtonArrow;

    private ImageView mRemovePhoneNumber;

    private ImageView mRemovePassword;

    private ImageView mHidePassword;

    private Boolean mIsHidden = true;

    private Boolean mIsOkButtonAvailable = false;

    public LoginScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.login));

    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** mContainerPhoneNumber ******************/

        mContainerPhoneNumber = new RelativeLayout(getContext());
        mContainerPhoneNumber.setId(R.id.login_rl_phone_number);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.login_rl_phone_number_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        mEtPhoneNumber = new EditText(getContext());
        mEtPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPhoneNumber.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPhoneNumber.setId(R.id.login_et_phone_number);
        mEtPhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtPhoneNumber.setHint(R.string.common_et_hint_phone_number);
        mEtPhoneNumber.setSingleLine(true);
        mEtPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtPhoneNumber.setHint(R.string.common_et_hint_phone_number);
                    mRemovePhoneNumber.setVisibility(View.GONE);
                } else {
                    mEtPhoneNumber.setHint(null);
                    if (mEtPhoneNumber.getText().toString().length() > 0)
                        mRemovePhoneNumber.setVisibility(View.VISIBLE);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtPhoneNumber, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Login Init : color_cursor Error");
        }

        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && mEtPassword.getText().toString().length() > 0) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    mRemovePhoneNumber.setVisibility(View.VISIBLE);
                } else {
                    mRemovePhoneNumber.setVisibility(View.GONE);
                }
            }
        });

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_remove_width);

        mContainerPhoneNumber.addView(mEtPhoneNumber, lpC1V1);

        mLinePhoneNumber = new View(getContext());
        mLinePhoneNumber.setId(R.id.login_line_phone_number);
        mLinePhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.login_et_phone_number);

        mContainerPhoneNumber.addView(mLinePhoneNumber, lpC1V2);

        /** EZ Touch start **/

        mContainerPhoneNumberRemoveEZTouch = new RelativeLayout(getContext());
        mContainerPhoneNumberRemoveEZTouch.setId(R.id.login_rl_phone_number_remove);
        mContainerPhoneNumberRemoveEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mRemovePhoneNumber = new ImageView(getContext());
        mRemovePhoneNumber.setId(R.id.login_iv_password_remove);
        mRemovePhoneNumber.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePhoneNumber.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC1C1V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);

        mContainerPhoneNumberRemoveEZTouch.addView(mRemovePhoneNumber, lpC1C1V1);

        mContainerPhoneNumber.addView(mContainerPhoneNumberRemoveEZTouch, lpC1C1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPhoneNumber, lpC1);

        /************** mContainerPassword ******************/

        mContainerPassword = new RelativeLayout(getContext());
        mContainerPassword.setId(R.id.login_rl_password);
        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.login_rl_password_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.login_rl_phone_number);

        mEtPassword = new EditText(getContext());
        mEtPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPassword.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPassword.setId(R.id.login_et_password);
        mEtPassword.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtPassword.setHint(R.string.common_et_hint_password);
        mEtPassword.setSingleLine(true);
        mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtPassword.setHint(R.string.common_et_hint_password);
                    mDividerPassword.setVisibility(View.INVISIBLE);
                    mRemovePassword.setVisibility(View.GONE);
                } else {
                    mEtPassword.setHint(null);
                    if (mEtPassword.getText().toString().length() > 0) {
                        mDividerPassword.setVisibility(View.VISIBLE);
                        mRemovePassword.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtPassword, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Login Init : color_cursor Error");
        }

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && mEtPhoneNumber.getText().toString().length() > 0) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    mDividerPassword.setVisibility(View.VISIBLE);
                    mRemovePassword.setVisibility(View.VISIBLE);
                } else {
                    mDividerPassword.setVisibility(View.INVISIBLE);
                    mRemovePassword.setVisibility(View.GONE);
                }
            }
        });

        RelativeLayout.LayoutParams lpC2V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC2V1.rightMargin = ResTools.getDimenInt(R.dimen.common_et_remove_hide_margin_right);

        mContainerPassword.addView(mEtPassword, lpC2V1);

        mLinePassword = new View(getContext());
        mLinePassword.setId(R.id.login_line_password);
        mLinePassword.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC2V2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_line_height));
        lpC2V2.addRule(RelativeLayout.BELOW, R.id.login_et_password);

        mContainerPassword.addView(mLinePassword, lpC2V2);

        /** EZ Touch start **/

        mContainerPasswordHideEZTouch = new RelativeLayout(getContext());
        mContainerPasswordHideEZTouch.setId(R.id.login_rl_password_hide);
        mContainerPasswordHideEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC2C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerPassword.addView(mContainerPasswordHideEZTouch, lpC2C1);

        mHidePassword = new ImageView(getContext());
        mHidePassword.setId(R.id.login_iv_password_hide);
        mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));

        RelativeLayout.LayoutParams lpC2C1V1 =
                new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPasswordHideEZTouch.addView(mHidePassword, lpC2C1V1);

        mDividerPassword = new View(getContext());
        mDividerPassword.setId(R.id.login_divider_password);
        mDividerPassword.setBackgroundColor(ResTools.getColor(R.color.c7));
        mDividerPassword.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_line_height),
                ResTools.getDimenInt(R.dimen.common_divider_height));
        lpC2V3.addRule(RelativeLayout.LEFT_OF, R.id.login_rl_password_hide);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPassword.addView(mDividerPassword, lpC2V3);

        mContainerPasswordRemoveEZTouch = new RelativeLayout(getContext());
        mContainerPasswordRemoveEZTouch.setId(R.id.login_rl_password_remove);
        mContainerPasswordRemoveEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2C2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_hide_width),
                LayoutParams.MATCH_PARENT);
        lpC2C2.addRule(RelativeLayout.LEFT_OF, R.id.login_divider_password);

        mContainerPassword.addView(mContainerPasswordRemoveEZTouch, lpC2C2);

        mRemovePassword = new ImageView(getContext());
        mRemovePassword.setId(R.id.login_iv_password_remove);
        mRemovePassword.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePassword.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC2C2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC2C2V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C2V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);
        lpC2C2V1.rightMargin = ResTools.getDimenInt(R.dimen.h2);

        mContainerPasswordRemoveEZTouch.addView(mRemovePassword, lpC2C2V1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPassword, lpC2);

        /************** mContainerOKButton ******************/

        mContainerOKButton = new RelativeLayout(getContext());
        mContainerOKButton.setId(R.id.login_rl_ok);
        mContainerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.login_rl_password);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mOkButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButton.setId(R.id.login_v_ok);
        mOkButton.setColor(ResTools.getColor(R.color.c1));
        mOkButton.setAlpha(0.3f);

        mContainerOKButton.addView(mOkButton);

        mOkButtonArrow = new ImageView(getContext());
        mOkButtonArrow.setId(R.id.login_iv_ok);
        mOkButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        mContainerOKButton.addView(mOkButtonArrow, lpC3V1);

        mContainer.addView(mContainerOKButton, lpC3);


    }

    @Override
    public void onClick(View v) {
        if(v == mContainerPasswordHideEZTouch) {
            if (mIsHidden) {
                mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));
                mIsHidden = false;
            } else {
                mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
                mIsHidden = true;
            }
            mEtPassword.postInvalidate();
            CharSequence charSequence = mEtPassword.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
        if (v == mContainerPasswordRemoveEZTouch) {
            mEtPassword.setText(null);
        }
        if (v == mContainerPhoneNumberRemoveEZTouch) {
            mEtPhoneNumber.setText(null);
        }
        if (v == mContainerOKButton) {
            if(mIsOkButtonAvailable)
                mCallBacks.handleAction(ActionId.CommitLoginClick, null, null);
        }
    }
}
