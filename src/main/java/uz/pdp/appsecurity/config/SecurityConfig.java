package uz.pdp.appsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 1. @Configuration anatatsiyasi qo'yiladi
 * 2. @EnabledWebSecurity anatatsiyasi qoyiladi
 * 3. WebSecurityConfigurerAdapter clasidan voris olinadi
 * 4. Voris olingan class ichidan configure(HttpSecurity http) methodi override qilinadi
 * 5. metod ichida ishlaymiz
 */

@Configuration //classni bean qiladi va bu class ichida yana binlarni ochishni taminlaydi
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() //serverdagi malumotlarni post, put delete qilishimiz mumkin boladi csrf bolmasa faqat get qilish mumkin
                .authorizeRequests() //requestlarni autorizatsiya qil
                .anyRequest() //har qanday requestni
                .authenticated() //requsetlarni tekshirib keyin otkazasan
                .and()
                .httpBasic(); //http basic bilan authentikatsiya qil, default google da form basic edi

    }
}
