package ru.shemich.donationapp.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.shemich.donationapp.model.Status;
import ru.shemich.donationapp.model.UserAuthDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUserApp implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUserApp(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static org.springframework.security.core.userdetails.UserDetails fromUserApp(UserAuthDetails userAuthDetails){
        return new User(userAuthDetails.getLogin(), userAuthDetails.getPassword(),
                userAuthDetails.getStatus().equals(Status.ACTIVE),
                userAuthDetails.getStatus().equals(Status.ACTIVE),
                userAuthDetails.getStatus().equals(Status.ACTIVE),
                userAuthDetails.getStatus().equals(Status.ACTIVE),
                userAuthDetails.getRole().getAuthorities()
        );
    }
}