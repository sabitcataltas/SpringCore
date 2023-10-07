package com.sabit.core.service.user.impl;

import com.sabit.core.exceptionhandler.CoreException;
import com.sabit.core.service.user.UserService;
import com.sabit.core.service.user.dto.UserDto;
import com.sabit.core.utils.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sabit.core.entity.User;
import com.sabit.core.repository.UserRepository;
import com.sabit.core.security.dto.RegistrationRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ModelMapper mapper;

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			System.out.println("User empty!!");
			return null;
		}
		System.out.println("User found: " + user.getEmail());
		return user;
	}

	@Override
	@Transactional
	public User save(User user) {
		// User u = modelMapper.map(user, User.class);
		// u = userRepository.save(u);
		// user.setId(u.getId());
		User u = userRepository.save(user);
		user.setId(u.getId());
		return u;
	}

	@Override
	@Transactional
	public Boolean register(RegistrationRequest registrationRequest) {
		try {
			User user = new User();
			user.setEmail(registrationRequest.getEmail());
			user.setLastName(registrationRequest.getNameSurname());
			user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
			user.setUsername(registrationRequest.getUsername());
			userRepository.save(user);
			return Boolean.TRUE;
		} catch (Exception e) {
			log.error("REGISTRATION=>", e);
			return Boolean.FALSE;
		}
	}

	@Override
	public List<User> getAll(){
		return userRepository.findAll();
	}

	@Override
	public User get(Long id){
		return userRepository.getById(id);
	}

	@Override
	@Transactional
	public void delete(Long id){
		User user = userRepository.getById(id);
		if (user == null)
			throw CoreException.thrw("Kayit bulunamadi");

		user.setStatus(Status.DELETED);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User update(UserDto dto) {
		Optional<User> user = userRepository.findById(dto.getId());

		if(!user.isPresent())
			return null;

		User copy = new User();
		mapper.map(user.get(), copy);
		copy.setFirstName(dto.getFirstName());
		copy.setLastName(dto.getLastName());
		copy.setEnabled(dto.isEnabled());
		copy.setVersion(dto.getVersion());

		return userRepository.save(copy);
	}
}
