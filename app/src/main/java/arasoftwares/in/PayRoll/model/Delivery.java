package arasoftwares.in.PayRoll.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import arasoftwares.in.PayRoll.Utils.Constants;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public class Delivery {
    @SerializedName(Constants.BILL_NO)
    @Expose
    private String billNo;
    @SerializedName(Constants.SIGNATURE_PATH)
    @Expose
    private String signaturePath;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public Delivery() {

    }

    public Delivery(String billNo, String signaturePath) {

        this.billNo = billNo;
        this.signaturePath = signaturePath;
    }

    @SerializedName(Constants.MSG)
    @Expose
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
