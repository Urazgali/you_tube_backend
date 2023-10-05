package com.example.util;

import com.example.dto.JwtDTO;
import com.example.exp.UnAuthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    public static final String secretKey = "!maz234^gikey";

    public static final long tokenLiveTime = 1000*3600*24*10; // 10day //TODO ABDULLO 1 hour
    public static final long emailTokenLiveTime = 1000*3600*24 *10; // 10 day //TODO ABDULLO 1 day

    public static String encode(String email) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("email", email);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("YOUTUBE PORTAL");
        return jwtBuilder.compact();
    }
    public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            String email = (String) claims.get("email");

            return new JwtDTO(email);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }

    public static String encodeEmailJwt(Integer profileId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.setIssuer("YOUTUBE PORTAL");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeEmailJwt(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            Integer id = (Integer) claims.get("id");
            return new JwtDTO(id);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }
}