package com.hansungmarket.demo.config;

import com.hansungmarket.demo.config.login.CustomAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .csrf().disable()// 테스트용, 나중에 설정해야 함

                .authorizeRequests()
                .antMatchers("/main").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/boards/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/boards/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/boards/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/boards/**").hasRole("USER")

                .antMatchers(HttpMethod.GET, "/api/myBoards").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/likeBoards/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/likeBoards/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/likeBoards/**").hasRole("USER")

                .antMatchers(HttpMethod.GET, "/api/images/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/users").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/changePassword").permitAll()

                .antMatchers(HttpMethod.POST, "/api/signUp/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/signUp/**").permitAll()

                .antMatchers("/api/login/fail").permitAll()
                .antMatchers("/api/logout/success").permitAll()

                .antMatchers(HttpMethod.GET, "/api/auth/mail").authenticated()
                .antMatchers(HttpMethod.GET, "/api/auth/{token}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/saleUser").permitAll()
                .antMatchers(HttpMethod.GET, "/api/saleGoods").permitAll()
                .antMatchers(HttpMethod.GET, "/api/likeGoods").permitAll()

                .antMatchers(HttpMethod.POST, "/api/mail/usernames").permitAll()
                .antMatchers(HttpMethod.POST, "/api/mail/findPasswordToken").permitAll()

                .antMatchers(HttpMethod.GET, "/api/chatRoom/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/chat/notice").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/chat/notice").hasRole("USER")

                .antMatchers(PERMIT_URL_ARRAY).permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin()
                    .loginPage("/api/login")
                    .loginProcessingUrl("/api/login").permitAll() // post 요청
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(customAuthenticationHandler)
                    .failureHandler(customAuthenticationHandler)

                .and()
                .logout()
                    .logoutUrl("/api/logout") // csrf 적용 시, post 요청
                    .logoutSuccessHandler(customAuthenticationHandler);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

