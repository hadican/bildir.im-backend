package com.hadican.bildirim;

import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

import org.apache.http.client.HttpClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.hadican.bildirim.configuration.BildirimConfiguration;
import com.hadican.bildirim.health.BildirimHealthCheck;
import com.hadican.bildirim.manager.UserManager;
import com.hadican.bildirim.resource.LoginResource;
import com.hadican.bildirim.resource.NotificationResource;
import com.hadican.bildirim.resource.RegistrationResource;
import com.mongodb.MongoClient;

public class BildirimApplication extends Application<BildirimConfiguration> {

	public static void main(String[] args) throws Exception {
		new BildirimApplication().run(args);
	}

	@Override
	public void run(BildirimConfiguration configuration, Environment environment) throws Exception {

		// create data store
		final Morphia morphia = new Morphia();
		morphia.mapPackage("com.hadican.bildirim.model");
		final Datastore userDatastore = morphia.createDatastore(new MongoClient(), "users");

		// create user manager to handle user CRUD actions
		final UserManager userManager = new UserManager(userDatastore);

		// HTTP client with default Dropwizard configurations
		final HttpClient httpClient = new HttpClientBuilder(environment).build("httpClient");

		// notification sender resource
		final NotificationResource notificationResource = new NotificationResource(httpClient, configuration.getApiKey());
		environment.jersey().register(notificationResource);

		// user registration resource
		final RegistrationResource registrationResource = new RegistrationResource(userManager);
		environment.jersey().register(registrationResource);

		// user login resource
		final LoginResource loginResource = new LoginResource(userManager);
		environment.jersey().register(loginResource);

		// heath check
		final BildirimHealthCheck healthCheck = new BildirimHealthCheck();
		environment.healthChecks().register("keepUp", healthCheck);
	}

}
