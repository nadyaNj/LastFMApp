package com.mlab.trainings.lastfmapp.model;

import com.mlab.trainings.lastfmapp.networking.HttpAsyncTask;
import com.mlab.trainings.lastfmapp.networking.HttpGetRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artist
{

    private volatile onFullLoadedCallback callback;
	private String name;
	private int listeners;
	private String logoUrl;

    private String summary;
    private int startYear;
    private int playerCount;


	
	public Artist(JSONObject json)
	{
		try
		{
			this.name = json.getString("name");
			this.listeners = json.getInt("listeners");
			this.logoUrl = json.getJSONArray("image").getJSONObject(2).getString("#text");

            HttpGetRequest req = new HttpGetRequest("http://ws.audioscrobbler.com/2.0/");
            req.put("method", "artist.info");
            req.put("artist", name);
            req.put("api_key", "4a63a45f148bf80885675f7facce7841");
            req.put("format", "json");
            new HttpAsyncTask(new HttpAsyncTask.OnResponseCallback() {
                @Override
                public void onResponse(JSONObject response) {
                    loadFullInfo(response);
                    if(callback != null) {
                        callback.onFullInfoLoader(Artist.this);
                    }
                }

                @Override
                public void onResponse(JSONArray response) {

                }
            }).execute(req);
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

    private void loadFullInfo(JSONObject infoJson) {

        try {
            JSONObject content = infoJson.getJSONObject("artist");
            this.playerCount = content.getJSONObject("stats").getInt("playcount");
            this.summary = content.getJSONObject("bio").getString("summary");
            this.startYear =content.getJSONObject("bio").getInt("yearformed");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOnFullInfoCallback(onFullLoadedCallback callback) {
        this.callback = callback;
        if(summary != null) {
            callback.onFullInfoLoader(this);
        }

    }
	public String getName()
	{
		return name;
	}

	public int getListeners()
	{
		return listeners;
	}

	public String getLogoUrl()
	{
		return logoUrl;
	}

    public String getSummary() {
        return summary;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public static interface onFullLoadedCallback
    {
        public void onFullInfoLoader(Artist artist);
    }
	
}
