package com.sande.soundload.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sande.soundload.R;
import com.sande.soundload.mainActivity_MVP.MainActivity;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_MainActivity extends Fragment {

    int layout_Used;
    static String layout_Identifier="Layout_Identifier";

    public Fragment_MainActivity() {
        // Required empty public constructor
    }

    public static Fragment_MainActivity getFragment(int option){
        Bundle bundle=new Bundle();
        bundle.putInt(layout_Identifier,option);
        Fragment_MainActivity mFrag=new Fragment_MainActivity();
        mFrag.setArguments(bundle);
        return mFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle=getArguments();
        layout_Used=mBundle.getInt(layout_Identifier);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView;
        if(layout_Used== MainActivity.NO_INTERNET){
            mView=inflater.inflate(R.layout.frag_no_internet, container, false);
        }else if(layout_Used==MainActivity.NOT_LOGGED_IN){
            mView=inflater.inflate(R.layout.frag_not_logged_in,container,false);
        }else{
            mView=inflater.inflate(R.layout.frag_show_tracks,container,false);
        }
        ButterKnife.bind(mView);

        return mView;
    }

}
