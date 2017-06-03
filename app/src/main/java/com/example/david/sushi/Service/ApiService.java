package com.example.david.sushi.Service;

import com.example.david.sushi.Database.Data.CallbackWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by David on 20/03/2017.
 */

public interface ApiService {

    @GET("public/api/menu")
    Call<CallbackWrapper> getAllMenu(

    );

    @GET("public/api/category")
    Call<CallbackWrapper> getAllCategory(

    );

    @GET("public/api/menu/{id}")
    Call<CallbackWrapper> getMenuCategory(
            @Path("id") String token
    );
}
