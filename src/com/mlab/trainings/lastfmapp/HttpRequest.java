package com.mlab.trainings.lastfmapp;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest
{
	private String url;
	private Map<String, String> params;
	private RequestType type;
	
	public HttpRequest(String url, RequestType type)
	{
		this.url = url;
		this.type = type;
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
	
	public RequestType getType()
	{
		return type;
	}
	
	
	
	
}
