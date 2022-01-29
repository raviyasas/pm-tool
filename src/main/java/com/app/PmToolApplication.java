package com.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@SpringBootApplication
public class PmToolApplication {

	@PostConstruct
	public void initialize() throws Exception {
		try {
			FileInputStream serviceAccount = new FileInputStream("./firebase-key.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://firebase-url.firebaseio.com").build();
			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			//logger.error("error connecting firebase : {} ", e.getMessage());
		}
	}



	public static void main(String[] args) {
		SpringApplication.run(PmToolApplication.class, args);
	}

}
