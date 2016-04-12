package com.dv.persistnote.business;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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

/**
 * Created by Admin2 on 2016/3/29.
 */
public class RegisterHomeScreen extends DefaultScreen implements View.OnClickListener {

    private RelativeLayout mContainer;

    private RelativeLayout containerPhoneNumber;

    private RelativeLayout containerPhoneNumberRemoveEZTouch;

    private RelativeLayout containerCode;

    private RelativeLayout containerCodeRemoveEZTouch;

    private RelativeLayout containerOKButton;

    private EditText etPhoneNumber;

    private EditText etCode;

    private Boolean isOkButtonAvailable = false;

    private Button getCode;

    private View linePhoneNumber;

    private View lineCode;

    private ImageView removeCode;

    private ImageView removePhoneNumber;

    private CircleView okButton;

    private ImageView okButtonArrow;

    private TextView wrongPhoneNumber;

    private TextView wrongCode;

    public RegisterHomeScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** containerPhoneNumber ******************/

        containerPhoneNumber = new RelativeLayout(getContext());
        containerPhoneNumber.setId(R.id.register_rl_phone_number);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_rl_phone_number_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        etPhoneNumber = new EditText(getContext());
        etPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etPhoneNumber.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etPhoneNumber.setId(R.id.register_et_phone_number);
        etPhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c4));
        etPhoneNumber.setHint(R.string.common_et_hint_phone_number);
        etPhoneNumber.setSingleLine(true);
        etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etPhoneNumber.setHint(R.string.common_et_hint_phone_number);
                    removePhoneNumber.setVisibility(View.GONE);
                } else {
                    etPhoneNumber.setHint(null);
                    if (etPhoneNumber.getText().toString().length() > 0)
                        removePhoneNumber.setVisibility(View.VISIBLE);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etPhoneNumber, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && etCode.getText().toString().length() > 0) {
                    isOkButtonAvailable = true;
                    okButton.setAlpha(1.0f);
                } else {
                    isOkButtonAvailable = false;
                    okButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    removePhoneNumber.setVisibility(View.VISIBLE);
                } else {
                    removePhoneNumber.setVisibility(View.GONE);
                }
            }
        });

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpC1V1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_remove_width);

        containerPhoneNumber.addView(etPhoneNumber, lpC1V1);

        linePhoneNumber = new View(getContext());
        linePhoneNumber.setId(R.id.register_line_phone_number);
        linePhoneNumber.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_et_phone_number);

        containerPhoneNumber.addView(linePhoneNumber, lpC1V2);

        /** EZ Touch start **/

        containerPhoneNumberRemoveEZTouch = new RelativeLayout(getContext());
        containerPhoneNumberRemoveEZTouch.setId(R.id.register_rl_phone_number_remove);
        containerPhoneNumberRemoveEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC1C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC1C1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        removePhoneNumber = new ImageView(getContext());
        removePhoneNumber.setId(R.id.register_iv_code);
        removePhoneNumber.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        removePhoneNumber.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC1C1V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        containerPhoneNumberRemoveEZTouch.addView(removePhoneNumber, lpC1C1V1);

        containerPhoneNumber.addView(containerPhoneNumberRemoveEZTouch, lpC1C1);

        /** EZ Touch end **/

        mContainer.addView(containerPhoneNumber, lpC1);

        /************** containerCode ******************/

        containerCode = new RelativeLayout(getContext());
        containerCode.setId(R.id.register_rl_code);
        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.register_rl_code_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.register_rl_phone_number);

        getCode = new Button(getContext());
        getCode.setId(R.id.register_bt_code);
        getCode.setBackgroundColor(ResTools.getColor(R.color.c2));
        getCode.setAlpha(0.2f);
        getCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        getCode.setTextColor(ResTools.getColor(R.color.c4));
        getCode.setText(ResTools.getString(R.string.register_bt_code));

        RelativeLayout.LayoutParams lpC2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_bt_code_width),
                ResTools.getDimenInt(R.dimen.register_bt_code_height));
        lpC2V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        containerCode.addView(getCode, lpC2V1);

        etCode = new EditText(getContext());
        etCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etCode.setId(R.id.register_et_code);
        etCode.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etCode.setBackgroundColor(ResTools.getColor(R.color.c4));
        etCode.setHint(R.string.register_et_hint_code);
        etCode.setSingleLine(true);
        etCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etCode.setHint(R.string.register_et_hint_code);
                    removeCode.setVisibility(View.GONE);
                } else {
                    etCode.setHint(null);
                    if (etCode.getText().toString().length() > 0)
                    removeCode.setVisibility(View.VISIBLE);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etCode, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && etPhoneNumber.getText().toString().length() > 0) {
                    isOkButtonAvailable = true;
                    okButton.setAlpha(1.0f);
                } else {
                    isOkButtonAvailable = false;
                    okButton.setAlpha(0.3f);
                }
                if (s.toString().length() > 0) {
                    removeCode.setVisibility(View.VISIBLE);
                } else {
                    removeCode.setVisibility(View.GONE);
                }
            }
        });

        RelativeLayout.LayoutParams lpC2V2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lpC2V2.addRule(RelativeLayout.LEFT_OF, R.id.register_bt_code);
        lpC2V2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpC2V2.rightMargin = ResTools.getDimenInt(R.dimen.register_et_code_margin_right);

        containerCode.addView(etCode, lpC2V2);

        lineCode = new View(getContext());
        lineCode.setId(R.id.register_line_code);
        lineCode.setBackgroundColor(ResTools.getColor(R.color.c1));
        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_line_height));
        lpC2V3.addRule(RelativeLayout.BELOW, R.id.register_et_code);
        lpC2V3.addRule(RelativeLayout.LEFT_OF, R.id.register_bt_code);
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpC2V3.rightMargin = ResTools.getDimenInt(R.dimen.register_line_code_margin_right);

        containerCode.addView(lineCode, lpC2V3);

        /** EZ Touch start **/

        containerCodeRemoveEZTouch = new RelativeLayout(getContext());
        containerCodeRemoveEZTouch.setId(R.id.register_rl_code_remove);
        containerCodeRemoveEZTouch.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2C1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_remove_width),
                LayoutParams.MATCH_PARENT);
        lpC2C1.addRule(RelativeLayout.LEFT_OF, R.id.register_bt_code);
        lpC2C1.rightMargin = ResTools.getDimenInt(R.dimen.register_rl_code_remove_margin_right);

        removeCode = new ImageView(getContext());
        removeCode.setId(R.id.register_iv_code);
        removeCode.setImageDrawable(ResTools.getDrawable(R.drawable.delete));
        removeCode.setVisibility(View.GONE);

        RelativeLayout.LayoutParams lpC2C1V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC2C1V1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2C1V1.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        containerCodeRemoveEZTouch.addView(removeCode, lpC2C1V1);

        containerCode.addView(containerCodeRemoveEZTouch, lpC2C1);

        /** EZ Touch end **/

        mContainer.addView(containerCode, lpC2);

        /************** containerOKButton ******************/

        containerOKButton = new RelativeLayout(getContext());
        containerOKButton.setId(R.id.register_rl_ok);
        containerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.common_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.register_rl_code);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        okButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        okButton.setId(R.id.register_v_ok);
        okButton.setColor(ResTools.getColor(R.color.c1));
        okButton.setAlpha(0.3f);

        containerOKButton.addView(okButton);

        okButtonArrow = new ImageView(getContext());
        okButtonArrow.setId(R.id.register_iv_ok);
        okButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        containerOKButton.addView(okButtonArrow, lpC3V1);

        mContainer.addView(containerOKButton, lpC3);

        /************** wrongPhoneNumber ******************/

        /************** wrongCode ******************/

    }

    @Override
    public void onClick(View v) {
        if (v == containerOKButton) {
            if(isOkButtonAvailable)
                mCallBacks.handleAction(ActionId.CommitRegisterHomeClick, null, null);
        }
        if (v == containerCodeRemoveEZTouch) {
            etCode.setText(null);
        }
        if (v == containerPhoneNumberRemoveEZTouch) {
            etPhoneNumber.setText(null);
        }
    }
}
