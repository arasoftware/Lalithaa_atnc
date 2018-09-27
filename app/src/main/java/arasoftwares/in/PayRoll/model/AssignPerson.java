package arasoftwares.in.PayRoll.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import arasoftwares.in.PayRoll.Utils.Constants;

import static arasoftwares.in.PayRoll.Utils.Constants.PARAM_BRANCH_ID;

public class AssignPerson {

    @SerializedName(Constants.REMARKS)
    @Expose
    private String remarks;
    @SerializedName(Constants.ASSIGN_PERSON)
    @Expose
    private String assignPerson;
    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(String assignPerson) {
        this.assignPerson = assignPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AssignPerson() {

    }
    @SerializedName(Constants.PARAM_BRANCH_ID)
    @Expose
    private String branchId;
    @SerializedName(Constants.USER_ID_ASSIGN)
    @Expose
    private String userid;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public AssignPerson(String remarks, String assignPerson, String date) {

        this.remarks = remarks;
        this.assignPerson = assignPerson;
        this.date = date;
    }

    @SerializedName(Constants.MSG)
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
