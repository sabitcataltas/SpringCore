package com.sabit.core.service.user.dto;

import com.sabit.core.entity.User;
import com.sabit.core.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private Long version;
    private Status status;

    public User toUpdateEntity() {
        User u = new User();
        u.setId(id);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEnabled(enabled);
        u.setVersion(version);
        return u;
    }
}
