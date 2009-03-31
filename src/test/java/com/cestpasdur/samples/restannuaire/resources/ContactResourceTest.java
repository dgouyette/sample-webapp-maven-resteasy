package com.cestpasdur.samples.restannuaire.resources;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXB;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jettison.json.JSONException;
import org.jboss.resteasy.test.BaseResourceTest;
import org.jboss.resteasy.test.TestPortProvider;
import org.jboss.resteasy.client.core.Marshaller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import com.cestpasdur.samples.restannuaire.domain.Contact;

public class ContactResourceTest extends BaseResourceTest {

    HttpClient client;

    HttpMethod method;

    @Before
    public void setUp() throws Exception {
        dispatcher.getRegistry().addPerRequestResource(ContactResource.class);
        client = new HttpClient();
    }

    @Test
    public void getContactXml() throws IOException, JSONException, JAXBException {
        method = new GetMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_OK, status);
        String output=method.getResponseBodyAsString();


    }

    @Test
    //Type de contenu non pris en charge
    public void getContactText() throws IOException, JSONException {
        method = new GetMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestHeader("Accept", MediaType.TEXT_PLAIN);
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, status);
    }


    @Test
    public void updateContact() throws IOException {
        method = new PutMethod(TestPortProvider.generateURL("/contact/"));

        int status = client.executeMethod(method);
        Assert.assertEquals(204, status);
        method.releaseConnection();


    }

    @After
    public void tearDown() {
        method.releaseConnection();
    }
}
