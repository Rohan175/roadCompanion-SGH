package com.rsking175453.com.sgh_try1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class dialogTime3 extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String mNum = getArguments().getString("num");
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
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}