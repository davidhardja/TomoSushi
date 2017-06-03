package com.example.david.sushi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.david.sushi.Common.Constant;
import com.example.david.sushi.Database.Data.Menus;
import com.example.david.sushi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by David on 23/03/2017.
 */

public class CartAdapter extends BaseAdapter {


    private List<Menus> menusList;

    public CartAdapter(Context context, List list) {
        super(context, list);
        menusList = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartAdapter.ViewHolder v = (CartAdapter.ViewHolder) holder;
        v.bind(menusList.get(position));
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart, parent, false);
        return new CartAdapter.ViewHolder(v);
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
        @BindView(R.id.ib_remove)
        Button ibRemove;
        @BindView(R.id.b_plus)
        Button bPlus;
        @BindView(R.id.b_minus)
        Button bMinus;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            customizeFonts(tvNo, tvQuantity, tvName,bPlus,bMinus);
            ibRemove.setOnClickListener(this);
            bPlus.setOnClickListener(this);
            bMinus.setOnClickListener(this);
        }

        private void bind(Menus menus) {
            tvNo.setText(String.valueOf(getAdapterPosition() + 1));
            tvName.setText(menus.getName());
            tvQuantity.setText(String.valueOf(menus.getQuantity()));
            if(getAdapterPosition()%2==0){
                llWrapper.setBackgroundResource(R.color.cF28888);
            }else{
                llWrapper.setBackgroundResource(R.color.cF9AD9D);
            }
        }

        @Override
        public void onClick(View view) {
            if(view == ibRemove){
                menusList.remove(getAdapterPosition());
                Constant.cart.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }else if(view == bPlus){
                menusList.get(getAdapterPosition()).setQuantity(menusList.get(getAdapterPosition()).getQuantity()+1);
//                Constant.cart.get(getAdapterPosition()).setQuantity(Constant.cart.get(getAdapterPosition()).getQuantity()+1);
                tvQuantity.setText(String.valueOf(menusList.get(getAdapterPosition()).getQuantity()));
            }else if(view == bMinus){
                if(menusList.get(getAdapterPosition()).getQuantity()>1){
                    menusList.get(getAdapterPosition()).setQuantity(menusList.get(getAdapterPosition()).getQuantity()-1);
//                    Constant.cart.get(getAdapterPosition()).setQuantity(Constant.cart.get(getAdapterPosition()).getQuantity()-1);
                    tvQuantity.setText(String.valueOf(menusList.get(getAdapterPosition()).getQuantity()));
                }
            }
        }

    }

}
