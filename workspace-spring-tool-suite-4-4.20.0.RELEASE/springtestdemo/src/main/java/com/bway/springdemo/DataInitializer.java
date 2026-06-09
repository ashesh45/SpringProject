package com.bway.springdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		for (User user : userRepo.findAll()) {
			String pwd = user.getPassword();
			if (pwd != null && !pwd.startsWith("$2a$") && !pwd.startsWith("$2b$") && !pwd.startsWith("$2y$")) {
				log.warn("Migrating plain-text password for user '{}' to BCrypt", user.getUsername());
				user.setPassword(passwordEncoder.encode(pwd));
				userRepo.save(user);
			}
		}

		if (userRepo.findByUsername("admin").isEmpty()) {
			User admin = new User();
			admin.setFname("Admin");
			admin.setLname("User");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			userRepo.save(admin);
			log.info("Created default admin user (username=admin, password=admin123)");
		}
	}

}
