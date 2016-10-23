package com.mlab.trainings.lastfmapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mlab.trainings.lastfmapp.HttpAsyncTask.OnResponseCallback;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void clickLast(View v)
	{
		HttpRequest req = new HttpRequest("http://ws.audioscrobbler.com/2.0/", RequestType.GET);
		req.put("method", "artist.search");
		req.put("artist", "floyd");
		req.put("api_key", "5d687d0eba9055708db581ca63367abd");
		req.put("format", "json");
		req.put("limit ", 10);
		HttpAsyncTask task = new HttpAsyncTask(new OnResponseCallback()
		{
			@Override
			public void onResponse(JSONObject response)
			{
				((TextView) findViewById(R.id.response)).setText(response.toString());
			}

			@Override
			public void onResponse(JSONArray response)
			{
				
			}
		});
		
		task.execute(req);
	}
}
