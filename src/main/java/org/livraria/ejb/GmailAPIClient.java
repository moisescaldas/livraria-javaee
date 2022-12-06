package org.livraria.ejb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class GmailAPIClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(GmailAPIClient.class);

	/**
	 * Application Name
	 */
	private static final String APPLICATION_NAME = "Projeto Livraria(Casa do Codigo)";

	/**
	 * Global instance of the JSON Factory
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	/**
	 * Diretory to store authorization tokens
	 */
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	private final Gmail client;

	public GmailAPIClient() throws GeneralSecurityException, IOException {
		LOGGER.info("Inicindo a criação do cliente Gmail");
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		this.client = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();
		LOGGER.info("Cliente do Gmail criado");
	}

	  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
		      throws IOException {
		    // Load client secrets.
		    InputStream in = GmailAPIClient.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		    }
		    GoogleClientSecrets clientSecrets =
		        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
		        .setAccessType("offline")
		        .build();
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    //returns an authorized Credential object.
		    return credential;
		  }


	public Gmail getClient() {
		return client;
	}

}
