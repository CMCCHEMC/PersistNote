package com.dv.persistnote.business;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.CustomEditText;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.lang.reflect.Field;

/**
 * Created by Admin2 on 2016/3/29.
 */
public class LoginScreen extends DefaultScreen implements View.OnClickListener {

    private LinearLayout mContainer;

    private EditText etPhoneNumber;

    public LoginScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.login));
    }

    private void init() {
        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.VERTICAL);
        setContent(mContainer);

        mContainer.removeAllViews();

        etPhoneNumber = new EditText(getContext());
        etPhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPhoneNumber.setHint(R.string.login_et_hint_phone_number);
        etPhoneNumber.setSingleLine(true);
        etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
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

        LinearLayout.LayoutParams lp1 =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp1.topMargin = ResTools.getDimenInt(R.dimen.login_et_margin_top);
        lp1.leftMargin = ResTools.getDimenInt(R.dimen.login_et_margin_left);
        lp1.rightMargin = ResTools.getDimenInt(R.dimen.login_et_margin_right);

        mContainer.addView(etPhoneNumber, lp1);

        View line1 = new View(getContext());
        line1.setBackgroundColor(ResTools.getColor(R.color.c1));

        LinearLayout.LayoutParams lp2 =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lp2.topMargin = ResTools.getDimenInt(R.dimen.common_line_margin_top);
        lp2.leftMargin = ResTools.getDimenInt(R.dimen.login_et_margin_left);
        lp2.rightMargin = ResTools.getDimenInt(R.dimen.login_et_margin_right);

        mContainer.addView(line1, lp2);

    }

    @Override
    public void onClick(View view) {

    }
}
