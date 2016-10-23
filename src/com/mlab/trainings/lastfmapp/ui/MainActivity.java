package com.mlab.trainings.lastfmapp.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import com.mlab.trainings.lastfmapp.ui.adapters.FullArtistAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.mlab.trainings.lastfmapp.R;
import com.mlab.trainings.lastfmapp.model.Artist;
import com.mlab.trainings.lastfmapp.networking.HttpAsyncTask;
import com.mlab.trainings.lastfmapp.networking.HttpAsyncTask.OnResponseCallback;
import com.mlab.trainings.lastfmapp.networking.HttpGetRequest;
import com.mlab.trainings.lastfmapp.ui.adapters.ArtistAdapter;
import com.mlab.trainings.lastfmapp.ui.adapters.ArtistAdapter.OnNewItemsLoadCallback;

public class MainActivity extends FragmentActivity
{

	private EditText searchEditText;
	private ArtistAdapter adapter;
    private ArtistAdapter.OnNewItemsLoadCallback itemsLoadCallback;
    private FullArtistAdapter fullArtistAdapter;



    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchEditText = (EditText) findViewById(R.id.search_editText);
		ListView list = (ListView) findViewById(R.id.artists_list);
		adapter = new ArtistAdapter(this);
        fullArtistAdapter = new FullArtistAdapter(this);
		list.setAdapter(adapter);
        adapter.setOnNew(new ArtistAdapter.OnNewItemsLoadCallback() {

            @Override
            public void onNewItems(int offset) {
                String searchText = searchEditText.getText().toString();
                HttpGetRequest req = new HttpGetRequest("http://ws.audioscrobbler.com/2.0/");
                req.put("method", "artist.search");
                req.put("artist", searchText);
                req.put("api_key", "4a63a45f148bf80885675f7facce7841");
                req.put("format", "json");
                req.put("limit",10);
                req.put("page",offset/10);

                HttpAsyncTask task = new HttpAsyncTask(new OnResponseCallback()

                {

                    @Override
                    public void onResponse(JSONArray response)
                    {
                    }

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONArray artistsJson = response.getJSONObject("results").getJSONObject("artistmatches").getJSONArray("artist");
                            List<Artist> artists = new ArrayList<Artist>(artistsJson.length());
                            for(int i = 0; i < artistsJson.length(); i++)
                            {
                                artists.add(new Artist(artistsJson.getJSONObject(i)));
                            }
                            adapter.addArtists(artists);
                            fullArtistAdapter.addArtists(artists);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });


                task.execute(req);
            }

        });
	}
	
	public void clickSearch(View v)	{
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment redFragment = new ArtistAboutFragment();
        Bundle params = new Bundle();
        params.putString("searchText",searchEditText.getText().toString());
        redFragment.setArguments(params);
        adapter.refresh();

        transaction.add(R.id.container, new SearchFragment());
        transaction.addToBackStack("search");

        transaction.commit();

	}
    public void onItemClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment redFragment = new ArtistAboutFragment();
        Bundle params = new Bundle();
        params.putString("searchText",searchEditText.getText().toString());
        redFragment.setArguments(params);
        fullArtistAdapter.refresh();

        transaction.add(R.id.container, new ArtistAboutFragment());
        transaction.addToBackStack("search");

        transaction.commit();
    }
	
}
