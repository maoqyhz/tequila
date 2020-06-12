package xyz.fur.skeleton.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import xyz.fur.skeleton.entity.User;
import xyz.fur.skeleton.entity.support.TokenConstant;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * JWT utils
 *
 * @author Fururur
 * @date 2020-03-11-16:03
 */
@Slf4j
public class JwtUtils {

    /**
     * Jwt 生成
     * @param user
     * @return
     */
    public static String createJwt(User user) {
        LocalDateTime now = LocalDateTime.now();
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", TokenConstant.TYP)
                .claim("userId", user.getId())
                .setSubject(user.getName())
                .setIssuer(TokenConstant.ISSUER)
                .setIssuedAt(LocalDateTimeUtils.convertLDTToDate(now)) //设置签发时间
                .setExpiration(LocalDateTimeUtils.convertLDTToDate(now.plusDays(TokenConstant.EXPIRED_DAY)))
                .signWith(SignatureAlgorithm.HS256, TokenConstant.SK);//设置签名秘钥
        return builder.compact();
    }

    /**
     * 解密
     *
     * @param token
     * @return
     */
    public static Claims parseJwt(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(TokenConstant.SK).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("parse jwt failed!");
            return null;
        }
        return claims;
    }

    /**
     * 判断是否过期
     *
     * @param claims
     * @return
     */
    public static boolean isExpired(Claims claims) {
        if (claims == null) {
            log.error("token verify failed!");
            return false;
        } else {
            if (claims.getExpiration().before(new Date())) {
                log.error("token is expired!");
                return true;
            } else
                return false;
        }
    }

    /**
     * 解析用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = parseJwt(token);
        if (claims == null) {
            log.error("token verify failed!");
            return null;
        } else
            return claims.getSubject();
    }

    public static Integer getUserId(String token) {
        Claims claims = parseJwt(token);
        if (claims == null) {
            log.error("token verify failed!");
            return null;
        } else
            return claims.get("userId", Integer.class);
    }

    public static String getUsername(Claims claims) {
        if (claims == null) {
            log.error("token verify failed!");
            return null;
        } else
            return claims.getSubject();
    }

    public static void main(String[] args) {
        // System.out.println(createJwt("1", "arc", "admin", "AISG"));
        // String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJkZXB0IjoiQUlTRyIsInVzZXJJZCI6IjEiLCJzdWIiOiJhcmMiLCJpc3MiOiJBcmNTb2Z0IiwiaWF0IjoxNTgzOTE0NzM0fQ.8xd3br1Trp8LG0zNKSM_QCjkZrT3ZTypJ1Wnkh5rPWU";
        String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaWiLCJkZXB0IjoiQUlTRyIsInVzZXJJZCI6IjEiLCJzdWIiOiJhcmMiLCJpc3MiOiJBcmNTb2Z0IiwiaWF0IjoxNTgzOTE0NzM0fQ.8xd3br1Trp8LG0zNKSM_QCjkZrT3ZTypJ1Wnkh5rPWU";
        Claims claims = parseJwt(str);
        System.out.println(claims.getSubject());
        System.out.println(claims.get("role"));
    }
}
