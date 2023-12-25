package com.formal.math.config;

import com.formal.math.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationFailureHandler customFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("----------------configure-----------------");
        http
            //.httpBasic(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .rememberMe(rememberMe -> rememberMe
                .key("123456")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30)
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
//                .usernameParameter("userId")
//                .passwordParameter("passwd")
//                .loginProcessingUrl("/login_proc")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        System.out.println("authentication : "+ authentication.getName());
//                        response.sendRedirect("/");
//                    }
//                })
                .failureHandler(customFailureHandler)
//                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")   // 로그아웃 처리 URL (= form action url)
                .logoutSuccessUrl("/") // 로그아웃 성공 후 targetUrl,
//                // logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
//                .addLogoutHandler((request, response, authentication) -> {
//                    // 사실 굳이 내가 세션 무효화하지 않아도 됨.
//                    // LogoutFilter가 내부적으로 해줌.
//                    HttpSession session = request.getSession();
//                    if (session != null) {
//                        session.invalidate();
//                    }
//                    })  // 로그아웃 핸들러 추가
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.sendRedirect("/login");
//                }) // 로그아웃 성공 핸들러
//                .deleteCookies("remember-me") // 로그아웃 후 삭제할 쿠키 지정
//            )
//            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/resources/**", "/signup", "/about").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/db/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))
//              //.requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))
//                .anyRequest().denyAll()
            )
            .oauth2Login((oauth2) -> oauth2
                  .loginPage("/oauth2/authorization/google")
                  .defaultSuccessUrl("/")
            );

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("---------web configure---------------");
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
