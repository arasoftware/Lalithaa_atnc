package arasoftwares.in.PayRoll.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketException;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.Utils.Constants;
import arasoftwares.in.PayRoll.Utils.Utils;
import arasoftwares.in.PayRoll.listeners.CustomGestureListener;
import arasoftwares.in.PayRoll.model.Delivery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment {
    private GestureOverlayView gestureOverlayView;
    private String picturePath = "";
    private Button btnClear;
    private Button btnSubmit;
    private Button btnSave;
    private EditText billNo;
    private View rootView;

    public DeliveryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delivery, container, false);
        gestureOverlayView = (GestureOverlayView) rootView.findViewById(R.id.containerOverlay);
        btnClear = (Button) rootView.findViewById(R.id.clear);
        billNo = (EditText) rootView.findViewById(R.id.billNoEd);
        btnSave = (Button) rootView.findViewById(R.id.save);
        btnSubmit = (Button) rootView.findViewById(R.id.submit);
        gestureOverlayView.addOnGesturePerformedListener(new CustomGestureListener());
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.clear(false);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndSaveSignature();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSignature();
            }
        });
        return rootView;
    }

    private void submitSignature() {
        if (!Utils.isNetworkAvailable(getContext())) {
            Utils.showSnack(rootView, "Please check your network connection");
            return;
        }
        if (!validate()) {
            return;
        }
        String billNoStr = billNo.getText().toString();
        Delivery delivery = new Delivery();
        delivery.setBillNo(billNoStr);
        delivery.setSignaturePath(picturePath);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Constants.MODE);
        String userID = sharedPreferences.getString(Constants.PREF_USERID_TAG, Constants.DEF_VAL_PREF);
        if (userID != "" && userID != null) {
            Log.e("userid  data : ", userID);
            delivery.setUserid(userID);
        }
        String branchID = sharedPreferences.getString(Constants.PREF_BRANCHID_TAG, Constants.DEF_VAL_PREF);
        if (branchID != "" && branchID != null) {
            delivery.setBranchId(branchID);
        }
        final ProgressDialog dialog = Utils.showDialog(getContext(), "processing...");
        dialog.show();
        (Utils.service()).deliverySignature(delivery).enqueue(new Callback<Delivery>() {
            @Override
            public void onResponse(Call<Delivery> call, Response<Delivery> response) {
                dialog.dismiss();
                Log.e("reponse : ", new Gson().toJson(response.body()));
                String msg = response.body().getMessage();
                if (msg.equalsIgnoreCase(Constants.SUCCESS_MESSAGE)) {
                    Utils.showSnack(rootView, "Signature submitted successfully");
                    DeliveryFragment deliveryFragment = new DeliveryFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Utils.refreshFragment(deliveryFragment, fragmentManager);
                } else {
                    Utils.showSnack(rootView, "Fail data was not Submitted");
                }
            }

            @Override
            public void onFailure(Call<Delivery> call, Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketException) {
                    Log.e("Failure Response : ", t.getMessage());
                    Utils.showSnack(rootView, "Socket time out .Please try again");
                    return;
                }
                Log.e("error", t.toString());
            }
        });
    }

    private boolean validate() {

        String billNoStr = billNo.getText().toString();
        if (billNoStr.isEmpty()) {
            Utils.showSnack(rootView, "Bill no is required");
            billNo.setError("Bill no required");
            return false;
        }
        if (picturePath.equalsIgnoreCase("")) {
            Utils.showSnack(rootView, "Please enter and save Signature above");
            return false;
        }
        return true;
    }

    private void checkPermissionAndSaveSignature() {
        try {
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {
                saveSignature();
            }
        } catch (Exception e) {
            Log.d("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveSignature() {
        try {
            gestureOverlayView.setDrawingCacheEnabled(true);
            Bitmap drawingCacheBitmap = gestureOverlayView.getDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(drawingCacheBitmap);/*
            String filePath = Environment.getExternalStorageDirectory().toString();
            filePath += File.separator;
            filePath += "sign.png";*/
            File file = new File(Constants.STORED_PATH);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String encoded = Utils.encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
            picturePath = encoded;
            fileOutputStream.flush();
            fileOutputStream.close();
            Utils.showSnack(rootView, "Signature captured");
        } catch (Exception e) {
            Log.v("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveSignature();
            } else {
                Utils.showSnack(rootView, "Storage Permission required to use this application");
            }
        }
    }
}
