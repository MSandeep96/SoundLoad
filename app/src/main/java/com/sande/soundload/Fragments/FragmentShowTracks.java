package com.sande.soundload.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sande.soundload.Adapters.TracksAdapter;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShowTracks extends Fragment implements ShowTracksInterface{

    @BindView(R.id.fst_rv_tracks)
    RecyclerView recyclerView;
    private TracksAdapter mAdapter;
    private ShowTracksPresenterInterface showTrackPresenter;

    public FragmentShowTracks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTrackPresenter=new ShowTracksPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView= inflater.inflate(R.layout.frag_show_tracks, container, false);
        ButterKnife.bind(mView);
        mAdapter=new TracksAdapter(getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        showTrackPresenter.getTracks();
        return mView;
    }

    @Override
    public void gotTracks(List<Track> trackList) {
        mAdapter.addTracks(trackList);
    }
}
