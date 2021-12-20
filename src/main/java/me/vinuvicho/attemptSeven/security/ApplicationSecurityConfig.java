package me.vinuvicho.attemptSeven.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static me.vinuvicho.attemptSeven.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/scripts/test.js").permitAll()             //можна без аутентифікації
                .antMatchers("/user/**").hasRole(ADMIN.name())                                   //Тільки Адміни мають доступ
                .anyRequest().authenticated().and()
                .httpBasic();                                                                               //форма зверху
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails userVinuVicho = User.builder()
                .username("VinuVicho")
                .password(passwordEncoder.encode("1"))
                .roles(ADMIN.name())                                                                             //спрінг розуміє як ADMIN
                .build();

        UserDetails userKodlon = User.builder()
                .username("Kodlon")
                .password(passwordEncoder.encode("1"))
                .roles(USER.name())                                                                             //спрінг розуміє як USER
                .build();

        return new InMemoryUserDetailsManager(userKodlon, userVinuVicho);
    }
}
