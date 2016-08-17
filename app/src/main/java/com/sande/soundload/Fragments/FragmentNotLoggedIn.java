package com.sande.soundload.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sande.soundload.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotLoggedIn extends Fragment {


    public FragmentNotLoggedIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_not_logged_in, container, false);
    }

}
