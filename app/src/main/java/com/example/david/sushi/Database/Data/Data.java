package com.example.david.sushi.Database.Data;

import java.util.List;

/**
 * Created by David on 03/06/2017.
 */

public class Data extends BaseDao {

    //===Data Universal===//
    private String id;
    private String nama;
    private String modifier;
    private String creator;
    private String status;
    //===Data Menu===//

    private String additional;
    private String harga;
    private String Id_category_menu;
    private String Keterangan;
    private String qty;
    private String picture_url;

    //===Data Category===//
    private String id_parent;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getId_parent() {
        return id_parent;
    }

    public void setId_parent(String id_parent) {
        this.id_parent = id_parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getId_category_menu() {
        return Id_category_menu;
    }

    public void setId_category_menu(String id_category_menu) {
        Id_category_menu = id_category_menu;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
}
