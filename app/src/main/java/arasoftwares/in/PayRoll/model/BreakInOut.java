package arasoftwares.in.PayRoll.model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import arasoftwares.in.PayRoll.Utils.Constants;

public class BreakInOut {
    //request
    @SerializedName(Constants.PARAM_BRANCH_ID)
    @Expose
    private String branchId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    @SerializedName(Constants.PARAM_USERID_BREAK_INOUT)
    @Expose
    private String userId;
    @SerializedName(Constants.BREAK_ID)
    @Expose
    private String breakID;
    @SerializedName(Constants.PARAM_BREAK_TYPE)
    @Expose
    private String breakType;

    public String getUserId() {
        return userId;
    }

    public BreakInOut() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBreakID() {
        return breakID;
    }

    public void setBreakID(String breakID) {
        this.breakID = breakID;
    }

    public String getBreakType() {
        return breakType;
    }

    public void setBreakType(String breakType) {
        this.breakType = breakType;
    }

    public BreakInOut(String userId, String breakID, String breakType) {
        this.userId = userId;

        this.breakID = breakID;
        this.breakType = breakType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    //response
    @SerializedName(Constants.MSG)
    @Expose
    private String msg;

}
