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

    public LoginScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.login));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        // containerPhoneNumber

        containerPhoneNumber = new RelativeLayout(getContext());
        containerPhoneNumber.setId(R.id.login_rl_phone_number);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp1.topMargin = ResTools.getDimenInt(R.dimen.login_rl_phone_number_margin_top);
        lp1.leftMargin = ResTools.getDimenInt(R.dimen.login_rl_margin_left);
        lp1.rightMargin = ResTools.getDimenInt(R.dimen.login_rl_margin_right);

        etPhoneNumber = new EditText(getContext());
        etPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPhoneNumber.setPadding(0, ResTools.getDimenInt(R.dimen.login_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.login_et_padding_bottom));
        etPhoneNumber.setId(R.id.login_et_phone_number);
        etPhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPhoneNumber.setHint(R.string.login_et_hint_phone_number);
        etPhoneNumber.setSingleLine(true);
        etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etPhoneNumber.setHint(R.string.login_et_hint_phone_number);
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

        RelativeLayout.LayoutParams lp2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerPhoneNumber.addView(etPhoneNumber, lp2);

        linePhoneNumber = new View(getContext());
        linePhoneNumber.setId(R.id.login_line_phone_number);
        linePhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lp3 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lp3.addRule(RelativeLayout.BELOW, R.id.login_et_phone_number);

        containerPhoneNumber.addView(linePhoneNumber, lp3);

        mContainer.addView(containerPhoneNumber, lp1);

        // containerPassword

        containerPassword = new RelativeLayout(getContext());
        containerPassword.setId(R.id.login_rl_password);
        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp4.topMargin = ResTools.getDimenInt(R.dimen.login_rl_phone_number_margin_top);
        lp4.leftMargin = ResTools.getDimenInt(R.dimen.login_rl_margin_left);
        lp4.rightMargin = ResTools.getDimenInt(R.dimen.login_rl_margin_right);
        lp4.addRule(RelativeLayout.BELOW, R.id.login_rl_phone_number);

        etPassword = new EditText(getContext());
        etPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPassword.setPadding(0, ResTools.getDimenInt(R.dimen.login_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.login_et_padding_bottom));
        etPassword.setId(R.id.login_et_password);
        etPassword.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPassword.setHint(R.string.login_et_hint_password);
        etPassword.setSingleLine(true);
        etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etPassword.setHint(R.string.login_et_hint_password);
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

        containerPassword.addView(etPassword, lp2);

        linePassword = new View(getContext());
        linePassword.setId(R.id.login_line_password);
        linePassword.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lp5 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lp5.addRule(RelativeLayout.BELOW, R.id.login_et_password);

        containerPassword.addView(linePassword, lp5);

        mContainer.addView(containerPassword, lp4);

        // containerOKButton

        containerOKButton = new RelativeLayout(getContext());
        containerOKButton.setId(R.id.login_rl_button);

        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.login_rl_button_width_height),
                ResTools.getDimenInt(R.dimen.login_rl_button_width_height));
        lp6.topMargin = ResTools.getDimenInt(R.dimen.login_rl_button_margin_top);
        lp6.addRule(RelativeLayout.BELOW, R.id.login_rl_password);
        lp6.addRule(RelativeLayout.CENTER_HORIZONTAL);

        okButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.login_bt_radius),
                ResTools.getDimenInt(R.dimen.login_bt_radius), ResTools.getDimenInt(R.dimen.login_bt_radius));
        okButton.setColor(ResTools.getColor(R.color.c1));
        okButton.setAlpha(0.3f);

        containerOKButton.addView(okButton);

        okButtonArrow = new ImageView(getContext());
        okButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp7.addRule(RelativeLayout.CENTER_IN_PARENT);

        containerOKButton.addView(okButtonArrow, lp7);

        mContainer.addView(containerOKButton, lp6);


    }

    @Override
    public void onClick(View view) {

    }
}
