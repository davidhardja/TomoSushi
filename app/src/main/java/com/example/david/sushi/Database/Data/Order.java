package com.example.david.sushi.Database.Data;

/**
 * Created by David on 03/06/2017.
 */

public class Order extends BaseDao {
    private String id_order;
    private String nama;
    private String qty;
    private String harga;

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
