package com.cestpasdur.samples.restannuaire.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ContactResourceTest extends TestCase {

	public void testRecupereContactJson() throws IOException {
		URL postUrl = new URL("http://localhost:8080/restsample/rest/contact/1");
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
		//connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("GET");
		//connection.setRequestProperty("Content-Type", "application/xml");
		
		OutputStream os = connection.getOutputStream();
	      System.out.println(connection.getContent());
	    Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, connection.getResponseCode());
	}
}
