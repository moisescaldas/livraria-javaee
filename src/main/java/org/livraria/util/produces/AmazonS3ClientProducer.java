package org.livraria.util.produces;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AmazonS3ClientProducer {

	private static final String ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE";

	private static final String SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

	@Produces
	@RequestScoped
	public AmazonS3 s3cliente() {
		AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonS3 cliente = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(creds))
				.withClientConfiguration(new ClientConfiguration())
				.withEndpointConfiguration(new EndpointConfiguration("http://localhost:9444", Regions.US_EAST_1.toString()))
				.withPathStyleAccessEnabled(true)
				.build();
		
		return cliente;
	}
}
