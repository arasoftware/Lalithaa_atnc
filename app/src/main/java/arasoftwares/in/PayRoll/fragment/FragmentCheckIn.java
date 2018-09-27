package arasoftwares.in.PayRoll.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.SocketException;

import arasoftwares.in.PayRoll.Utils.Utils;
import arasoftwares.in.PayRoll.Utils.Constants;
import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.model.BreakInOut;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCheckIn extends Fragment {
    private static final String TAG = "FragmentCheckin";
    private View view = null;
    private Switch btnBreakSwitch;
    private RelativeLayout containerS;
    private BreakInOut breakio;
    private TextView username;

    public FragmentCheckIn() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checkin_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(view, "PLease check your Network Connection");
        }
        username = (TextView) view.findViewById(R.id.username);
        breakio = new BreakInOut();
        containerS = (RelativeLayout) view.findViewById(R.id.fragment_checkin_layout);
        btnBreakSwitch = (Switch) view.findViewById(R.id.break_switch);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
        String userid = sharedPreferences.getString(Constants.USER_ID, Constants.DEF_VAL_PREF);
        if (userid != "" && userid != null) {
            breakio.setUserId(userid);
            username.setText("Hello User : " + userid);
        }
        String branchId = sharedPreferences.getString(Constants.PREF, Constants.DEF_VAL_PREF);
        if (branchId != "" && branchId != null) {
            breakio.setBranchId(branchId);
        }
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences(Constants.PREF_BREAKSTATUS, Constants.MODE);
        String breakId = sharedPreferences1.getString(Constants.BREAK_ID, Constants.DEF_VAL_PREF);
        if (breakId != "" && breakId != null) {
            btnBreakSwitch.setChecked(true);
        } else {
            btnBreakSwitch.setChecked(false);
        }
        btnBreakSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchChecked) {
                breakInOut(switchChecked);
            }
        });
    }

    private void breakInOut(boolean ischeked) {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(view, "PLease check your Network Connection");
            return;
        }
        if (!ischeked) {
            breakIn();
        } else {
            breakOut();
        }

    }

    private void breakIn() {
        breakio.setBreakType(Constants.BREAK_IN);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF_BREAKSTATUS, Constants.MODE);
        String breakId = sharedPreferences.getString(Constants.BREAK_ID, "");
        if (breakId != "" && breakId != null) {
            breakio.setBreakID(breakId);
        }
        final ProgressDialog dialog = Utils.showDialog(getContext(), "Loading...");
        dialog.show();
        (Utils.service().breakInOut(breakio)).enqueue(new Callback<BreakInOut>() {
            @Override
            public void onResponse(Call<BreakInOut> call, Response<BreakInOut> response) {
                dialog.dismiss();
                Log.e(TAG, "Response break out : " + new Gson().toJson(response.body()));
                String msg = response.body().getMsg();
                if (msg.equalsIgnoreCase(Constants.SUCCESS_MESSAGE)) {
                    SharedPreferences sharedPreferences1 = getContext().getSharedPreferences(Constants.PREF_BREAKSTATUS, Constants.MODE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.clear();
                    editor.commit();
                    Utils.showSnack(view, Constants.SUCCESS_MESSAGE);
                } else {
                    Utils.showSnack(view, "Break In fail");
                }
            }

            @Override
            public void onFailure(Call<BreakInOut> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e(TAG, "Break in Failure Response : " + t.getMessage());
                    Utils.showSnack(view, "Socket time out .Please try again");
                    return;
                }
                Log.e(TAG, "Response Failure : " + t.getMessage());
                Utils.showSnack(view, "Some thing Went Wrong Please Contact Support");
            }
        });
    }

    private void breakOut() {
        breakio.setBreakType(Constants.BREAK_OUT);
        breakio.setBreakID(Constants.DEF_BREAKID);
        final ProgressDialog dialog = Utils.showDialog(getContext(), "Loading...");
        dialog.show();
        (Utils.service().breakInOut(breakio)).enqueue(new Callback<BreakInOut>() {
            @Override
            public void onResponse(Call<BreakInOut> call, Response<BreakInOut> response) {
                dialog.dismiss();
                Log.e(TAG, "Response break out : " + new Gson().toJson(response.body()));
                String msg = response.body().getMsg();
                switch (msg) {
                    case Constants.SUCCESS_MESSAGE:
                        String breakId = response.body().getBreakID();
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF_BREAKSTATUS, Constants.MODE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.BREAK_ID, breakId);
                        editor.commit();
                        Utils.showSnack(view, "Break out succcess");
                        break;
                    default:
                        Utils.showSnack(view, "You are not checked in Today");
                        FragmentCheckIn fragmentCheckIn = new FragmentCheckIn();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        Utils.refreshFragment(fragmentCheckIn,fm);
                        break;
                }
            }

            @Override
            public void onFailure(Call<BreakInOut> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e(TAG, "Break out Failure Response : " + t.getMessage());
                    Utils.showSnack(view, "Socket time out . Please try again");
                    return;
                }
                Log.e(TAG, "Response Failure : " + t.getMessage());
                Utils.showSnack(view, "Some thing Went Wrong Please Contact Support");
            }
        });
    }

}
