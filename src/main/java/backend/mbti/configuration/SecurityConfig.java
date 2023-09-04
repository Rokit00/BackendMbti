package backend.mbti.configuration;

import backend.mbti.configuration.jwt.JwtFilter;
import backend.mbti.configuration.jwt.JwtProvider;
import backend.mbti.domain.member.Member;
import backend.mbti.service.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/members/signup", "/members/login").permitAll()
                .antMatchers(HttpMethod.POST, "/post").authenticated() // 인증해아함
                .and()
                .addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
}
