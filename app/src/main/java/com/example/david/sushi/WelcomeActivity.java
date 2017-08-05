package com.example.david.sushi;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.CallbackWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 21/03/2017.
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.b_go_to_main)
    Button bGoToMain;
    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ib_setting)
    ImageButton ibSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_welcome);
        ButterKnife.bind(this);
        customizeFonts(tvWelcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvWelcome.setText(getSession().getWelcomeText());
    }

    private void setListener() {
        bGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<CallbackWrapper> call = getService().getMejaStatus(getSession().getNoMeja());
                call.enqueue(new Callback<CallbackWrapper>() {
                    @Override
                    public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus_meja().matches(Constant.AVAILABLE)) {
                                goToMainPage();
                            } else {
                                Toast.makeText(WelcomeActivity.this, getString(R.string.table_not_available),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

                    }
                });
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVerifyBack();
            }
        });
    }

    private void goToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        Constant.mainActivity = null;
        Call<CallbackWrapper> call = getService().openTable("status","2",getSession().getNoMeja());
        call.enqueue(new Callback<CallbackWrapper>() {
            @Override
            public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {

            }

            @Override
            public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

            }
        });
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //finish();
    }

    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToSettingPage() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void showVerifyBack() {
        final Dialog dialog = new Dialog(this, R.style.StyleDialog);
        dialog.setContentView(R.layout.layout_dialog_password);
        RelativeLayout rlWrapper = (RelativeLayout) dialog.findViewById(R.id.rl_wrapper);
        final PinLockView pinLockView = (PinLockView) dialog.findViewById(R.id.pin_lock_view);
        IndicatorDots indicatorDots = (IndicatorDots) dialog.findViewById(R.id.indicator_dots);
        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String s) {
                Call<CallbackWrapper> callLogin = getService().login(s);
                callLogin.enqueue(new Callback<CallbackWrapper>() {
                    @Override
                    public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                        if (response.isSuccessful() && response.body().getCode().equals(Constant.API_SUCCESS)) {
                            dialog.dismiss();
                            goToSettingPage();
                        } else {
                            YoYo.with(Techniques.Shake)
                                    .duration(500)
                                    .repeat(1).onEnd(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    pinLockView.resetPinLockView();
                                }
                            })
                                    .playOn(pinLockView);
                        }
                    }

                    @Override
                    public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {
                        YoYo.with(Techniques.Shake)
                                .duration(500)
                                .repeat(1).onEnd(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {
                                pinLockView.resetPinLockView();
                            }
                        })
                                .playOn(pinLockView);
                    }
                });
            }

            @Override
            public void onEmpty() {

            }

            @Override
            public void onPinChange(int i, String s) {

            }
        });

        rlWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
