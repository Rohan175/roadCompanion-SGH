package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rsking175453.com.sgh_try1.R;

/**
 * Created by shail on 24/3/18.
 */

public class contactsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contactdetials, container, false);
    }
}
