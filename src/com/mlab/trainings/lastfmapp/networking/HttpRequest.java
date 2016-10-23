package com.mlab.trainings.lastfmapp.networking;

import java.util.HashMap;
import java.util.Map;

import com.mlab.trainings.lastfmapp.RequestType;

public abstract class HttpRequest
{
	private String url;
	private Map<String, String> params;
	
	public HttpRequest(String url)
	{
		this.url = url;
		params = new HashMap<String, String>();
	}
	
	public void put(String key, String value)
	{
		params.put(key, value);
	}
	
	public void put(String key, int value)
	{
		put(key, Integer.toString(value));
	}
	
	public void put(String key, double value)
	{
		put(key, Double.toString(value));
	}
	
	public void put(String key, boolean value)
	{
		put(key, Boolean.toString(value));
	}
	
	public void putAll(Map<String, String> params)
	{
		this.params.putAll(params);
	}
	
	public String buildUrl()
	{
		StringBuilder builder = new StringBuilder(url + "?");
		for(String key : params.keySet())
			builder.append(key + "=" + params.get(key) + "&");
		
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
	public abstract RequestType getType();
}
