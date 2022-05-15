package ru.otus.finalproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.finalproject.domain.Role;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userService;

    public SecurityConfig(UserDetailServiceImpl userService) {
        this.userService = userService;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .mvcMatchers( "/request/**" ).permitAll()
                    .mvcMatchers( "/admin/**").hasRole(Role.ADMIN.name())
                    .mvcMatchers( "/","/about","/contacts","/product").permitAll()
                    .mvcMatchers(
                            "/actuator/**",
                            "/h2-console/**").permitAll()
                    .mvcMatchers("/order/**").authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/do-login")
                    .usernameParameter("user")
                    .passwordParameter("pass")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("uniqueAndSecret")
                .tokenValiditySeconds(10*60);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
