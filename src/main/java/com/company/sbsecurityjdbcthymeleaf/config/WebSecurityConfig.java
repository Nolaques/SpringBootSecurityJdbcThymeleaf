package com.company.sbsecurityjdbcthymeleaf.config;


import com.company.sbsecurityjdbcthymeleaf.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        //setting service to find user in the database
        // and setting PasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        //pages does not require login
        http.authorizeRequests().antMatchers("/", "/login","logout").permitAll();

        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN
        // if no login, it will redirect to /login page
        http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

        //for admin only
        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

        //when the user has logged in as XX
        //but access a page that requires role YY,
        //accessDeniedException will be thrown
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        //config for login form
        http.authorizeRequests().and().formLogin()
                //submit URL of login page
                .loginProcessingUrl("/j_spring_security_check") // submitting url
                .loginPage("/login")
                .defaultSuccessUrl("/userAccountInfo")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                // config for logout page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }
}
