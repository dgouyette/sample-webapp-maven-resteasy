package com.cestpasdur.samples.restannuaire.resources;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jettison.json.JSONException;
import org.jboss.resteasy.test.BaseResourceTest;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactResourceTest extends BaseResourceTest {

    HttpClient client;

    @Before
    public void setUp() throws Exception {
        dispatcher.getRegistry().addPerRequestResource(ContactResource.class);
        client= new HttpClient();
    }

    @Test
    public void getContactXml() throws IOException, JSONException {
        GetMethod method = new GetMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_OK, status);
    }

    @Test
    //Type de contenu non pris en charge
    public void getContactText() throws IOException, JSONException {
        GetMethod method = new GetMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestHeader("Accept", MediaType.TEXT_PLAIN);
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, status);
    }



    @Test
    public void updateContact() {
        PutMethod putMethod=new PutMethod();



    }
}
