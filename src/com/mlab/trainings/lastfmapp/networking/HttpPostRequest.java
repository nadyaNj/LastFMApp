package com.mlab.trainings.lastfmapp.networking;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mlab.trainings.lastfmapp.RequestType;

public class HttpPostRequest extends HttpRequest
{
	private Map<String, String> bodyParams;

	public HttpPostRequest(String url)
	{
		super(url);
		bodyParams = new HashMap<String, String>();
	}
	
	public void putBodyParam(String key, String value)
	{
		bodyParams.put(key, value);
	}
	
	public void putBodyParam(String key, int value)
	{
		putBodyParam(key, Integer.toString(value));
	}
	
	public void putBodyParam(String key, double value)
	{
		putBodyParam(key, Double.toString(value));
	}
	
	public void putBodyParam(String key, boolean value)
	{
		putBodyParam(key, Boolean.toString(value));
	}
	
	public void putAllBodyParams(Map<String, String> params)
	{
		this.bodyParams.putAll(params);
	}
	
	public JSONObject getBody()
	{
		JSONObject bodyJson = new JSONObject();
		for(String key : bodyParams.keySet())
		{
			try
			{
				bodyJson.put(key, bodyParams.get(key));
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		return bodyJson;
	}
	
	public String getBodyString()
	{
		return getBody().toString();
	}

	@Override
	public RequestType getType()
	{
		return RequestType.POST;
	}
}
