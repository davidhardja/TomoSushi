package com.example.david.sushi.Database.Data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by David on 22/03/2017.
 */

public class Menus extends BaseDao {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private Integer harga;
    @DatabaseField
    private Integer quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
