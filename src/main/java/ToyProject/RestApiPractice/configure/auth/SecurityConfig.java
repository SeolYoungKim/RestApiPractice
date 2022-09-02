package ToyProject.RestApiPractice.configure.auth;

import ToyProject.RestApiPractice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }

    /**
     * 최신 코드 - SecurityFilterChain 작성 방식 변경 (람다식 방식)
     * 위 코드도 무리없이 돌아는 감
     */

    public SecurityFilterChain newFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService));

        return http.build();
    }
}
