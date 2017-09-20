package com.example.david.sushi;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.sushi.Common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by David on 01/04/2017.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_base_url)
    TextView tvBaseUrl;
    @BindView(R.id.et_base_url)
    EditText etBaseUrl;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.et_timer)
    EditText etTimer;
    @BindView(R.id.tv_welcome)
    TextView tvWelcomeText;
    @BindView(R.id.et_welcome)
    EditText etWelcomeText;
    @BindView(R.id.tv_id_meja)
    TextView tvIdMeja;
    @BindView(R.id.et_no_meja)
    EditText etNoMeja;

    @BindView(R.id.b_save_setting)
    Button bSave;
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_setting);
        ButterKnife.bind(this);
        customizeFonts(tvSetting, tvBaseUrl, etBaseUrl, bSave, tvTimer,etTimer, tvWelcomeText,etWelcomeText);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
        setView();
    }

    private void setView(){
        etTimer.setText(String.valueOf(getSession().getTimer()/1000));
        etBaseUrl.setText(getSession().getBaseUrl());
        etWelcomeText.setText(getSession().getWelcomeText());
        etNoMeja.setText(getSession().getNoMeja());
    }

    private void setListener() {
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSession().setWelcomeText(etWelcomeText.getText().toString());
                getSession().setNoMeja(etNoMeja.getText().toString());
                getSession().setTimer(Long.valueOf(etTimer.getText().toString())*1000);
                getSession().setBaseUrl(etBaseUrl.getText().toString());
                ((Application) getApplication()).changeBaseUrl();
                finish();
            }
        });


        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
