package com.sabit.core.setup;

import com.sabit.core.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sabit.core.repository.PrivilegeRepository;
import com.sabit.core.repository.RoleRepository;
import com.sabit.core.repository.UserRepository;

import javax.transaction.Transactional;

@Component
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * Application start complete listener
	 *
	 * @param applicationReadyEvent
	 */
	@Override
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		SetupUtil.setupApp(userRepository, roleRepository, privilegeRepository, passwordEncoder, userRoleRepository);
	}

}
