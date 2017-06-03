package com.example.david.sushi;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    //    @BindView(R.id.et_username)
//    EditText etUsername;
//    @BindView(R.id.et_password)
//    EditText etPassword;
    @BindView(R.id.pin_lock_view)
    PinLockView pinLockView;
    @BindView(R.id.indicator_dots)
    IndicatorDots indicatorDots;
    //    @BindView(R.id.b_login)
//    Button bLogin;
    @BindView(R.id.b_exit)
    Button bExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
    }

    private void goToWelcomePage() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setListener() {
        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String s) {
                if (s.matches("1234")) {
                    goToWelcomePage();
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
            public void onEmpty() {

            }

            @Override
            public void onPinChange(int i, String s) {

            }
        });

//        bLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToWelcomePage();
//            }
//        });
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
