package com.sabit.core.repository;

import com.sabit.core.entity.Role;
import com.sabit.core.entity.User;
import com.sabit.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select r from UserRole r where r.user.id = :userId and r.role.id = :roleId")
    UserRole findByUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Query("select r from UserRole r where r.user = :user and r.role = :role")
    UserRole findByUserRole(@Param("user") User user, @Param("role") Role role);

    @Query("select r.role from UserRole r where r.user = :user")
    List<Role> findRoleByUser(@Param("user") User user);

}
