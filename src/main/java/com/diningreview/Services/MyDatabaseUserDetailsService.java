package com.diningreview.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diningreview.model.User;
import com.diningreview.repository.UsersRepository;

@Service
public class MyDatabaseUserDetailsService implements UserDetailsService {

	private UsersRepository userRepository;

	public MyDatabaseUserDetailsService(UsersRepository usersRepository) {
		this.userRepository = usersRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("No account found for " + username));

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					getAuthorities(user.getRole()));
		} else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		 List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();

	        listRole.add(new SimpleGrantedAuthority(role)); 
	        return listRole;
	}
}
