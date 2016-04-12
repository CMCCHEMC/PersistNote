package com.dv.persistnote.business;

import android.content.Context;
import android.text.Editable;
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
 * Created by Admin2 on 2016/4/5.
 */
public class RegisterPasswordScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout containerPassword;

    private RelativeLayout containerOKButton;

    private EditText etPassword;

    private View linePassword;

    private CircleView okButton;

    private ImageView okButtonArrow;

    private ImageView hidePassword;

    private Boolean isHidden = true;

    private Boolean isOkButtonAvailable = false;

    private TextView wrongPassword;

    public RegisterPasswordScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** containerPassword ******************/

        containerPassword = new RelativeLayout(getContext());
        containerPassword.setId(R.id.register_p_rl_password);
        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_p_rl_password_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        etPassword = new EditText(getContext());
        etPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPassword.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etPassword.setId(R.id.register_p_et_password);
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

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 6 && s.toString().length() < 16) {
                    linePassword.setBackgroundColor(ResTools.getColor(R.color.c4));
                    wrongPassword.setText(null);

                } else {
                    if (s.toString().length() < 6) {
                        wrongPassword.setText(ResTools.getString(R.string.register_p_tv_wrong_password_short));
                    } else {
                        wrongPassword.setText(ResTools.getString(R.string.register_p_tv_wrong_password_long));
                    }
                    linePassword.setBackgroundColor(ResTools.getColor(R.color.c10));

                }
                if (s.toString().length() > 0) {
                    isOkButtonAvailable = true;
                    okButton.setAlpha(1.0f);
                } else {
                    isOkButtonAvailable = false;
                    okButton.setAlpha(0.3f);
                }
            }
        });

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerPassword.addView(etPassword, lpC1V1);

        linePassword = new View(getContext());
        linePassword.setId(R.id.register_p_line_password);
        linePassword.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_p_et_password);

        containerPassword.addView(linePassword, lpC1V2);

        hidePassword = new ImageView(getContext());
        hidePassword.setId(R.id.register_p_iv_password);
        hidePassword.setImageDrawable(ResTools.getDrawable(R.drawable.eyes_open));
        hidePassword.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1V3 =
                new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        containerPassword.addView(hidePassword, lpC1V3);

        wrongPassword = new TextView(getContext());
        wrongPassword.setId(R.id.register_p_tv_password_wrong);
        wrongPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        wrongPassword.setTextColor(ResTools.getColor(R.color.c10));
        wrongPassword.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lpC1V4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpC1V4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1V4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V4.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        containerPassword.addView(wrongPassword, lpC1V4);

        mContainer.addView(containerPassword, lpC1);

        /************** containerOKButton ******************/

        containerOKButton = new RelativeLayout(getContext());
        containerOKButton.setId(R.id.register_p_rl_ok);
        containerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC2.addRule(RelativeLayout.BELOW, R.id.register_p_rl_password);
        lpC2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        okButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        okButton.setId(R.id.register_p_v_ok);
        okButton.setColor(ResTools.getColor(R.color.c1));
        okButton.setAlpha(0.3f);

        containerOKButton.addView(okButton);

        okButtonArrow = new ImageView(getContext());
        okButtonArrow.setId(R.id.register_p_iv_ok);
        okButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC2V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        containerOKButton.addView(okButtonArrow, lpC2V1);

        mContainer.addView(containerOKButton, lpC2);
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
            if(isOkButtonAvailable)
                mCallBacks.handleAction(ActionId.CommitRegisterPasswordClick, null, null);
        }
    }
}
