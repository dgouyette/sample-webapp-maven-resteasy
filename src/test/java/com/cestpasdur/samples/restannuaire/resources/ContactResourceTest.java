package com.cestpasdur.samples.restannuaire.resources;

import com.cestpasdur.samples.restannuaire.domain.Contact;
import com.cestpasdur.samples.restannuaire.domain.Contacts;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.*;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;


/**
 * Test unitaire de la ressource REST contact
 */
public class ContactResourceTest {

	HttpClient client;

	private static final  String URL_BASE = "http://localhost:8888/restsample/rest";

	@Before
	public void setUp() throws Exception {
		client = new HttpClient();

	}

	/**
	 * CREATE **
	 */

	@Test
	public void addContact() throws IOException {
		PostMethod method = new PostMethod(URL_BASE + "/contact/");
		method.setRequestEntity(new StringRequestEntity(
				"<?xml version=\"1.0\"?>" + "<contact>"
						+ "<firstName>Joselyne</firstName>"
						+ "<lastName>MICHU</lastName>"
						+ "<mail>jmichu@gmail.com</mail>" + "</contact>",
				"application/xml", null));
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_CREATED, status);
		method.releaseConnection();
	}

	/**
	 * READ 
	 * @throws JAXBException **
	 */

	@Test
	public void recupereContactXml() throws IOException, JAXBException {
		GetMethod method = new GetMethod(URL_BASE + "/contact/0");
		method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
		int status = client.executeMethod(method);
		Assert.assertEquals(MediaType.APPLICATION_XML, method
				.getResponseHeader("content-type").getValue());
		Assert.assertEquals(HttpStatus.SC_OK, status);
		
		Contact contact = fromString(Contact.class, method.getResponseBodyAsString());
		Assert.assertEquals("Joselyne", contact.getFirstName());
		Assert.assertEquals("MICHU", contact.getLastName());
		Assert.assertEquals("jmichu@gmail.com", contact.getMail());
	}

	@Test
	public void recupereContactJSON() throws IOException {
		GetMethod method = new GetMethod(URL_BASE + "/contact/0");
		method.setRequestHeader("Accept", MediaType.APPLICATION_JSON);
		int status = client.executeMethod(method);
		Assert.assertEquals(MediaType.APPLICATION_JSON, method
				.getResponseHeader("content-type").getValue());
		Assert.assertEquals(HttpStatus.SC_OK, status);
	}

	@Test
	// Type de contenu non pris en charge
	public void getContactText() throws IOException, JSONException {
		GetMethod method = new GetMethod(URL_BASE + "/contact/1");
		method.setRequestHeader("Accept", MediaType.TEXT_PLAIN);
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, status);
	}

	@Test
	// Ne doit pas retourner de contact existant (erreur 404)
	public void recupereContactNotFound() throws IOException {
		GetMethod method = new GetMethod(URL_BASE + "/contact/3");
		method.setRequestHeader("Accept", MediaType.APPLICATION_XML);
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_NOT_FOUND, status);
	}

	@Test
	public void recupereTousContacts() throws IOException, JAXBException {
		GetMethod method = new GetMethod(URL_BASE + "/contact/all");
		method.setRequestHeader("Accept", MediaType.APPLICATION_XML);

        int status = client.executeMethod(method);
        Assert.assertEquals(HttpStatus.SC_OK, status);

        String result = method.getResponseBodyAsString();
        Assert.assertNotNull(result);



		JAXBContext ctx = JAXBContext.newInstance(Contacts.class);
		Contacts contacts = (Contacts) ctx.createUnmarshaller().unmarshal(new StringReader(result));
		Assert.assertEquals(1, contacts.getContacts().size());
	}

	/**
	 * UPDATE **
	 */

	@Test
	public void updateContact() throws IOException, JAXBException {
		PutMethod method = new PutMethod(URL_BASE + "/contact/0");
		method.setRequestEntity(new StringRequestEntity(
				"<?xml version=\"1.0\"?>" + "<contact>"
						+ "<firstName>Robert</firstName>"
						+ "<lastName>DUFOUR</lastName>"
                        + "</contact>",
				"application/xml", null));
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_OK, status);
        //Nous verifions ensuite que la donnee recue a bien ete mise a jour

        Contact contactUpdated = fromString(Contact.class, method.getResponseBodyAsString());
        Assert.assertEquals("Robert", contactUpdated.getFirstName());
        Assert.assertEquals("DUFOUR", contactUpdated.getLastName());

	}

	@Test
	public void updateContactInexistant() throws IOException {
		PutMethod method = new PutMethod(URL_BASE + "/contact/44");
		method.setRequestEntity(new StringRequestEntity(
				"<?xml version=\"1.0\"?>" + "<contact>"
						+ "<firstName>Jose</firstName>"
						+ "<lastName>BONPOIL</lastName>" + "</contact>",
				"application/xml", null));
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_NOT_FOUND, status);
		

	}

	/**
	 * DELETE **
	 */

	@Test
	// Supprime une ressource existante
	public void removeContact() throws IOException {
		DeleteMethod method = new DeleteMethod(URL_BASE + "/contact/0");
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_NO_CONTENT, status);
	}

	@Test
	// Supprimes une ressources inexistante
	public void removeContactInexistant() throws IOException {
		DeleteMethod method = new DeleteMethod(URL_BASE + "/contact/43");
		int status = client.executeMethod(method);
		Assert.assertEquals(HttpStatus.SC_NOT_FOUND, status);
	}
	
	/**
	 * Transforme un xml en une classe passee en paramètre
	 * @param <T>
	 * @param clazz
	 * @param input
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	private static <T> T fromString(Class<T> clazz, String input) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(clazz);
		return (T)ctx.createUnmarshaller().unmarshal(new StringReader(input));
	}

}
