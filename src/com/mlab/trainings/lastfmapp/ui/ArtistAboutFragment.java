package com.mlab.trainings.lastfmapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mlab.trainings.lastfmapp.R;

/**
 * Created by nadya on 7/21/14.
 */
public class ArtistAboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*Bundle args = getArguments();
        View content = inflater.inflate(R.layout.about_artist, container, false);
        ((TextView)content.findViewById(R.id.text)).setText(args.getString("searchText"));*/
        return container;
    }
}