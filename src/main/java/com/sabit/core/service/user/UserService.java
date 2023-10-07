package com.sabit.core.service.user;

import com.sabit.core.entity.User;
import com.sabit.core.security.dto.RegistrationRequest;
import com.sabit.core.service.user.dto.UserDto;

import java.util.List;

public interface UserService {

	 User findByUsername(String username);
	 
	 User save(User user);
	 
	 Boolean register(RegistrationRequest registrationRequest);

	 List<User> getAll();

	User get(Long id);

	void delete(Long id);

	User update(UserDto dto);
}
