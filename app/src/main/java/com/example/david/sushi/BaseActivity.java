package com.example.david.sushi;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.DatabaseHelper;
import com.example.david.sushi.Service.ApiService;
import com.example.david.sushi.Tools.SessionManagement;

/**
 * Created by David on 20/03/2017.
 */

public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    }

    public ApiService getService() {
        return ((Application) getApplication()).getService();
    }

    public DatabaseHelper getHelper() {
        return ((Application) getApplication()).getHelper();
    }

    public SessionManagement getSession() {
        return ((Application) getApplication()).getSession();
    }

    protected void customizeFonts(TextView... textViews) {
        for (TextView textView : textViews) {
            Typeface typeFace = Typeface.createFromAsset(getAssets(), Constant.DEFAULT_FONT);
            textView.setTypeface(typeFace);
        }
    }

}
