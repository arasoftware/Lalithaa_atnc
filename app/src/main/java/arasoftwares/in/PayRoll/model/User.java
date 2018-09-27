package arasoftwares.in.PayRoll.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static arasoftwares.in.PayRoll.Utils.Constants.AUTHORIZE_TOKEN;
import static arasoftwares.in.PayRoll.Utils.Constants.MSG;
import static arasoftwares.in.PayRoll.Utils.Constants.PARAM_BRANCH_ID;
import static arasoftwares.in.PayRoll.Utils.Constants.PARAM_PASSWORD;
import static arasoftwares.in.PayRoll.Utils.Constants.PARAM_USERNAME;
import static arasoftwares.in.PayRoll.Utils.Constants.USER_ID;

public class User {
    public User() {
    }

    //request
    @SerializedName(PARAM_USERNAME)
    @Expose
    private String username;
    @SerializedName(PARAM_PASSWORD)
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //response
    @SerializedName(PARAM_BRANCH_ID)
    @Expose
    private String banchId;

    public String getBanchId() {
        return banchId;
    }

    public void setBanchId(String banchId) {
        this.banchId = banchId;
    }

    @SerializedName(MSG)
    @Expose
    private String msg;
    @SerializedName(USER_ID)
    @Expose
    private String id;;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
