package me.vinuvicho.attemptSeven.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static me.vinuvicho.attemptSeven.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()          //disabled for now  TODO: make csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/scripts/test.js").permitAll()             //можна без аутентифікації
                .antMatchers("/user/**").hasRole(ADMIN.name())                                   //Тільки Адміни мають доступ
//                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(USER_EDIT.getPermission()) //замінив на @PreAuthorise
//                .antMatchers(HttpMethod.POST, "/management/**").hasAuthority(USER_EDIT.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(USER_EDIT.getPermission())
//                .antMatchers("/management/**").hasAnyRole(ADMIN.name(), HALF_ADMIN.name())
                .anyRequest().authenticated().and()
                .httpBasic();                                                                               //форма зверху
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails userVinuVicho = User.builder()
                .username("VinuVicho")
                .password(passwordEncoder.encode("1"))
//                .roles(ADMIN.name())                                                                             //спрінг розуміє як ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails userKodlon = User.builder()
                .username("Kodlon")
                .password(passwordEncoder.encode("1"))
//                .roles(USER.name())                                                                             //спрінг розуміє як ROLE_USER
                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails userVouzze = User.builder()
                .username("Vouzze")
                .password(passwordEncoder.encode("1"))
//                .roles(HALF_ADMIN.name())                                                                             //спрінг розуміє як ROLE_HALF_ADMIN
                .authorities(HALF_ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(userKodlon, userVinuVicho, userVouzze);
    }
}
