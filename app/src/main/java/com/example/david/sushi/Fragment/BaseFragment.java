package com.example.david.sushi.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.david.sushi.Application;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.DatabaseHelper;
import com.example.david.sushi.Service.ApiService;
import com.example.david.sushi.Tools.SessionManagement;

/**
 * Created by David on 20/03/2017.
 */

public class BaseFragment extends Fragment {

    protected DatabaseHelper helper;
    protected SessionManagement session;
    protected ApiService service;

    private int parentIndex = 0;

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception n) {

        }
    }

    protected void customizeFonts(TextView... textViews) {
        for (TextView textView : textViews) {
            Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), Constant.DEFAULT_FONT);
            textView.setTypeface(typeFace);
        }
    }

    protected void customizeFont(TextView textView, String fontFace) {
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), fontFace);
        textView.setTypeface(typeFace);
    }

    public static void showSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
            inputMethodManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(),0);
        } catch (Exception n) {

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeAllService();
    }

    protected void initializeAllService() {
        if (service == null) {
            service = ((Application) getActivity().getApplication()).getService();
            helper = ((Application) getActivity().getApplication()).getHelper();
            session = ((Application) getActivity().getApplication()).getSession();
        }
    }

    protected boolean isNetworkConnected() {
        if (getActivity() != null) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo ni = cm.getActiveNetworkInfo();
                return ni != null;
            }
            return false;
        }
        return false;
    }

    public ApiService getService() {
        return service;
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    public SessionManagement getSession() {
        return session;
    }

    public int getParentTab() {
        return parentIndex;
    }

    public void setParentTab(int parentIndex) {
        this.parentIndex = parentIndex;
    }
}
