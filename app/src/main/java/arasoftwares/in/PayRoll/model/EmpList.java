package arasoftwares.in.PayRoll.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import arasoftwares.in.PayRoll.Utils.Constants;

public class EmpList implements Parcelable {
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose

    private int employeeId;
    @SerializedName(Constants.EMPLOYEE_NO)
    @Expose
    private String employeeNo;
    @SerializedName(Constants.EMPLOYEE_UNIQ_ID)
    @Expose
    private String employeeUniqId;
    @SerializedName(Constants.EMPLOYEE_PASSWORD)
    @Expose
    private String employeePassword;


    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeUniqId() {
        return employeeUniqId;
    }

    public void setEmployeeUniqId(String employeeUniqId) {
        this.employeeUniqId = employeeUniqId;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @SerializedName(Constants.EMPLOYEE_NAME)
    @Expose

    private String employeeName;

    private int mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<EmpList> CREATOR
            = new Parcelable.Creator<EmpList>() {
        public EmpList createFromParcel(Parcel in) {
            return new EmpList(in);
        }

        public EmpList[] newArray(int size) {
            return new EmpList[size];
        }
    };

    private EmpList(Parcel in) {
        mData = in.readInt();
    }
}
