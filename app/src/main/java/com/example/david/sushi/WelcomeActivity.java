package com.example.david.sushi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.sushi.Common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                goToMainPage();
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
                goToSettingPage();
            }
        });
    }

    private void goToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        Constant.mainActivity = null;
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
}
