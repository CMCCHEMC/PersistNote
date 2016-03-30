package com.dv.persistnote.business;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.lang.reflect.Field;

/**
 * Created by Admin2 on 2016/3/29.
 */
public class LoginScreen extends DefaultScreen implements View.OnClickListener {

    private LinearLayout mContainer;

    private RelativeLayout containerPhoneNumber;

    private RelativeLayout containerPassword;

    private EditText etPhoneNumber;

    private EditText etPassword;

    private View linePhoneNumber;

    private View linePassword;

    public LoginScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.login));
    }

    private void init() {
        mContainer = new LinearLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        containerPhoneNumber = new RelativeLayout(getContext());
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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


    }

    @Override
    public void onClick(View view) {

    }
}
