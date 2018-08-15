package com.rsking175453.com.sgh_try1.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.models.URLs;


public class changeLinkDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.changelink, container, false);

        final EditText t = (EditText) view.findViewById(R.id.link);
        t.setText(URLs.ROOT_URL);
        Button cancel =(Button) view.findViewById(R.id.cancelLink);
        Button go =(Button) view.findViewById(R.id.goLink);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URLs.changeRoot(t.getText().toString());
                dismiss();

            }
        });


        return view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}

