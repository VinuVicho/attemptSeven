package me.vinuvicho.attemptSeven.security;

import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static me.vinuvicho.attemptSeven.entity.user.UserRole.ADMIN;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;
//    private final UserService userService;
//
//    @Autowired
//    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
//        this.passwordEncoder = passwordEncoder;
//        this.userService = userService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()          //disabled for now  TODO: make csrf
                .disable()
                .authorizeRequests()
                .antMatchers("index", "/css/*", "/scripts/test.js", "/logout", "/register/**").permitAll()   //можна без аутентифікації
//                .antMatchers("/user/**").hasRole(ADMIN.name())                                   //Тільки Адміни мають доступ
//                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(USER_EDIT.getPermission()) //замінив на @PreAuthorise
//                .antMatchers("/management/**").hasAnyRole(ADMIN.name(), HALF_ADMIN.name())
//                .anyRequest().authenticated()
                .and()
                .formLogin()                                                                                //форма зверху -- httpBasic
                .loginPage("/login").permitAll().and()
                .rememberMe().and()
                .logout().logoutSuccessUrl("/")
        ;
    }

////        працює і без цього коду лол
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean         //метод має повертати бін
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(userService);
//        return provider;
//    }
}
