package ru.shemich.donationapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.shemich.donationapp.exception.JwtAuthenticationException;
import ru.shemich.donationapp.model.Role;
import ru.shemich.donationapp.model.User;
import ru.shemich.donationapp.service.impl.UserServiceImpl;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //Он будет генерировать и валидировать access токен.

    private final UserDetailsService userDetailsService;
    private final UserServiceImpl userServiceImpl;


    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long validityInMilliseconds;
    @Value("${jwt.header}")
    private String authorizationHeader;

    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, UserServiceImpl userServiceImpl) {
        this.userDetailsService = userDetailsService;
        this.userServiceImpl = userServiceImpl;
    }

    //для безопасности
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String createToken(String userName, Long userId, Role role) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);
        claims.put("user_id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            log.info("* Start validating token *");
            String userName = getUserName(token);  //  парсим приходящий токен, вытаскиваем поле sub из payload т.е имя приложения
            String userId = String.valueOf(getUserId(token));
            Date dateOfExpiration = getDateOfExpiration(token);
            User user = userServiceImpl.getById(Long.valueOf(userId));

/*            // Читаем системные клеймы
            log.info("Subject: " + userName);
            log.info("Expiration: " + dateOfExpiration);

            // Читаем кастомные клеймы
            log.info("User id: " + userId);
            log.info("User info by id={}: {}", userId, user);
            log.info("Token equals: {}", user.getToken().equals(token));
            log.info("Token non expired: {}", !user.getDateOfExpiration().before(new Date()));*/

            return user.getToken().equals(token) && !user.getDateOfExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return String.valueOf(claims.get("user_id"));
    }

    public Date getDateOfExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

}