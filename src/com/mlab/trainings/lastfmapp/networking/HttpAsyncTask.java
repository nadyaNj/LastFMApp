package com.mlab.trainings.lastfmapp.networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class HttpAsyncTask extends AsyncTask<HttpRequest, Integer, String>
{
	private OnResponseCallback callback;

	public HttpAsyncTask(OnResponseCallback callback)
	{
		this.callback = callback;
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected void onPostExecute(String result)
	{
		try
		{
			if (callback != null)
			{
				if (result.startsWith("["))
					callback.onResponse(new JSONArray(result));
				else if (result.startsWith("{"))
					callback.onResponse(new JSONObject(result));
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		// try
		// {
		// if(callback instanceof OnResponseObjectCallback)
		// ((OnResponseObjectCallback)callback).onResponse(new
		// JSONObject(result));
		// else if(callback instanceof OnResponseArrayCallback)
		// ((OnResponseArrayCallback)callback).onResponse(new
		// JSONArray(result));
		// } catch (JSONException e)
		// {
		// e.printStackTrace();
		// }
	}

	@Override
	protected String doInBackground(HttpRequest... params)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		try
		{
			HttpUriRequest request;
			String url = params[0].buildUrl();
            url = url.replaceAll(" ","%20");
			switch (params[0].getType())
			{
			case DELETE:
				request = new HttpDelete(url);
				break;

			case POST:
				request = new HttpPost(url);
				String body = ((HttpPostRequest) params[0]).getBodyString();
				((HttpPost) request).setEntity(new StringEntity(body));
				break;

			case PUT:
				request = new HttpPut(url);
				break;

			case GET:
			default:
				request = new HttpGet(url);
				break;
			}
			response = httpclient.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK)
			{
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				String responseString = out.toString();
                out.close();

                return responseString;
				// ..more logic
			} else
			{
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static interface OnResponseCallback
	{
		public void onResponse(JSONObject response);

		public void onResponse(JSONArray response);
	}

	// public static interface OnResponseObjectCallback extends
	// OnResponseCallback
	// {
	// public void onResponse(JSONObject response);
	// }
	//
	// public static interface OnResponseArrayCallback extends
	// OnResponseCallback
	// {
	// public void onResponse(JSONArray response);
	//
	// }
}
