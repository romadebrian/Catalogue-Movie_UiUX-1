package com.ghozay19.cataloguemovieui_ux.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghozay19.cataloguemovieui_ux.Movieitem;
import com.ghozay19.cataloguemovieui_ux.R;
import com.ghozay19.cataloguemovieui_ux.adapter.NowUpAdapter;
import com.ghozay19.cataloguemovieui_ux.loader.NowPlayAsyncTaskLoader;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movieitem>> {

    NowUpAdapter adapter;
    Context context;
    RecyclerView mRecyclerView;
    private ArrayList<Movieitem> nowPlayingData;


    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_now_playing);

        adapter = new NowUpAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;

    }


    @Override
    public Loader<ArrayList<Movieitem>> onCreateLoader(int id, Bundle args) {
        return new NowPlayAsyncTaskLoader(getContext(), nowPlayingData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movieitem>> loader, ArrayList<Movieitem> nowPlayingData) {
        adapter.setData(nowPlayingData);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movieitem>> loader) {
        adapter.setData(null);

    }
}
