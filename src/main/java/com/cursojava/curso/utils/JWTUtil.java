package com.cursojava.curso.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("clave")
    private String key;

    @Value("Main")
    private String issuer;

    @Value("3600000")
    private long ttlMillis;

    public String create(String id, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public String getValue(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt)
                .getBody();

        // Verificar si el token ha expirado antes de obtener el valor del campo
        // "subject"
        Date expirationDate = claims.getExpiration();
        if (expirationDate != null && expirationDate.before(new Date())) {
            return null; // El token ha expirado
        }

        return claims.getSubject();
    }

    public String getKey(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt)
                .getBody();

        // Verificar si el token ha expirado antes de obtener el valor del campo "id"
        Date expirationDate = claims.getExpiration();
        if (expirationDate != null && expirationDate.before(new Date())) {
            return null; // El token ha expirado
        }

        return claims.getId();
    }
}
