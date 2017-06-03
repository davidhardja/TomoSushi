package com.example.david.sushi;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.DatabaseHelper;
import com.example.david.sushi.Service.ApiService;
import com.example.david.sushi.Tools.SessionManagement;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by David on 20/03/2017.
 */

public class Application extends MultiDexApplication {
    private ApiService service;
    private DatabaseHelper helper;
    private SessionManagement session;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(ApiService.class);

        helper = new DatabaseHelper(this);

        session = new SessionManagement(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
}
