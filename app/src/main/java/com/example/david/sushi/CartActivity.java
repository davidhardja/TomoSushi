package com.example.david.sushi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.sushi.Adapter.CartAdapter;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.CallbackWrapper;
import com.example.david.sushi.Database.Data.Menus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 23/03/2017.
 */

public class CartActivity extends BaseActivity {
    @BindView(R.id.rv_cart)
    RecyclerView rvCart;
    @BindView(R.id.b_order)
    Button bOrder;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    CartAdapter cartAdapter;
    List<Menus> menusList = new ArrayList<>();
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_cart_list);
        ButterKnife.bind(this);
        customizeFonts(tvName, tvNo, tvQuantity, bOrder, tvTitle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
        setView();
    }

    private void setListener() {
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProcess();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setView() {
        menusList.clear();
        menusList.addAll(Constant.cart);

        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(this, menusList);
        }
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);
    }

    int totalQty = 0;
    String idMenu ="";
    String qty = "";
    String creator = "Meja 1";
    String keterangan = "";

    private void showProcess() {
        final Dialog dialog = new Dialog(this, R.style.StyleDialog);
        dialog.setContentView(R.layout.layout_dialog_order);
        TextView tvOrder = (TextView) dialog.findViewById(R.id.tv_order);
        Button bOk = (Button) dialog.findViewById(R.id.b_ok);
        Button bNo = (Button) dialog.findViewById(R.id.b_no);



        customizeFonts(tvOrder);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Constant.cart.size(); i++) {
                    boolean find = false;
                    for (int j = 0; j < Constant.bill.size(); j++) {
                        if (Constant.bill.get(j).getId().matches(Constant.cart.get(i).getId())) {
                            find = true;
                            Constant.cart.get(i).setQuantity(Constant.cart.get(i).getQuantity() + Constant.bill.get(j).getQuantity());
                        }
                    }

                    if (!find) {
                        Constant.bill.add(Constant.cart.get(i));
                    }
                }

                for (int i=0;i<menusList.size();i++){
                    totalQty = totalQty+ menusList.get(i).getQuantity();
                    idMenu = idMenu+menusList.get(i).getId() +"||";
                    qty = qty+ menusList.get(i).getQuantity()+"||";
                    keterangan = keterangan + menusList.get(i).getKeterangan()+"||";
                }

                System.out.println("CHECK PARAMETER: "+ totalQty);
                System.out.println("CHECK PARAMETER: "+ idMenu);
                System.out.println("CHECK PARAMETER: "+ qty);


                Call<CallbackWrapper> orderCall = getService().postOrder(getSession().getNoMeja(),String.valueOf(totalQty),creator,idMenu,qty,keterangan);
                orderCall.enqueue(new Callback<CallbackWrapper>() {
                    @Override
                    public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                        if(response.isSuccessful()){
                            System.out.println("RESPONSE: "+ response.body());
                        }else{
                            System.out.println("RESPONSE ERROR: "+ response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

                    }
                });
                Constant.cart.clear();
                menusList.clear();



                dialog.dismiss();
                CartActivity.this.finish();
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
