package com.mlab.trainings.lastfmapp.networking;

import com.mlab.trainings.lastfmapp.RequestType;

public class HttpGetRequest extends HttpRequest
{

	public HttpGetRequest(String url)
	{
		super(url);
	}

	@Override
	public RequestType getType()
	{
		return RequestType.GET;
	}
}
