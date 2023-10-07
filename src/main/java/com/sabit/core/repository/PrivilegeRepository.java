package com.sabit.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabit.core.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	@Query("select r from Privilege r where r.name=:name")
	Privilege findByName(@Param("name") String name);

}
