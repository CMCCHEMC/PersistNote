package com.dv.persistnote.business;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
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
 * Created by QinZheng on 2016/4/23.
 */
public class ResetPasswordPasswordScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout mContainerPassword;

    private RelativeLayout mContainerOKButton;

    private RelativeLayout mContainerPasswordHideEZTouch;

    private RelativeLayout mContainerPasswordRemoveEZTouch;

    private EditText mEtPassword;

    private View mLinePassword;

    private View mDividerPassword;

    private CircleView mOkButton;

    private CircleView mOkButtonClick;

    private ImageView mOkButtonArrow;

    private ImageView mHidePassword;

    private ImageView mRemovePassword;

    private Boolean mIsHidden = true;

    private Boolean mIsOkButtonAvailable = false;

    private Boolean mIsRemovePassword = false;

    private TextView mWrongPassword;

    private float mStartX, mNowX;

    private float mStartY, mNowY;

    public ResetPasswordPasswordScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.reset_password));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** mContainerPassword ******************/

        mContainerPassword = new RelativeLayout(getContext());
        mContainerPassword.setId(R.id.reset_password_p_rl_password);
        LayoutParams lpC1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.reset_password_p_rl_password_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        mEtPassword = new EditText(getContext());
        mEtPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPassword.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPassword.setId(R.id.reset_password_p_et_password);
        mEtPassword.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtPassword.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] chars = new char[62];
                int j = 0;
                for(char i = 'a'; i <= 'z';j++,i++) {
                    chars[j] = i;
                }
                for(char i = 'A'; i <= 'Z'; j++,i++) {
                    chars[j] = i;
                }
                for(char i = '0'; i <= '9'; j++,i++) {
                    chars[j] = i;
                }
                return chars;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
        });
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
                    mIsRemovePassword = false;
                } else {
                    mEtPassword.setHint(null);
                    if (mEtPassword.getText().toString().length() > 0) {
                        mDividerPassword.setVisibility(View.VISIBLE);
                        mRemovePassword.setVisibility(View.VISIBLE);
                        mIsRemovePassword = true;
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
            Log.e(TAG, "Reset Password Init : color_cursor Error");
        }

        mEtPassword.addTextChangedListener(new PasswordWatcher());

        mEtPassword.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(16)
        });

        LayoutParams lpC1V1 =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_et_remove_hide_margin_right);

        mContainerPassword.addView(mEtPassword, lpC1V1);

        mLinePassword = new View(getContext());
        mLinePassword.setId(R.id.reset_password_p_line_password);
        mLinePassword.setBackgroundColor(ResTools.getColor(R.color.c1));

        LayoutParams lpC1V2 =
                new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.reset_password_p_et_password);

        mContainerPassword.addView(mLinePassword, lpC1V2);

        /** EZ Touch start **/

        mContainerPasswordHideEZTouch = new RelativeLayout(getContext());
        mContainerPasswordHideEZTouch.setId(R.id.reset_password_p_rl_password_hide);
        mContainerPasswordHideEZTouch.setOnClickListener(this);

        LayoutParams lpC1C1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerPassword.addView(mContainerPasswordHideEZTouch, lpC1C1);

        mHidePassword = new ImageView(getContext());
        mHidePassword.setId(R.id.reset_password_p_iv_password_hide);
        mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));

        LayoutParams lpC1C1V1 =
                new LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPasswordHideEZTouch.addView(mHidePassword, lpC1C1V1);

        mDividerPassword = new View(getContext());
        mDividerPassword.setId(R.id.reset_password_p_divider_password);
        mDividerPassword.setBackgroundColor(ResTools.getColor(R.color.c7));
        mDividerPassword.setVisibility(View.INVISIBLE);

        LayoutParams lpC1V3 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_line_height),
                ResTools.getDimenInt(R.dimen.common_divider_height));
        lpC1V3.addRule(RelativeLayout.LEFT_OF, R.id.reset_password_p_rl_password_hide);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        mContainerPassword.addView(mDividerPassword, lpC1V3);

        mContainerPasswordRemoveEZTouch = new RelativeLayout(getContext());
        mContainerPasswordRemoveEZTouch.setId(R.id.reset_password_p_rl_password_remove);
        mContainerPasswordRemoveEZTouch.setOnClickListener(this);

        LayoutParams lpC1C2 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_hide_width),
                LayoutParams.MATCH_PARENT);
        lpC1C2.addRule(RelativeLayout.LEFT_OF, R.id.reset_password_p_divider_password);

        mContainerPassword.addView(mContainerPasswordRemoveEZTouch, lpC1C2);

        mRemovePassword = new ImageView(getContext());
        mRemovePassword.setId(R.id.reset_password_p_iv_password_remove);
        mRemovePassword.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePassword.setVisibility(View.GONE);

        LayoutParams lpC1C2V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC1C2V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C2V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);
        lpC1C2V1.rightMargin = ResTools.getDimenInt(R.dimen.h2);

        mContainerPasswordRemoveEZTouch.addView(mRemovePassword, lpC1C2V1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPassword, lpC1);

        /************** mContainerOKButton ******************/

        mOkButtonClick = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButtonClick.setColor(ResTools.getColor(R.color.c9));
        mOkButtonClick.setVisibility(View.GONE);

        mContainerOKButton = new RelativeLayout(getContext());
        mContainerOKButton.setId(R.id.reset_password_p_rl_ok);
        mContainerOKButton.setOnClickListener(this);
        mContainerOKButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mIsOkButtonAvailable) {
                    mNowX = motionEvent.getX();
                    mNowY = motionEvent.getY();
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mOkButtonClick.setVisibility(View.VISIBLE);
                            mStartX = motionEvent.getX();
                            mStartY = motionEvent.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            mOkButtonClick.setVisibility(View.GONE);
                            if (Math.abs(mNowX - mStartX) < 3.0 && Math.abs(mNowY - mStartY) < 3.0) {
                                mCallBacks.handleAction(ActionId.CommitResetPasswordPasswordClick, null, null);
                            }
                            break;
                    }
                }
                return false;
            }
        });

        LayoutParams lpC3 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.reset_password_p_rl_password);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mOkButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButton.setId(R.id.reset_password_p_v_ok);
        mOkButton.setColor(ResTools.getColor(R.color.c1));
        mOkButton.setAlpha(0.3f);

        mContainerOKButton.addView(mOkButton);
        mContainerOKButton.addView(mOkButtonClick);

        mOkButtonArrow = new ImageView(getContext());
        mOkButtonArrow.setId(R.id.reset_password_p_iv_ok);
        mOkButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        LayoutParams lpC3V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        mContainerOKButton.addView(mOkButtonArrow, lpC3V1);

        mContainer.addView(mContainerOKButton, lpC3);

        /************** mWrongPassword ******************/

        mWrongPassword = new TextView(getContext());
        mWrongPassword.setId(R.id.reset_password_p_tv_password_wrong);
        mWrongPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongPassword.setTextColor(ResTools.getColor(R.color.c10));
        mWrongPassword.setText(null);

        LayoutParams lpV1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV1.addRule(RelativeLayout.BELOW, R.id.reset_password_p_rl_password);
        lpV1.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongPassword, lpV1);
    }

    @Override
    public void onClick(View v) {
        if(v == mContainerPasswordHideEZTouch) {
            if (mIsHidden) {
                mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
                mIsHidden = false;
            } else {
                mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mHidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));
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
            if (mIsRemovePassword)
                mEtPassword.setText(null);
        }
    }

    protected class PasswordWatcher implements TextWatcher {


        public PasswordWatcher(){

        }

        @Override
        public void beforeTextChanged (CharSequence s,int start, int count, int after){
        }

        @Override
        public void onTextChanged (CharSequence s,int start, int before, int count){

        }

        @Override
        public void afterTextChanged (Editable s){
            if (s.toString().length() > 0) {
                mDividerPassword.setVisibility(View.VISIBLE);
                mRemovePassword.setVisibility(View.VISIBLE);
                mIsRemovePassword = true;
            } else {
                mDividerPassword.setVisibility(View.INVISIBLE);
                mRemovePassword.setVisibility(View.GONE);
                mIsRemovePassword = false;
            }
            if (s.toString().length() >= 6) {
                mIsOkButtonAvailable = true;
                mOkButton.setAlpha(1.0f);
            } else {
                mIsOkButtonAvailable = false;
                mOkButton.setAlpha(0.3f);
            }
        }
    }
}
