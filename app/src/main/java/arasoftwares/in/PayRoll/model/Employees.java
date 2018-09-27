package arasoftwares.in.PayRoll.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import arasoftwares.in.PayRoll.Utils.Constants;

public class Employees {
    @SerializedName(Constants.PARAM_BRANCH_ID)
    @Expose
    private String branchId;
    @SerializedName(Constants.USER_ID_ASSIGN)
    @Expose
    private String userid;
    @SerializedName(Constants.MSG)
    @Expose
    private String message;
    @SerializedName(Constants.EMPLOEE_LIST)
    @Expose
    private ArrayList<EmpList> empLists;

    public ArrayList<EmpList> getEmpLists() {
        return empLists;
    }

    public void setEmpLists(ArrayList<EmpList> empLists) {
        this.empLists = empLists;
    }

    public Employees(String branchId, String userid) {
        this.branchId = branchId;
        this.userid = userid;

    }

    public Employees() {

    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
