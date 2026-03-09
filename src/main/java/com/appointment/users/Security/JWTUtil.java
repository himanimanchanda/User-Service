package com.appointment.users.Security;
import com.appointment.users.config.JWTConfig;
import com.appointment.users.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private final Key key;
    private final long expiration;

    public JWTUtil(JWTConfig config) {

        System.out.println(config.getSecret()+"---"+config.getExpiration());
        System.out.println(config.getSecret()+"---"+config.getExpiration());
        this.key = Keys.hmacShaKeyFor(config.getSecret().getBytes());
        this.expiration = config.getExpiration();
    }

    public String generateToken(Long userId, Role role, Long organisationId) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key);

        if (organisationId != null) {
            builder.claim("orgId", organisationId);
        }
        return builder.compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}

//
//package com.appointment.users.Security;
//
//import com.appointment.users.entity.Role;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JWTUtil {
//    private final Key key;
//    private final long expiration = 3600000; // 1 Hour
//
//    // Bypass application.properties temporarily
//    public JWTUtil() {
//        String secret = "thisIsMySuperLongAndSecureSecretKeyForJWT1234567890";
//        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//        System.out.println("USER SERVICE SECRET LOADED: " + secret);
//    }
//
//    public String generateToken(Long userId, Role role, Long organisationId) {
//        JwtBuilder builder = Jwts.builder()
//                .setSubject(userId.toString())
//                .claim("role", role.name())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(key, SignatureAlgorithm.HS256);
//
//        if (organisationId != null) {
//            builder.claim("orgId", organisationId);
//        }
//        return builder.compact();
//    }
//    public Claims validateToken(String token) {
//        String cleanToken = token != null ? token.trim() : null;
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(cleanToken)
//                .getBody();
//    }
//}