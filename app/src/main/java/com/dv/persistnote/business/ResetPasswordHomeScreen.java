package com.dv.persistnote.business;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import static com.dv.persistnote.base.ContextManager.getSystemService;

/**
 * Created by QinZheng on 2016/4/23.
 */
public class ResetPasswordHomeScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout mContainerPhoneNumber;

    private RelativeLayout mContainerPhoneNumberRemoveEZTouch;

    private RelativeLayout mContainerCode;

    private RelativeLayout mContainerCodeRemoveEZTouch;

    private RelativeLayout mContainerOKButton;

    private EditText mEtPhoneNumber;

    private EditText mEtCode;

    private Boolean mIsOkButtonAvailable = false;

    private Boolean mIsRemovePhoneNumber = false;

    private Boolean mIsRemoveCode = false;

    private Boolean mIsGetCode = false;

    private Button mGetCode;

    private View mLinePhoneNumber;

    private View mLineCode;

    private ImageView mRemoveCode;

    private ImageView mRemovePhoneNumber;

    private CircleView mOkButton;

    private CircleView mOkButtonClick;

    private ImageView mOkButtonArrow;

    private TextView mWrongPhoneNumber;

    private TextView mWrongCode;

    private float mStartX, mNowX;

    private float mStartY, mNowY;

    private InputMethodManager mImm;

    public ResetPasswordHomeScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.reset_password));
    }

    protected void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();
        mContainer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // 失去焦点，隐藏键盘
                if(mEtPhoneNumber.hasFocus()) {
                    mEtPhoneNumber.clearFocus();
                    mImm.hideSoftInputFromWindow(mEtPhoneNumber.getWindowToken(), 0);
                }
                if(mEtCode.hasFocus()) {
                    mEtCode.clearFocus();
                    mImm.hideSoftInputFromWindow(mEtCode.getWindowToken(), 0);
                }
                return false;
            }
        });

        // 获取InputMethodManager
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        /************** mContainerPhoneNumber ******************/

        mContainerPhoneNumber = new RelativeLayout(getContext());
        mContainerPhoneNumber.setId(R.id.reset_password_rl_phone_number);
        mContainerPhoneNumber.setFocusable(true);
        mContainerPhoneNumber.setFocusableInTouchMode(true);

        LayoutParams lpC1 = new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.reset_password_rl_phone_number_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        mEtPhoneNumber = new EditText(getContext());
        mEtPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtPhoneNumber.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtPhoneNumber.setId(R.id.reset_password_et_phone_number);
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
                    mIsRemovePhoneNumber = false;
                } else {
                    mEtPhoneNumber.setHint(null);
                    if (mEtPhoneNumber.getText().toString().length() > 0)
                        mRemovePhoneNumber.setVisibility(View.VISIBLE);
                        mIsRemovePhoneNumber = true;
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtPhoneNumber, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Reset Password Init : color_cursor Error");
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
                if (s.toString().length() == 11 && mEtCode.getText().toString().length() == 6) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    mRemovePhoneNumber.setVisibility(View.VISIBLE);
                    mIsRemovePhoneNumber = true;
                } else {
                    mRemovePhoneNumber.setVisibility(View.GONE);
                    mIsRemovePhoneNumber = false;
                }
                if (s.toString().length() == 11) {
                    mIsGetCode = true;
                    mGetCode.setAlpha(1.0f);
                } else {
                    mIsGetCode = false;
                    mGetCode.setAlpha(0.2f);
                }
            }
        });

        mEtPhoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        LayoutParams lpC1V1 =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_remove_width);

        mContainerPhoneNumber.addView(mEtPhoneNumber, lpC1V1);

        mLinePhoneNumber = new View(getContext());
        mLinePhoneNumber.setId(R.id.reset_password_line_phone_number);
        mLinePhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c1));

        LayoutParams lpC1V2 =
                new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.reset_password_et_phone_number);

        mContainerPhoneNumber.addView(mLinePhoneNumber, lpC1V2);

        /** EZ Touch start **/

        mContainerPhoneNumberRemoveEZTouch = new RelativeLayout(getContext());
        mContainerPhoneNumberRemoveEZTouch.setId(R.id.reset_password_rl_phone_number_remove);
        mContainerPhoneNumberRemoveEZTouch.setOnClickListener(this);

        LayoutParams lpC1C1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mRemovePhoneNumber = new ImageView(getContext());
        mRemovePhoneNumber.setId(R.id.reset_password_iv_phone_number_remove);
        mRemovePhoneNumber.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemovePhoneNumber.setVisibility(View.GONE);

        LayoutParams lpC1C1V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);

        mContainerPhoneNumberRemoveEZTouch.addView(mRemovePhoneNumber, lpC1C1V1);

        mContainerPhoneNumber.addView(mContainerPhoneNumberRemoveEZTouch, lpC1C1);

        /** EZ Touch end **/

        mContainer.addView(mContainerPhoneNumber, lpC1);

        /************** mContainerCode ******************/

        mContainerCode = new RelativeLayout(getContext());
        mContainerCode.setId(R.id.reset_password_rl_code);
        mContainerCode.setFocusable(true);
        mContainerCode.setFocusableInTouchMode(true);

        LayoutParams lpC2 = new LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.reset_password_rl_code_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.reset_password_rl_phone_number);

        mGetCode = new Button(getContext());
        mGetCode.setId(R.id.reset_password_bt_code);
        mGetCode.setAlpha(0.2f);
        mGetCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        mGetCode.setTextColor(ResTools.getColor(R.color.c4));
        mGetCode.setText(ResTools.getString(R.string.common_bt_code));
        mGetCode.setOnClickListener(this);
        // Deprecated. 可以更改Target Api，则可以使用setBackground()
        mGetCode.setBackgroundDrawable(ResTools.getDrawable(R.drawable.bt_code_background_color));

        LayoutParams lpC2V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.reset_password_bt_code_width),
                ResTools.getDimenInt(R.dimen.reset_password_bt_code_height));
        lpC2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mContainerCode.addView(mGetCode, lpC2V1);

        mEtCode = new EditText(getContext());
        mEtCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        mEtCode.setId(R.id.reset_password_et_code);
        mEtCode.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        mEtCode.setBackgroundColor(ResTools.getColor(R.color.c4));
        mEtCode.setHint(R.string.common_et_hint_code);
        mEtCode.setSingleLine(true);
        mEtCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEtCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mEtCode.setHint(R.string.common_et_hint_code);
                    mRemoveCode.setVisibility(View.GONE);
                    mIsRemoveCode = false;
                } else {
                    mEtCode.setHint(null);
                    if (mEtCode.getText().toString().length() > 0)
                        mRemoveCode.setVisibility(View.VISIBLE);
                        mIsRemoveCode = true;
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEtCode, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Reset Password Init : color_cursor Error");
        }

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6 && mEtPhoneNumber.getText().toString().length() == 11) {
                    mIsOkButtonAvailable = true;
                    mOkButton.setAlpha(1.0f);
                } else {
                    mIsOkButtonAvailable = false;
                    mOkButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    mRemoveCode.setVisibility(View.VISIBLE);
                    mIsRemoveCode = true;
                } else {
                    mRemoveCode.setVisibility(View.GONE);
                    mIsRemoveCode = false;
                }
            }
        });

        mEtCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        LayoutParams lpC2V2 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lpC2V2.addRule(RelativeLayout.LEFT_OF, R.id.reset_password_bt_code);
        lpC2V2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpC2V2.rightMargin = ResTools.getDimenInt(R.dimen.reset_password_et_code_margin_right);

        mContainerCode.addView(mEtCode, lpC2V2);

        mLineCode = new View(getContext());
        mLineCode.setId(R.id.reset_password_line_code);
        mLineCode.setBackgroundColor(ResTools.getColor(R.color.c1));
        LayoutParams lpC2V3 = new LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_line_height));
        lpC2V3.addRule(RelativeLayout.BELOW, R.id.reset_password_et_code);
        lpC2V3.addRule(RelativeLayout.LEFT_OF, R.id.reset_password_bt_code);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpC2V3.rightMargin = ResTools.getDimenInt(R.dimen.reset_password_line_code_margin_right);

        mContainerCode.addView(mLineCode, lpC2V3);

        /** EZ Touch start **/

        mContainerCodeRemoveEZTouch = new RelativeLayout(getContext());
        mContainerCodeRemoveEZTouch.setId(R.id.reset_password_rl_code_remove);
        mContainerCodeRemoveEZTouch.setOnClickListener(this);

        LayoutParams lpC2C1 = new LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC2C1.addRule(RelativeLayout.LEFT_OF, R.id.reset_password_bt_code);
        lpC2C1.rightMargin = ResTools.getDimenInt(R.dimen.reset_password_rl_code_remove_margin_right);

        mRemoveCode = new ImageView(getContext());
        mRemoveCode.setId(R.id.reset_password_iv_code_remove);
        mRemoveCode.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        mRemoveCode.setVisibility(View.GONE);

        LayoutParams lpC2C1V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.h2), ResTools.getDimenInt(R.dimen.h2));
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_12);

        mContainerCodeRemoveEZTouch.addView(mRemoveCode, lpC2C1V1);

        mContainerCode.addView(mContainerCodeRemoveEZTouch, lpC2C1);

        /** EZ Touch end **/

        mContainer.addView(mContainerCode, lpC2);

        /************** mContainerOKButton ******************/

        mOkButtonClick = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButtonClick.setColor(ResTools.getColor(R.color.c9));
        mOkButtonClick.setVisibility(View.GONE);

        mContainerOKButton = new RelativeLayout(getContext());
        mContainerOKButton.setId(R.id.reset_password_rl_ok);
        mContainerOKButton.setOnClickListener(this);
        mContainerOKButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // 失去焦点，隐藏键盘
                if(mEtPhoneNumber.hasFocus()) {
                    mEtPhoneNumber.clearFocus();
                    mImm.hideSoftInputFromWindow(mEtPhoneNumber.getWindowToken(), 0);
                }
                if(mEtCode.hasFocus()) {
                    mEtCode.clearFocus();
                    mImm.hideSoftInputFromWindow(mEtCode.getWindowToken(), 0);
                }
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
                                mCallBacks.handleAction(ActionId.CommitResetPasswordHomeClick, null, null);
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
        lpC3.addRule(RelativeLayout.BELOW, R.id.reset_password_rl_code);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        mOkButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        mOkButton.setId(R.id.reset_password_v_ok);
        mOkButton.setColor(ResTools.getColor(R.color.c1));
        mOkButton.setAlpha(0.3f);

        mContainerOKButton.addView(mOkButton);
        mContainerOKButton.addView(mOkButtonClick);

        mOkButtonArrow = new ImageView(getContext());
        mOkButtonArrow.setId(R.id.reset_password_iv_ok);
        mOkButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        LayoutParams lpC3V1 = new LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        mContainerOKButton.addView(mOkButtonArrow, lpC3V1);

        mContainer.addView(mContainerOKButton, lpC3);

        /************** mWrongPhoneNumber ******************/

        mWrongPhoneNumber = new TextView(getContext());
        mWrongPhoneNumber.setId(R.id.reset_password_tv_phone_number_wrong);
        mWrongPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongPhoneNumber.setTextColor(ResTools.getColor(R.color.c10));
        mWrongPhoneNumber.setText(null);
        mWrongPhoneNumber.setVisibility(View.GONE);

        LayoutParams lpV1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV1.addRule(RelativeLayout.BELOW, R.id.reset_password_rl_phone_number);
        lpV1.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongPhoneNumber, lpV1);

        /************** mWrongCode ******************/

        mWrongCode = new TextView(getContext());
        mWrongCode.setId(R.id.reset_password_tv_code_wrong);
        mWrongCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h3));
        mWrongCode.setTextColor(ResTools.getColor(R.color.c10));
        mWrongCode.setText(ResTools.getString(R.string.common_tv_wrong_code));
        mWrongCode.setVisibility(View.GONE);

        LayoutParams lpV2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpV2.addRule(RelativeLayout.BELOW, R.id.reset_password_rl_code);
        lpV2.topMargin = ResTools.getDimenInt(R.dimen.common_tv_margin_top);
        lpV2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);

        mContainer.addView(mWrongCode, lpV2);

    }

    @Override
    public void onClick(View v) {
        if (v == mGetCode) {
            // 失去焦点，隐藏键盘
            if(mEtPhoneNumber.hasFocus()) {
                mEtPhoneNumber.clearFocus();
                mImm.hideSoftInputFromWindow(mEtPhoneNumber.getWindowToken(), 0);
            }
            if(mEtCode.hasFocus()) {
                mEtCode.clearFocus();
                mImm.hideSoftInputFromWindow(mEtCode.getWindowToken(), 0);
            }
            if(mIsGetCode) {
                // TODO: 获取验证码
            }
        }
        if (v == mContainerCodeRemoveEZTouch) {
            if (mIsRemoveCode)
                mEtCode.setText(null);
        }
        if (v == mContainerPhoneNumberRemoveEZTouch) {
            if (mIsRemovePhoneNumber)
                mEtPhoneNumber.setText(null);
        }
    }
}
