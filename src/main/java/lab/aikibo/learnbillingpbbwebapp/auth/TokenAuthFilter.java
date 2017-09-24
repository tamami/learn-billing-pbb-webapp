package lab.aikibo.learnbillingpbbwebapp.auth;

import lab.aikibo.learnbillingpbbwebapp.services.TokenAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenAuthFilter extends GenericFilterBean {

    private final TokenAuthService authService;

    public TokenAuthFilter(TokenAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication = authService.getAuthentication((HttpServletRequest) servletRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
