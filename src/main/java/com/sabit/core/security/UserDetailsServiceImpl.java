package com.sabit.core.security;

import java.util.*;

import com.sabit.core.entity.Privilege;
import com.sabit.core.repository.RoleRepository;
import com.sabit.core.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sabit.core.entity.Role;
import com.sabit.core.entity.User;
import com.sabit.core.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(userRoleRepository.findRoleByUser(user)));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		Set<String> privileges = new HashSet<>();
		for (Role role : roles) {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
			//role.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getName())).forEach(authorities::add);
			for (Privilege p : role.getPrivileges()){
				if(!privileges.contains(p.getName())){
					privileges.add(p.getName());
					authorities.add(new SimpleGrantedAuthority(p.getName()));
				}
			}
		}

		return authorities;
	}
}