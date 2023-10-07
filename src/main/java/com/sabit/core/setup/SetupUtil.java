package com.sabit.core.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.sabit.core.entity.UserRole;
import com.sabit.core.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sabit.core.entity.Privilege;
import com.sabit.core.entity.Role;
import com.sabit.core.entity.User;
import com.sabit.core.repository.PrivilegeRepository;
import com.sabit.core.repository.RoleRepository;
import com.sabit.core.repository.UserRepository;

public class SetupUtil {
    static boolean alreadySetup = false;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PrivilegeRepository privilegeRepository;
    PasswordEncoder passwordEncoder;
    UserRoleRepository userRoleRepository;

    String[] adminPrivileges = new String[]{"ADMIN", "USER_ADD", "USER_EDIT", "USER_GET", "USER_DELETE", "ROLE_ADD",
            "ROLE_EDIT", "ROLE_DELETE"};
    String[] otherPrivileges = new String[]{"PRODUCT_ADD", "PRODUCT_EDIT", "PRODUCT_GET", "PRODUCT_DELETE"};

    public SetupUtil(UserRepository userRepository, RoleRepository roleRepository,
                     PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public static void setupApp(UserRepository userRepository, RoleRepository roleRepository,
                                PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        new SetupUtil(userRepository, roleRepository, privilegeRepository, passwordEncoder, userRoleRepository).setup();
    }

    public void setup() {
        if (alreadySetup)
            return;
        List<Privilege> adminPriv = new ArrayList<>();
        List<Privilege> otherPriv = new ArrayList<>();
        for (String s : adminPrivileges)
            adminPriv.add(createPrivilegeIfNotFound(s));
        for (String s : otherPrivileges)
            otherPriv.add(createPrivilegeIfNotFound(s));
        adminPriv.addAll(otherPriv);

        createRoleIfNotFound("ROLE_ADMIN", adminPriv);
        createRoleIfNotFound("ROLE_USER", otherPriv);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("admin@admin.com");
        user.setUsername("admin");
        //user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        createUserIfNotFound(user);
        createUserRoleIfNotFound(user, adminRole);

        Role userRole = roleRepository.findByName("ROLE_USER");
        User user2 = new User();
        user2.setFirstName("Test");
        user2.setLastName("Test");
        user2.setPassword(passwordEncoder.encode("test"));
        user2.setEmail("test@test.com");
        user2.setUsername("test");
        //user2.setRoles(Arrays.asList(userRole));
        user2.setEnabled(true);
        createUserIfNotFound(user2);
        createUserRoleIfNotFound(user2, userRole);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    User createUserIfNotFound(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        if (u == null) {
            u = userRepository.save(user);
        }
        return u;
    }

    UserRole createUserRoleIfNotFound(User user, Role role) {
        UserRole ur = userRoleRepository.findByUserRole(user, role);
        if (ur == null) {
            ur = new UserRole();
            ur.setUser(user);
            ur.setRole(role);
            ur = userRoleRepository.save(ur);
        }
        return ur;
    }

}
