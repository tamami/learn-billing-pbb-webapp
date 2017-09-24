package lab.aikibo.learnbillingpbbwebapp.services;

import lab.aikibo.learnbillingpbbwebapp.auth.TokenUser;
import lab.aikibo.learnbillingpbbwebapp.entity.DatLoginWebapp;
import lab.aikibo.learnbillingpbbwebapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public TokenUser loadUserByUsername(String username) throws UsernameNotFoundException {
        final DatLoginWebapp user = userRepo.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));

        TokenUser currentUser = new TokenUser(user);
        detailsChecker.check(currentUser);

        return currentUser;
    }
}
