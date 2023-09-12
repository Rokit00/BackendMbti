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
                .cors().disable();
        // 세션 관리
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 권한 설정
        httpSecurity
                .authorizeRequests()
                .antMatchers("/auth/kakao/callback", "/members/signup","/members/login","/post/lists", "/comment/*/count","/post/*","/actuator/**", "/instances/**").permitAll()
                .anyRequest().authenticated();
        // 필터
        httpSecurity
                .addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().antMatchers(HttpMethod.POST, "/members/**","/member/*/**", "/mypage/**", "/mypage/","/post/**", "/post/*/like", "/comment/*", "/comment/*/like")
                .requestMatchers().antMatchers(HttpMethod.GET, "/mypage", "/mypage/*/posts", "/mypage/**")
                .requestMatchers().antMatchers(HttpMethod.PUT, "/post/*", "/mypage/update-all", "/comment/*")
                .requestMatchers().antMatchers(HttpMethod.DELETE, "/post/*", "/mypage/*", "/comment/*");
        return httpSecurity.build();

    }
}
