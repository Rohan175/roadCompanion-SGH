package com.rsking175453.com.sgh_try1.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rsking175453.com.sgh_try1.R;


public class dialogTime3 extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String mNum = getArguments().getString("msg");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mNum
        )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        Log.v("offline","hi");
                        //getActivity().finish();
                        //Intent i = new Intent(getActivity(),navigationMain.class);
                    }
                })
                .setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().findViewById(R.id.submit).callOnClick();
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}