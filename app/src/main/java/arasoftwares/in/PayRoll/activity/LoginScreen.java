package arasoftwares.in.PayRoll.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.net.SocketException;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.Utils.Utils;
import arasoftwares.in.PayRoll.Utils.Constants;
import arasoftwares.in.PayRoll.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "Login_Screen : ";
    private EditText txtUserName, txtPassWord;
    private RelativeLayout container;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        initializeViews();
    }

    private void initializeViews() {
        if (!Utils.isNetworkAvailable(this)) {
            Utils.showToast(this, "Please check your network connection");
        }
        container = (RelativeLayout) findViewById(R.id.login_layout);
        txtUserName = (EditText) findViewById(R.id.username);
        txtPassWord = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF, Constants.MODE);
        String userid = sharedPreferences.getString(Constants.PREF_USERID_TAG, Constants.DEF_VAL_PREF);
        if (userid != "" && userid != null) {
            startActivity(new Intent(LoginScreen.this, MainScreen.class));
            finish();
        }
    }

    private void loginUser() {
        if (!Utils.isNetworkAvailable(this)) {
            Utils.showSnack(container, "Check your network connection");
            return;
        }
        if (!validate()) {
            return;
        }

        String userStr = txtUserName.getText().toString();
        String passStr = txtPassWord.getText().toString();
        final User user = new User(userStr, passStr);
        Log.e(TAG, "json model data : " + new Gson().toJson(user));
        final ProgressDialog dialog = Utils.showDialog(this, "Loading ...");
        (Utils.service().login(user)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                Log.e(TAG, "Response : " + new Gson().toJson(response.body()));
                String msg = response.body().getMsg();
                String userId = response.body().getId();
                String branchId = response.body().getBanchId();
                if (msg.equalsIgnoreCase(Constants.SUCCESS_MESSAGE)) {
                    Utils.showToast(getApplicationContext(), "Login Success");
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF, Constants.MODE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.PREF_USERID_TAG, userId);
                    editor.putString(Constants.PREF_BRANCHID_TAG, branchId);
                    editor.commit();
                    startActivity(new Intent(LoginScreen.this, MainScreen.class));
                    finish();
                } else {
                    Utils.showSnack(container, "Please Check Your Username Or Password");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e(TAG, "Failure Response : " + t.getMessage());
                    Utils.showSnack(container, "Socket time out .Please try again");
                    return;
                }
                Utils.showSnack(container, "Something went wrong Please Contact Support");
                Log.e(TAG, "Failure Response : " + t.getMessage());
            }
        });
    }

    private boolean validate() {
        String user = txtUserName.getText().toString();
        String pass = txtPassWord.getText().toString();
        if (TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)) {
            txtPassWord.setError("Password is required");
            txtUserName.setError("Username is required");
            return false;
        } else if (TextUtils.isEmpty(user)) {
            txtUserName.setError("Username is required");
            return false;
        } else if (TextUtils.isEmpty(pass)) {
            txtPassWord.setError("Password is required");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
