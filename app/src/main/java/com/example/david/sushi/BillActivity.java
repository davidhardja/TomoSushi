package com.example.david.sushi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david.sushi.Adapter.BillAdapter;
import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.Menus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.ib_back)
    ImageButton ibBack;

    BillAdapter billAdapter;
    List<Menus> menusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_bill);
        ButterKnife.bind(this);
        customizeFonts(bPay, tvName, tvNo, tvPrice, tvQuantity, tvSubtotal, tvTotal, tvTitle);
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
        menusList.addAll(Constant.bill);

        if (billAdapter == null) {
            billAdapter = new BillAdapter(this, menusList);
        }
        rvBill.setLayoutManager(new LinearLayoutManager(this));
        rvBill.setAdapter(billAdapter);

        int total = 0;

        for (int i = 0; i < menusList.size(); i++) {
            total = total + (menusList.get(i).getQuantity() * menusList.get(i).getHarga());
        }
        SpannableStringBuilder builderTotal = new SpannableStringBuilder(MessageFormat.format(getString(R.string.rupiah), total));
        tvTotal.setText(builderTotal);
    }
}
