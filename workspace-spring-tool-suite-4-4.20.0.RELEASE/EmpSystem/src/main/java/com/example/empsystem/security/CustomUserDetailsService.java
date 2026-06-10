package com.example.empsystem.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	private EmployeeRepository empRepo;
	
	
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	   Employee emp = empRepo.findByUsername(username);
	   if (emp == null) {
		   throw new UsernameNotFoundException("User not found: " + username);
	   }
	   return new User(emp.getUsername(), emp.getPassword(),
			   List.of(new SimpleGrantedAuthority("ROLE_" + emp.getRole())));
   }		
	
	
}
