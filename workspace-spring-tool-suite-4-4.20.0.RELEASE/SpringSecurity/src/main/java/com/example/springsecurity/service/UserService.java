package com.example.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity.model.MyUser;
import com.example.springsecurity.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	   MyUser user = userRepo.findByUsername(username);
	   if (user == null) {
		   throw new UsernameNotFoundException("User not found: " + username);
	   }
	   return new User(user.getUsername(), user.getPassword(),
			   List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
   }		
	

	
			
	public void saveUser(MyUser user) {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	userRepo.save(user);

}

	
	


}