package uz.pdp.appsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 1. @Configuration anatatsiyasi qo'yiladi
 * 2. @EnabledWebSecurity anatatsiyasi qoyiladi
 * 3. WebSecurityConfigurerAdapter clasidan voris olinadi
 * 4. Voris olingan class ichidan configure(HttpSecurity http) methodi override qilinadi
 * 5. metod ichida ishlaymiz
 */

@Configuration //classni bean qiladi va bu class ichida yana binlarni ochishni taminlaydi
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //PreAuthorization ishlashi uchun
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //1 - ochilgan metod
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable() //serverdagi malumotlarni post, put delete qilishimiz mumkin boladi csrf bolmasa faqat get qilish mumkin
                .authorizeRequests() //requestlarni autorizatsiya qil

                /* bu yoqilsa rolelar orqali kiriladi *
                .antMatchers(HttpMethod.GET, "/api/product/*").hasAnyRole("USER", "MANAGER", "DIRECTOR")// bitta * demak faqat bittasini olish mumkin
                .antMatchers(HttpMethod.GET,"/api/product/*").hasAnyRole("DIRECTOR", "MANAGER") //  /api/product/ shu yolga meneger get metodi bilan murojat qilaoladi
                .antMatchers("/api/product/*").hasRole("DIRECTOR") // /api/product dan keyingi hamma yolni DIRECTOR  qilaolsin
                */

                .anyRequest() //har qanday requestni
                .authenticated() //requsetlarni tekshirib keyin otkazasan
                .and()
                .httpBasic(); //http basic bilan authentikatsiya qil, default google da form basic edi

    }





    //2 - ochilgan metod
    /**
     * Foydalanuvchilarni belgilash yani authorization . va Role larni berish
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                .withUser("director").password(passwordEncoder().encode("director")).roles("DIRECTOR") //demak 1 ta userning username, password va rollari degan xususiyatlari bor
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");

    }

    /**
     * Bu method passwordlarni encodlashimiz uchun
     * @return encodlangan password
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
















