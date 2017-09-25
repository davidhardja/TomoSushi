package com.example.david.sushi.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david.sushi.Adapter.MenuAdapter;
import com.example.david.sushi.Database.Data.CallbackWrapper;
import com.example.david.sushi.Database.Data.Data;
import com.example.david.sushi.Database.Data.Menus;
import com.example.david.sushi.R;
import com.example.david.sushi.Tools.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by David on 20/03/2017.
 */

public class MenuFragment extends BaseFragment {

    @BindView(R.id.rv_list_menu)
    RecyclerView rvListMenu;
    MenuAdapter menuAdapter;
    List<Menus> menusList = new ArrayList<>();
    String id = "-1";


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_menu, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        String str = bundle.getString("id");
        this.id = str;
        System.out.println("CHECK ID CAT: "+str);
        setData();
        setMenu();
    }

    private void setMenu() {
        if (menuAdapter == null) {
            menuAdapter = new MenuAdapter(getActivity(), menusList);
        }
        rvListMenu.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        rvListMenu.addItemDecoration(itemDecoration);
        rvListMenu.setAdapter(menuAdapter);
    }

    private void setData() {

        Call<CallbackWrapper> call = getService().getMenuCategory(id);
        call.enqueue(new Callback<CallbackWrapper>() {
            @Override
            public void onResponse(Call<CallbackWrapper> call, Response<CallbackWrapper> response) {
                if(response.isSuccessful()){
                    List<Data> dataList = response.body().getData();
                    menusList.clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        Menus m = new Menus();
                        m.setId(dataList.get(i).getId());
                        m.setName(dataList.get(i).getNama());
                        m.setHarga(Integer.valueOf(dataList.get(i).getHarga()));
                        m.setPicture_url(dataList.get(i).getPicture_url());
                        m.setKeterangan(dataList.get(i).getKeterangan());
                        m.setQuantity(0);
                        menusList.add(m);
                        menuAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CallbackWrapper> call, Throwable throwable) {

            }
        });

//        System.out.println("CHECK ID2: "+ this.id+" "+this);
//        menusList.clear();
//        for (int i = 0; i < 15; i++) {
//            Menus m = new Menus();
//            m.setId(String.valueOf(i));
//            m.setName("Sushi " + i);
//            m.setHarga(i * 5000);
//            m.setQuantity(0);
//            menusList.add(m);
//        }
    }

}
