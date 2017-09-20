package com.example.david.sushi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.david.sushi.Adapter.BillAdapter;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.CallbackWrapper;
import com.example.david.sushi.Database.Data.Data;
import com.example.david.sushi.Database.Data.DataBon;
import com.example.david.sushi.Database.Data.Menus;
import com.example.david.sushi.Database.Data.Order;
import com.victor.loading.book.BookLoading;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 24/03/2017.
 */

public class BillActivity extends BaseActivity {

    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.b_pay)
    Button bPay;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_diskon)
    TextView tvDiskon;
    @BindView(R.id.tv_ppn)
    TextView tvPpn;
    @BindView(R.id.tv_grand_total)
    TextView tvGrandTotal;
    @BindView(R.id.tv_no_meja)
    TextView tvNoMeja;


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    @BindView(R.id.rl_wrapper_loading)
    RelativeLayout rlWrapperLoading;
    @BindView(R.id.rotateloading)
    BookLoading rlLoading;

    BillAdapter billAdapter;
    List<Menus> menusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_bill);
        ButterKnife.bind(this);
        customizeFonts(bPay, tvName, tvNo, tvPrice, tvQuantity, tvSubtotal, tvTotal, tvTitle,tvDiskon,tvPpn,tvGrandTotal,tvNoMeja,tvService);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setListener();
        setView();
    }

    private void setListener() {
        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.cart.clear();
                Constant.bill.clear();
                Constant.mainActivity.finish();
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

    private void setView() {
//        for (int i = 0; i < 15; i++) {
//            menusList.add(new Menus());
//        }
        menusList.clear();
        //menusList.addAll(Constant.bill);

        showLoading();
        Call<CallbackWrapper> call = getService().getReceipt(getSession().getNoMeja(),getSession().getNoMeja(),"DINE_IN");
        call.enqueue(new Callback<CallbackWrapper>() {
            @Override
            public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                if(response.isSuccessful()){
                    DataBon dataBon = response.body().getDataBon();
                    System.out.println("CHECK SIZE BON: "+ response.body().getDataBon().getOrder().size());
                    List<Order> orderList = dataBon.getOrder();
                    for (int i=0;i<orderList.size();i++){
                        Menus menu = new Menus();
                        menu.setId(orderList.get(i).getId_order());
                        menu.setName(orderList.get(i).getNama());
                        menu.setQuantity(Integer.valueOf(orderList.get(i).getQty()));
                        menu.setHarga(Integer.valueOf(orderList.get(i).getHarga()));
                        menusList.add(menu);
                    }
                    billAdapter.notifyDataSetChanged();
                    try{
                        SpannableStringBuilder builderTotal = new SpannableStringBuilder(MessageFormat.format(getString(R.string.total_harga), dataBon.getTrans().getTotal()));
                        tvTotal.setText(builderTotal);
                        SpannableStringBuilder builderGrandTotal = new SpannableStringBuilder(MessageFormat.format(getString(R.string.grand_total_harga), dataBon.getTrans().getGrandtotal()));
                        tvGrandTotal.setText(builderGrandTotal);
                        SpannableStringBuilder builderService = new SpannableStringBuilder(MessageFormat.format(getString(R.string.service), dataBon.getTrans().getService()));
                        tvService.setText(builderService);
                        SpannableStringBuilder builderNoMeja = new SpannableStringBuilder(MessageFormat.format(getString(R.string.no_meja),dataBon.getTrans().getNo_meja()));
                        tvNoMeja.setText(builderNoMeja);
                        SpannableStringBuilder builderDiskon= new SpannableStringBuilder(MessageFormat.format(getString(R.string.diskon), dataBon.getTrans().getDiskon()));
                        tvDiskon.setText(builderDiskon);
                        SpannableStringBuilder builderPpn = new SpannableStringBuilder(MessageFormat.format(getString(R.string.ppn), dataBon.getTrans().getPpn()));
                        tvPpn.setText(builderPpn);
                    }catch (Exception e){
                        System.out.println("ERROR: "+e.getMessage());
                    }
                    hideLoading();
                }
            }

            @Override
            public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {
                hideLoading();
            }
        });


        if (billAdapter == null) {
            billAdapter = new BillAdapter(this, menusList);
        }
        rvBill.setLayoutManager(new LinearLayoutManager(this));
        rvBill.setAdapter(billAdapter);

        int total = 0;

        for (int i = 0; i < menusList.size(); i++) {
            total = total + (menusList.get(i).getQuantity() * menusList.get(i).getHarga());
        }
    }

    public void showLoading() {
        rlWrapperLoading.setVisibility(View.VISIBLE);
        rlLoading.start();
    }

    public void hideLoading() {
        rlWrapperLoading.setVisibility(View.GONE);
        rlLoading.stop();

    }
}
