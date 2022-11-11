package ru.shemich.donationapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.donationapp.api.request.AuthenticationRequest;
import ru.shemich.donationapp.model.Role;
import ru.shemich.donationapp.model.User;
import ru.shemich.donationapp.model.UserAuthDetails;
import ru.shemich.donationapp.repository.UserDetailsRepository;
import ru.shemich.donationapp.repository.UserRepository;
import ru.shemich.donationapp.security.JwtTokenProvider;
import ru.shemich.donationapp.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/auth", produces = APPLICATION_JSON_VALUE)
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userServiceImpl;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserDetailsRepository userDetailsRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserServiceImpl userServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            String login = request.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(),request.getPassword()));
            UserAuthDetails userAuthDetails = userDetailsRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not exists"));
            User user = userRepository.getById(userAuthDetails.getId());
            Long userId = userAuthDetails.getId();
            Role userRole = userAuthDetails.getRole();
            String token = jwtTokenProvider.createToken(login, userId, userRole);
            Date date = jwtTokenProvider.getDateOfExpiration(token);
            Map<Object, Object> response = new HashMap<>();
            user.setToken(token);
            userServiceImpl.saveUser(user);
            log.info("{} save token: {} in db until {}", login, token, date);
            response.put("login", request.getLogin());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response,null);
    }
}