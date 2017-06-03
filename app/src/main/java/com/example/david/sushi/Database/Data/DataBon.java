package com.example.david.sushi.Database.Data;

import java.util.List;

/**
 * Created by David on 03/06/2017.
 */

public class DataBon extends BaseDao{
    private Trans trans;
    private List<Order> order;

    public Trans getTrans() {
        return trans;
    }

    public void setTrans(Trans trans) {
        this.trans = trans;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
