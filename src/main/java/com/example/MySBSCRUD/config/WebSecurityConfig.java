package com.example.MySBSCRUD.config;

import com.example.MySBSCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.sql.DataSource;

// Класс для переключения автоконфиг безопасности под наши реалии
// Метод configure (HttpSecurity http) настроен на:
//        Игнорировать статические пути к ресурсам "/ resources / ", "/ webjars / " и "/ assets / **"
//        Разрешить всем иметь доступ к корневому URL "/"
//        Ограничть доступ к URL-адресам, начинающимся с / admin /, только пользователям с ролью ADMIN
//        Все остальные URL-адреса должны быть доступны только авторизованным пользователям.
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/Index").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/Adminhome/**").hasRole("ADMIN")
                .antMatchers("/Userhome").hasRole("USER")
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/Userhome")
                .permitAll();

    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

}
