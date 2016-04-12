package com.dv.persistnote.business;

import android.content.Context;
import android.graphics.Color;
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
public class RegisterUserInfoScreen extends DefaultScreen implements View.OnClickListener{

    private RelativeLayout mContainer;

    private RelativeLayout containerUserName;

    private RelativeLayout containerUserSex;

    private RelativeLayout containerOKButton;

    private EditText etUserName;

    private View lineUserName;

    private TextView wrongUserName;

    private View backgroundMale;

    private View backgroundFemale;

    private ImageView ivMale;

    private ImageView ivFemale;

    private CircleView okButton;

    private ImageView okButtonArrow;

    private boolean isOkButtonAvailable = true;

    public RegisterUserInfoScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setTitle(ResTools.getString(R.string.register));
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        setContent(mContainer);

        mContainer.removeAllViews();

        /************** containerUserName ******************/

        containerUserName = new RelativeLayout(getContext());
        containerUserName.setId(R.id.register_u_rl_user_name);

        RelativeLayout.LayoutParams lpC1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_rl_height));
        lpC1.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_user_name_margin_top);
        lpC1.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC1.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);

        etUserName = new EditText(getContext());
        etUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h1));
        etUserName.setPadding(0, ResTools.getDimenInt(R.dimen.common_et_padding_top),
                0, ResTools.getDimenInt(R.dimen.common_et_padding_bottom));
        etUserName.setId(R.id.register_u_et_user_name);
        etUserName.setBackgroundColor(ResTools.getColor(R.color.c4));
        etUserName.setHint(R.string.register_u_et_hint_user_name);
        etUserName.setSingleLine(true);
        etUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etUserName.setHint(R.string.common_et_hint_phone_number);
                } else {
                    etUserName.setHint(null);
                }
            }
        });
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(etUserName, R.drawable.color_cursor);
        } catch (Exception ignored) {
            Log.e(TAG, "Register Init : color_cursor Error");
        }

        RelativeLayout.LayoutParams lpC1V1 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        containerUserName.addView(etUserName, lpC1V1);

        lineUserName = new View(getContext());
        lineUserName.setId(R.id.register_u_line_user_name);
        lineUserName.setBackgroundColor(ResTools.getColor(R.color.c1));

        RelativeLayout.LayoutParams lpC1V2 =
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.common_line_height));
        lpC1V2.addRule(RelativeLayout.BELOW, R.id.register_u_et_user_name);

        containerUserName.addView(lineUserName, lpC1V2);

        wrongUserName = new TextView(getContext());
        wrongUserName.setId(R.id.register_u_tv_user_name_wrong);
        wrongUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.h4));
        wrongUserName.setTextColor(ResTools.getColor(R.color.c10));
        wrongUserName.setText(ResTools.getString(R.string.register_u_tv_wrong_user_name_long));
        wrongUserName.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lpC1V3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC1V3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpC1V3.bottomMargin = ResTools.getDimenInt(R.dimen.common_margin_bottom_11);

        containerUserName.addView(wrongUserName, lpC1V3);

        mContainer.addView(containerUserName, lpC1);

        /************** containerUserSex ******************/

        containerUserSex = new RelativeLayout(getContext());
        containerUserSex.setId(R.id.register_u_rl_user_sex);

        RelativeLayout.LayoutParams lpC2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_margin_top);
        lpC2.leftMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_left);
        lpC2.rightMargin = ResTools.getDimenInt(R.dimen.common_rl_margin_right);
        lpC2.addRule(RelativeLayout.BELOW, R.id.register_u_rl_user_name);

        backgroundMale = new View(getContext());
        backgroundMale.setId(R.id.register_u_v_user_sex_male);
        backgroundMale.setBackgroundColor(Color.parseColor("#7dd8ef"));
        backgroundMale.setAlpha(0.3f);
        backgroundMale.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height),
                        ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2V1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        containerUserSex.addView(backgroundMale, lpC2V1);

        ivMale = new ImageView(getContext());
        ivMale.setImageDrawable(ResTools.getDrawable(R.drawable.male));
        ivMale.setId(R.id.register_u_iv_user_sex_male);

        RelativeLayout.LayoutParams lpC2V2 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height),
                ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height));
        lpC2V2.addRule(RelativeLayout.CENTER_VERTICAL);
        lpC2V2.leftMargin = ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height);

        containerUserSex.addView(ivMale, lpC2V2);

        backgroundFemale = new View(getContext());
        backgroundFemale.setId(R.id.register_u_v_user_sex_female);
        backgroundFemale.setBackgroundColor(Color.parseColor("#f9a7b8"));
        backgroundFemale.setAlpha(0.3f);
        backgroundFemale.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC2V3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height),
                ResTools.getDimenInt(R.dimen.register_u_rl_user_sex_height));
        lpC2V3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        containerUserSex.addView(backgroundFemale, lpC2V3);

        ivFemale = new ImageView(getContext());
        ivFemale.setImageDrawable(ResTools.getDrawable(R.drawable.female));
        ivFemale.setId(R.id.register_u_iv_user_sex_female);

        RelativeLayout.LayoutParams lpC2V4 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height),
                ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height));
        lpC2V4.addRule(RelativeLayout.CENTER_VERTICAL);
        lpC2V4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpC2V4.rightMargin = ResTools.getDimenInt(R.dimen.register_u_iv_user_sex_width_height);

        containerUserSex.addView(ivFemale, lpC2V4);

        mContainer.addView(containerUserSex, lpC2);

        /************** containerOKButton ******************/

        containerOKButton = new RelativeLayout(getContext());
        containerOKButton.setId(R.id.register_u_rl_ok);
        containerOKButton.setOnClickListener(this);

        RelativeLayout.LayoutParams lpC3 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.common_rl_ok_width_height),
                ResTools.getDimenInt(R.dimen.common_rl_ok_width_height));
        lpC3.topMargin = ResTools.getDimenInt(R.dimen.register_u_rl_ok_margin_top);
        lpC3.addRule(RelativeLayout.BELOW, R.id.register_u_rl_user_sex);
        lpC3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        okButton = new CircleView(getContext(), ResTools.getDimenInt(R.dimen.common_cv_radius),
                ResTools.getDimenInt(R.dimen.common_cv_radius), ResTools.getDimenInt(R.dimen.common_cv_radius));
        okButton.setId(R.id.register_u_v_ok);
        okButton.setColor(ResTools.getColor(R.color.c1));
        okButton.setAlpha(0.3f);

        containerOKButton.addView(okButton);

        okButtonArrow = new ImageView(getContext());
        okButtonArrow.setId(R.id.register_u_iv_ok);
        okButtonArrow.setImageDrawable(ResTools.getDrawable(R.drawable.arrow_right));

        RelativeLayout.LayoutParams lpC3V1 = new RelativeLayout.LayoutParams(ResTools.getDimenInt(R.dimen.h1), ResTools.getDimenInt(R.dimen.h1));
        lpC3V1.addRule(RelativeLayout.CENTER_IN_PARENT);

        containerOKButton.addView(okButtonArrow, lpC3V1);

        mContainer.addView(containerOKButton, lpC3);

    }

    @Override
    public void onClick(View v) {
        if (v == containerOKButton) {
            if(isOkButtonAvailable)
                mCallBacks.handleAction(ActionId.CommitRegisterUserInfoClick, null, null);
        }
    }
}
