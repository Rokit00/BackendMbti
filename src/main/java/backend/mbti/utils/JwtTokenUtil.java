package backend.mbti.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String userId, String key, long expireTimeMs) {
<<<<<<< HEAD
        Claims claims = Jwts.claims(); // map 같은 거
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 만든 날짜
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 끝나는 날짜
                .signWith(SignatureAlgorithm.HS256, key) // 키를 가지고 sign
=======
        Claims claims = Jwts.claims(); // map 같은 것
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims) // 토큰에 포함되는 정보 설정, 키-값 Map 같은 것,
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 날짜, 현재 시간 기준
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 만료 날짜
                .signWith(SignatureAlgorithm.HS256, key) // 서명 같은 것, 위조 구분 시 사용
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
                .compact()
                ;
    }
}
