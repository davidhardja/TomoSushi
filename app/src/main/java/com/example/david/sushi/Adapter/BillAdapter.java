package com.example.david.sushi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.david.sushi.Database.Data.Menus;
import com.example.david.sushi.R;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitHelper;

/**
 * Created by David on 24/03/2017.
 */

public class BillAdapter extends BaseAdapter {
    private List<Menus> menusList;

    public BillAdapter(Context context, List list) {
        super(context, list);
        menusList = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BillAdapter.ViewHolder v = (BillAdapter.ViewHolder) holder;
        v.bind(menusList.get(position));
    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bill, parent, false);
        return new BillAdapter.ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ll_wrapper)
        LinearLayout llWrapper;
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            customizeFonts(tvNo, tvName, tvQuantity, tvPrice, tvSubtotal);
        }

        private void bind(Menus menus) {
            SpannableStringBuilder quantityPrice = new SpannableStringBuilder(MessageFormat.format(context.getString(R.string.rupiah), menus.getHarga()));
            SpannableStringBuilder quantityTotal = new SpannableStringBuilder(MessageFormat.format(context.getString(R.string.rupiah), menus.getHarga() * menus.getQuantity()));
            tvNo.setText(String.valueOf(getAdapterPosition() + 1));
            tvName.setText(menus.getName());
            tvPrice.setText(quantityPrice);
            tvQuantity.setText(String.valueOf(menus.getQuantity()));
            tvSubtotal.setText(quantityTotal);
            AutofitHelper.create(tvPrice);
            AutofitHelper.create(tvSubtotal);
            if (getAdapterPosition() % 2 == 0) {
                llWrapper.setBackgroundResource(R.color.cF28888);
            } else {
                llWrapper.setBackgroundResource(R.color.cF9AD9D);
            }
        }

        @Override
        public void onClick(View view) {

        }

    }
}
