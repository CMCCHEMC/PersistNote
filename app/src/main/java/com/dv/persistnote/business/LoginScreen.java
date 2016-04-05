package com.dv.persistnote.business;

import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
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
 * Created by Admin2 on 2016/3/29.
 */
public class LoginScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout containerPhoneNumber;

    private RelativeLayout containerPassword;

    private RelativeLayout containerOKButton;

    private EditText etPhoneNumber;

    private EditText etPassword;

    private View linePhoneNumber;

    private View linePassword;

    private CircleView okButton;

    private ImageView okButtonArrow;

    private ImageView hidePassword;

    private Boolean isHidden = true;

    private TextView wrongPhoneNumber;

    private TextView wrongPassword;

    public LoginScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.login));

    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** containerPhoneNumber ******************/

        containerPhoneNumber = new RelativeLayout(getContext());
        containerPhoneNumber.setId(R.id.login_rl_phone_number);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.login_rl_phone_number_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        etPhoneNumber = new EditText(getContext());
        etPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPhoneNumber.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etPhoneNumber.setId(R.id.login_et_phone_number);
        etPhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPhoneNumber.setHint(R.string.common_et_hint_phone_number);
        etPhoneNumber.setSingleLine(true);
        etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etPhoneNumber.setHint(R.string.common_et_hint_phone_number);
                } else {
                    etPhoneNumber.setHint(null);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etPhoneNumber, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Login Init : color_cursor Error");
        }

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerPhoneNumber.addView(etPhoneNumber, lpC1V1);

        linePhoneNumber = new View(getContext());
        linePhoneNumber.setId(R.id.login_line_phone_number);
        linePhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.login_et_phone_number);

        containerPhoneNumber.addView(linePhoneNumber, lpC1V2);

        wrongPhoneNumber = new TextView(getContext());
        wrongPhoneNumber.setId(R.id.login_tv_phone_number_wrong);
        wrongPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        wrongPhoneNumber.setTextColor(ResTools.getColor(R.color.c10));
        wrongPhoneNumber.setText(ResTools.getString(R.string.login_tv_wrong_phone_number));
        wrongPhoneNumber.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lpC1V3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom);

        containerPhoneNumber.addView(wrongPhoneNumber, lpC1V3);

        mContainer.addView(containerPhoneNumber, lpC1);

        /************** containerPassword ******************/

        containerPassword = new RelativeLayout(getContext());
        containerPassword.setId(R.id.login_rl_password);
        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.login_rl_password_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.login_rl_phone_number);

        etPassword = new EditText(getContext());
        etPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPassword.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etPassword.setId(R.id.login_et_password);
        etPassword.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPassword.setHint(R.string.common_et_hint_password);
        etPassword.setSingleLine(true);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etPassword.setHint(R.string.common_et_hint_password);
                } else {
                    etPassword.setHint(null);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etPassword, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Login Init : color_cursor Error");
        }

        containerPassword.addView(etPassword, lpC1V1);

        linePassword = new View(getContext());
        linePassword.setId(R.id.login_line_password);
        linePassword.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC2V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC2V1.addRule(RelativeLayout.BELOW, R.id.login_et_password);

        containerPassword.addView(linePassword, lpC2V1);

        hidePassword = new ImageView(getContext());
        hidePassword.setId(R.id.login_iv_password);
        hidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
        hidePassword.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2V2 =
                new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC2V2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2V2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2V2.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom);

        containerPassword.addView(hidePassword, lpC2V2);

        wrongPassword = new TextView(getContext());
        wrongPassword.setId(R.id.login_tv_password_wrong);
        wrongPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        wrongPassword.setTextColor(ResTools.getColor(R.color.c10));
        wrongPassword.setText(ResTools.getString(R.string.login_tv_wrong_password));
        wrongPassword.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom);

        containerPassword.addView(wrongPassword, lpC2V3);

        mContainer.addView(containerPassword, lpC2);

        /************** containerOKButton ******************/

        containerOKButton = new RelativeLayout(getContext());
        containerOKButton.setId(R.id.login_rl_ok);
        containerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.login_rl_password);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        okButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        okButton.setId(R.id.login_v_ok);
        okButton.setColor(ResTools.getColor(R.color.c1));
        okButton.setAlpha(0.3f);

        containerOKButton.addView(okButton);

        okButtonArrow = new ImageView(getContext());
        okButtonArrow.setId(R.id.login_iv_ok);
        okButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        containerOKButton.addView(okButtonArrow, lpC3V1);

        mContainer.addView(containerOKButton, lpC3);


    }

    @Override
    public void onClick(View v) {
        if(v == hidePassword) {
            if (isHidden) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                hidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_close));
                isHidden = false;
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                hidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
                isHidden = true;
            }
            etPassword.postInvalidate();
            CharSequence charSequence = etPassword.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }
        if (v == containerOKButton) {
            mCallBacks.handleAction(ActionId.CommitLoginClick, null, null);
        }
    }
}
