package lab.aikibo.learnbillingpbbwebapp.config;

import lab.aikibo.learnbillingpbbwebapp.auth.LoginFilter;
import lab.aikibo.learnbillingpbbwebapp.auth.TokenAuthFilter;
import lab.aikibo.learnbillingpbbwebapp.services.TokenAuthService;
import lab.aikibo.learnbillingpbbwebapp.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("token.secret")
    public String SECRET;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenAuthService tokenAuthService;

    public SecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling().and()
            .anonymous().and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new TokenAuthFilter(tokenAuthService), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new LoginFilter("/auth/login", authenticationManager(), tokenAuthService),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
