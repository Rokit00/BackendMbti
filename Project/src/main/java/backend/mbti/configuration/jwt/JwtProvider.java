package backend.mbti.configuration.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    public static String getUserName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createToken(String userId, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); // Token info
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 날짜, 현재 시간 기준
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 만료 날짜
                .signWith(SignatureAlgorithm.HS256, key) // 서명 같은 것, 위조 구분 시 사용
                .compact()
                ;
    }
}
