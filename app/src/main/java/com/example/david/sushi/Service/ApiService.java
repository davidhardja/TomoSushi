package com.example.david.sushi.Service;

import com.example.david.sushi.Database.Data.CallbackWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Path("id") String id
    );

    @POST("public/api/order")
    Call<CallbackWrapper> postOrder(
            @Query("no_meja") String idMeja,
            @Query("total_qty") String totalQty,
            @Query("creator") String creator,
            @Query("id_menu") String idMenu,
            @Query("qty") String qty
    );

    @GET("public/api/order/{no_meja}")
    Call<CallbackWrapper> getOrder(
            @Path("no_meja") String noMeja
    );


}
