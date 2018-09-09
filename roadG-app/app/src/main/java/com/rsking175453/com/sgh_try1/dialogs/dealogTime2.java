package com.rsking175453.com.sgh_try1.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.registerActivity;


public class dealogTime2 extends DialogFragment {
    private static final String TAG = "dealogTime2";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("User name or Password didn't Match")
                .setPositiveButton("New User ?", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(),registerActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().findViewById(R.id.email_sign_in_button).callOnClick();
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}