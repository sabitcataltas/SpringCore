package com.sabit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sabit.core.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from users u where u.id = :id")
	User getById(@Param("id") Long id);

	@Query("select u from users u where u.username = :username and u.status <> 3")
	User findByUsername(@Param("username") String username);

	@Query("select u from users u where u.email = :email and u.status <> 3")
	User findByEmail(String email);

	@Query("select u from users u where u.email=:emailOrUsername or u.username=:emailOrUsername and u.status <> 3")
	User findByEmailOrUsername(String emailOrUsername);

	@Transactional
	@Query("update users u set u.status = 3 where u.id = :id")
	@Modifying
	void deleteUser(Long id);

}
