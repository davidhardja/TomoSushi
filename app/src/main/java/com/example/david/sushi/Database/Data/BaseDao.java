package com.example.david.sushi.Database.Data;

import com.example.david.sushi.Common.StringHelper;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by David on 22/03/2017.
 */

public class BaseDao {
    public static final String CREATED_AT = "created_at";
    public static final String UPDATE_AT = "update_at";

    @DatabaseField
    private String created_at;
    @DatabaseField
    private String updated_at;

    public String getCreated_at() {
        return StringHelper.getCleanString(created_at);
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return StringHelper.getCleanString(updated_at);
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
