package lab.aikibo.learnbillingpbbwebapp.auth;


import lab.aikibo.learnbillingpbbwebapp.entity.DatLoginWebapp;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by tamami on 9/22/17.
 */
public class TokenUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private DatLoginWebapp user;

    public TokenUser(DatLoginWebapp user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getRole() {
        return user.getRole();
    }

}
