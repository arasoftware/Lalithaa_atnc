package arasoftwares.in.PayRoll.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.Utils.Utils;
import arasoftwares.in.PayRoll.Utils.Constants;
import arasoftwares.in.PayRoll.adapter.EmployeeListsAdapter;
import arasoftwares.in.PayRoll.model.AssignPerson;
import arasoftwares.in.PayRoll.model.EmpList;
import arasoftwares.in.PayRoll.model.Employees;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAssigningWork extends Fragment {
    private static final String TAG = "FragmentAssigningWork";
    private AutoCompleteTextView txtAutoComplete;
    private Button btnAssignWork;
    private AppCompatEditText txtRemarks;
    private TextView lblDatePicker;
    private View view = null;
    private String employeeName = "";
    private AssignPerson assignPerson = new AssignPerson();

    public FragmentAssigningWork() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.assigningwork_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(view, "Please check your network connection");
        }
        lblDatePicker = (TextView) view.findViewById(R.id.datePicker);
        txtAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.simpleSpinner);
        btnAssignWork = (Button) view.findViewById(R.id.buttonassign);
        txtRemarks = (AppCompatEditText) view.findViewById(R.id.remarks_forAssign);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
        String jsonEmpList = sharedPreferences.getString(Constants.EMPLIST_PREF, Constants.DEF_VAL_PREF);
        if (jsonEmpList != "" && jsonEmpList != null) {
            Type type = new TypeToken<ArrayList<EmpList>>() {
            }.getType();
            ArrayList<EmpList> empLists = new Gson().fromJson(jsonEmpList, type);
            final EmployeeListsAdapter adapter = new EmployeeListsAdapter(getContext(), R.layout.autocomplte_layout, empLists);
            txtAutoComplete.setAdapter(adapter);
            txtAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    employeeName = adapter.getItem(i).getEmployeeName();
                    int employeeId = adapter.getItem(i).getEmployeeId();
                    Log.e(TAG, "name and id : " + employeeName + " " + employeeId);
                    assignPerson.setAssignPerson(String.valueOf(employeeId));
                }
            });
        } else {
            initializeEmployeeList();
        }
        btnAssignWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assigningUser();
            }
        });
        lblDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String format = (year) + "-" + (month + 1) + "-" + (day);
                        lblDatePicker.setText(format);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void initializeEmployeeList() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(view, "Please check your network connection");
            return;
        }
        final Employees employees = new Employees();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
        String userID = sharedPreferences.getString(Constants.PREF_USERID_TAG, Constants.DEF_VAL_PREF);
        if (userID != "" && userID != null) {
            employees.setUserid(userID);
            assignPerson.setUserid(userID);
            Log.e(TAG, "userid : " + userID);
        }
        String branchID = sharedPreferences.getString(Constants.PREF_BRANCHID_TAG, Constants.DEF_VAL_PREF);
        if (branchID != "" && branchID != null) {
            employees.setBranchId(branchID);
        }
        final ProgressDialog dialog = Utils.showDialog(getContext(), "Loading...");
        dialog.show();
        (Utils.service()).getEmployee(employees).enqueue(new Callback<Employees>() {
            @Override
            public void onResponse(Call<Employees> call, Response<Employees> response) {
                dialog.dismiss();
                Log.e(TAG, "response : " + new Gson().toJson(response.body()));
                if (response.body().getMessage().equalsIgnoreCase(Constants.SUCCESS_MESSAGE)) {
                    ArrayList<EmpList> empLists = response.body().getEmpLists();
                    SharedPreferences employeeList = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
                    SharedPreferences.Editor editor = employeeList.edit();
                    editor.putString(Constants.EMPLIST_PREF, new Gson().toJson(empLists));
                    editor.apply();
                    final EmployeeListsAdapter adapter = new EmployeeListsAdapter(getContext(), R.layout.autocomplte_layout, empLists);
                    txtAutoComplete.setAdapter(adapter);

                    txtAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            employeeName = adapter.getItem(i).getEmployeeName();
                            int employeeId = adapter.getItem(i).getEmployeeId();
                            Log.e(TAG, "name and id : " + employeeName + " " + employeeId);
                            assignPerson.setAssignPerson(String.valueOf(employeeId));
                        }
                    });
                } else {
                    Utils.showSnack(view, "Can't get employee list Contact Support");
                }
            }

            @Override
            public void onFailure(Call<Employees> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e(TAG, "Failure Response : " + t.getMessage());
                    Utils.showSnack(view, "Socket time out .Please try again");
                    return;
                }
                Log.e(TAG, "Error : " + t.toString());
                Utils.showSnack(view, t.toString());
            }
        });
    }

    public boolean validate() {
        String remarks = txtRemarks.getText().toString();
        String autoComlete = txtAutoComplete.getText().toString().trim();
        String lblDate = lblDatePicker.getText().toString();
        if (remarks.isEmpty()) {
            txtRemarks.setError("Remarks is required");
            Utils.showSnack(view, "Please enter remarks");
            return false;
        } else {
            txtRemarks.setError(null);
        }

        if (lblDate.equalsIgnoreCase("Date")) {
            Utils.showSnack(view, "Date is required");
            return false;
        }
        if (autoComlete.isEmpty()) {
            txtAutoComplete.setError("Person Name is required");
            Utils.showSnack(view, "Please select valid Person name");
            return false;
        } else {
            txtAutoComplete.setError(null);
        }
        return true;
    }

    private void assigningUser() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(view, "Please check your network connection");
            return;
        }

        if (!validate()) {
            return;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
        String branchID = sharedPreferences.getString(Constants.PREF_BRANCHID_TAG, Constants.DEF_VAL_PREF);
        if (branchID != "" && branchID != null) {
            assignPerson.setBranchId(branchID);
        }
        String remarks = txtRemarks.getText().toString();
        String dateLbl = lblDatePicker.getText().toString();
        assignPerson.setDate(dateLbl);
        assignPerson.setRemarks(remarks);
        Log.e(TAG, "Model data pojo : " + new Gson().toJson(assignPerson));
        final ProgressDialog dialog = Utils.showDialog(getContext(), "Loading...");
        dialog.show();
        (Utils.service()).assignPerson(assignPerson).enqueue(new Callback<AssignPerson>() {
            @Override
            public void onResponse(Call<AssignPerson> call, Response<AssignPerson> response) {
                dialog.dismiss();
                Log.e(TAG, "response : " + new Gson().toJson(response.body()));
                String message = response.body().getMessage();
                if (message.equalsIgnoreCase(Constants.SUCCESS_MESSAGE)) {
                    Utils.showSnack(view, "Task is assigned to : " + employeeName);
                    FragmentAssigningWork fragmentAssigningWork = new FragmentAssigningWork();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Utils.refreshFragment(fragmentAssigningWork, fragmentManager);
                } else {
                    Utils.showSnack(view, "Assigning Failed");
                }
            }

            @Override
            public void onFailure(Call<AssignPerson> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e(TAG, "Failure Response : " + t.getMessage());
                    Utils.showSnack(view, "Socket time out .Please try again");
                    return;
                }
                Log.e(TAG, "error" + t.toString());
            }
        });
    }
}
