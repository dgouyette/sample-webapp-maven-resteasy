package com.cestpasdur.samples.restannuaire.resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXB;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
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


    @Before
    public void setUp() throws Exception {
        dispatcher.getRegistry().addPerRequestResource(ContactResource.class);
        client = new HttpClient();
    }

    /**
     * CREATE **
     */

    @Test
    public void addContact() throws IOException {
        PostMethod method = new PostMethod(TestPortProvider.generateURL("/contact/"));
        method.setRequestEntity(new StringRequestEntity(
                "<?xml version=\"1.0\"?>" +
                        "<contact>" +
                        "<firstName>Jolyne</firstName>" +
                        "<lastName>MICHU</lastName>" +
                        "</contact>",
                "application/xml", null));
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_CREATED, status);
        method.releaseConnection();
    }


    /**
     * READ **
     */

    @Test
    public void recupereContactXml() throws IOException {
        GetMethod method = new GetMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
        int status = client.executeMethod(method);
        Assert.assertEquals(MediaType.APPLICATION_XML, method.getResponseHeader("content-type").getValue());
        Assert.assertEquals(HttpStatus.SC_OK, status);
    }

    @Test
    public void recupereContactJSON() throws IOException {
        GetMethod method = new GetMethod(TestPortProvider.generateURL("/contact/2"));
        method.setRequestHeader("Accept", MediaType.APPLICATION_JSON);
        int status = client.executeMethod(method);
        Assert.assertEquals(MediaType.APPLICATION_JSON, method.getResponseHeader("content-type").getValue());
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
    //Ne doit pas retourner de contact existant (erreur 404)
    public void recupereContactNotFound() throws IOException {
        GetMethod method = new GetMethod(TestPortProvider.generateURL("/contact/3"));
        method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, status);

    }




















    


    /**
     * UPDATE **
     */

    @Test
    public void updateContact() throws IOException {
        PutMethod method = new PutMethod(TestPortProvider.generateURL("/contact/1"));
        method.setRequestEntity(new StringRequestEntity(
                "<?xml version=\"1.0\"?>" +
                        "<contact>" +
                        "<firstName>Robert</firstName>" +
                        "<lastName>DUFOUR</lastName>" +
                        "</contact>",
                "application/xml", null));
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_OK, status);
    }


    /**
     * DELETE **
     */
    @Test
    //Supprime une ressource existante
    public void removeContact() throws IOException {
        DeleteMethod method = new DeleteMethod((TestPortProvider.generateURL("/contact/1")));
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_NO_CONTENT, status);
    }

    @Test
    //Supprimes une ressources inexistante
    public void removeContactInexistant() throws IOException {
        DeleteMethod method = new DeleteMethod((TestPortProvider.generateURL("/contact/43")));
        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND, status);
    }

}
