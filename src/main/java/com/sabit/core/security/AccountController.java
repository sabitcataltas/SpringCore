package com.sabit.core.security;

import com.sabit.core.security.dto.LoginRequest;
import com.sabit.core.security.dto.LoginResponse;
import com.sabit.core.security.dto.RegistrationRequest;
import com.sabit.core.security.jwt.JwtTokenUtil;
import com.sabit.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/token")
@Slf4j
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws AuthenticationException {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // final User user = userRepository.findByUsername(request.getUsername());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(userDetails.getUsername(), token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@RequestBody RegistrationRequest registrationRequest)
            throws AuthenticationException {
        Boolean response;
        try {
            response = userService.register(registrationRequest);
        } catch (Exception e) {
            log.error("REGISTRATION=>", e);
            response = Boolean.FALSE;
        }

        return ResponseEntity.ok(response);
    }
}
