package ru.shemich.donationapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.UserAuthDetails;
import ru.shemich.donationapp.repository.UserDetailsRepository;

@Service("userDetailsServiceImpl")
public class UserAppDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserAppDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserAuthDetails userDetails = userDetailsRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User not exists"));
        return SecurityUserApp.fromUserApp(userDetails);
    }
}