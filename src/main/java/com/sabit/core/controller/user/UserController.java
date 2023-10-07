package com.sabit.core.controller.user;

import com.sabit.core.entity.User;
import com.sabit.core.exceptionhandler.CoreException;
import com.sabit.core.service.user.UserService;
import com.sabit.core.service.user.dto.UserDto;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Api
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/all")
    ResponseEntity<List<UserDto>> all() {
        List<User> list = userService.getAll();

        if(list == null || list.isEmpty())
            return null;

        List<UserDto> resp = list.stream()
                .map(user->mapper.map(user,UserDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDto> get(@PathVariable Long id) {
        User user = userService.get(id);
        if(user == null)
            return null;
        return ResponseEntity.ok(mapper.map(user,UserDto.class));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(true);
    }

    @PostMapping
    ResponseEntity<UserDto> update(@RequestBody UserDto dto) {
        User user = userService.update(dto);

        if(user == null)
            throw CoreException.thrw("Kullanıcı bulunamadı!");

        return ResponseEntity.ok(mapper.map(user,UserDto.class));
    }

}
