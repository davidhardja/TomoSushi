package com.example.david.sushi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.sushi.Adapter.OrderAdapter;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.CallbackWrapper;
import com.example.david.sushi.Database.Data.Data;
import com.example.david.sushi.Database.Data.Menus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 02/04/2017.
 */

public class OrderActivity extends BaseActivity {
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.b_pay)
    Button bPay;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    OrderAdapter orderAdapter;
    List<Menus> menusList = new ArrayList<>();
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_order);
        ButterKnife.bind(this);
        customizeFonts(tvName, tvNo, tvQuantity, bPay, tvTitle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
        setView();
    }

    private void setListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.bill.size()>0){
                    showProcess();
                }
            }
        });
    }

    private void setView() {
        menusList.clear();
        //menusList.addAll(Constant.bill);

        Call<CallbackWrapper> orderCall = getService().getOrder("1");
        orderCall.enqueue(new Callback<CallbackWrapper>() {
            @Override
            public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                if(response.isSuccessful()){
                    List<Data> dataList = response.body().getData();

                    for(int i=0;i<dataList.size();i++){
                        Menus menu = new Menus();
                        menu.setName(dataList.get(i).getNama());
                        menu.setQuantity(Integer.valueOf(dataList.get(i).getQty()));
                        menu.setHarga(Integer.valueOf(dataList.get(i).getHarga()));
                        menusList.add(menu);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

            }
        });

        if (orderAdapter == null) {
            orderAdapter = new OrderAdapter(this, menusList);
        }
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(orderAdapter);

        if(Constant.bill.size()==0){
            bPay.setAlpha(0.4f);
        }else {
            bPay.setAlpha(1f);
        }
    }

    private void showProcess() {
        final Dialog dialog = new Dialog(this, R.style.StyleDialog);
        dialog.setContentView(R.layout.layout_dialog_confirmation);
        TextView tvConfirmation = (TextView) dialog.findViewById(R.id.tv_confirmation);
        Button bOk = (Button) dialog.findViewById(R.id.b_ok);
        Button bNo = (Button) dialog.findViewById(R.id.b_no);

        customizeFonts(tvConfirmation, bOk, bNo);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(OrderActivity.this, BillActivity.class);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });

        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
