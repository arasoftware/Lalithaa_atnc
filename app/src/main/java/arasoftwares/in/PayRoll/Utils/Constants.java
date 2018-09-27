package arasoftwares.in.PayRoll.Utils;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
    public static final String DEF_VAL_PREF = "";
    public static final String PARAM_BRANCH_ID = "branch_id";
    public static final String BASE_URL = "http://payroll.lalithaa.com/app/api/";
    public static final String PARAM_USERNAME = "userName";
    public static final String PARAM_PASSWORD = "passWord";
    public static final String END_POINT_LOGIN = "login";
    public static final String END_POINT_BREAKINOUT = "breakInOut";
    public static final String PARAM_USERID_BREAK_INOUT = "userId";
    public static final String PARAM_BREAK_TYPE = "breakType";
    public static final String BREAK_OUT = "OUT";
    public static final String BREAK_IN = "IN";
    public static final String BREAK_ID = "breakId";
    public static final String DEF_BREAKID = "0";
    public static final String SUCCESS_MESSAGE = "success";
    public static final String NOT_CHECKED_MESSAGE = "notchecked";
    public static final int MODE = MODE_PRIVATE;
    public static final String MSG = "msg";
    public static final String USER_ID = "id";
    public static final String AUTHORIZE_TOKEN = "token";
    public static final String PREF = "user";
    public static final String PREF_USERNAME_TAG = "username";
    public static final String PREF_USERID_TAG = "id";
    public static final String PREF_BRANCHID_TAG = "branch_id";
    public static final String PREF_TOKEN_TAG = "token";
    private static final String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/signature/";
    private static final String PIC_NAME = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    public static final String STORED_PATH = DIRECTORY + PIC_NAME + ".png";
    public static final String INTENT_DATE_EXTRA = "date";
    public static final String PREF_BREAKSTATUS = "break_status";
    public static final int AUTOCOMPLETE_THRESHOLD = 1;
    public static final String REMARKS = "remarks";
    public static final String DATE = "date";
    public static final String ASSIGN_PERSON = "assignto";
    public static final String BILL_NO = "billno";
    public static final String SIGNATURE_PATH = "signature";
    public static final String END_POINT_ASSIGNTO = "assignInsert";
    public static final String END_POINT_DELIVERY = "deliveryInsert";
    public static final String END_POINT_EMPLOYEELIST = "employeeList";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NO = "employee_no";
    public static final String EMPLOYEE_UNIQ_ID = "employee_uniq_id";
    public static final String EMPLOYEE_PASSWORD = "employee_password";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String USER_ID_ASSIGN = "userId";
    public static final String USER_ID_DEILVERY = "userId";
    public static final String EMPLOEE_LIST = "empList";
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final String EMPLIST_PREF = "emplistPref";
}