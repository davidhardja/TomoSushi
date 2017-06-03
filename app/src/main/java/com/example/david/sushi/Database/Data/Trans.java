package com.example.david.sushi.Database.Data;

/**
 * Created by David on 03/06/2017.
 */

public class Trans extends BaseDao {

    private String id;
    private String no_meja;
    private String no_trans;
    private String id_order;
    private String total;
    private String diskon;
    private String ppn;
    private String grandtotal;
    private String status;
    private String printed;
    private String count_print;
    private String bayar;
    private String kembalian;
    private String tipe_bayar;
    private String pembayaran;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo_meja() {
        return no_meja;
    }

    public void setNo_meja(String no_meja) {
        this.no_meja = no_meja;
    }

    public String getNo_trans() {
        return no_trans;
    }

    public void setNo_trans(String no_trans) {
        this.no_trans = no_trans;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtota(String grandtota) {
        this.grandtotal = grandtota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrinted() {
        return printed;
    }

    public void setPrinted(String printed) {
        this.printed = printed;
    }

    public String getCount_print() {
        return count_print;
    }

    public void setCount_print(String count_print) {
        this.count_print = count_print;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    public String getTipe_bayar() {
        return tipe_bayar;
    }

    public void setTipe_bayar(String tipe_bayar) {
        this.tipe_bayar = tipe_bayar;
    }

    public String getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }
}
