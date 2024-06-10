package com.notification.notification.notification;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.security.Principal;
import java.util.Base64;
import java.util.Map;

@Component
public class NotificationHandshakeHandler extends DefaultHandshakeHandler {

    private final String jwtSecret = "SECRETKEY64ASDASDSADASDSADSADASDASDSADASSDASDASDASDASDASDAD";

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        URI uri = request.getURI();
        String query = uri.getQuery();
        String token;

        if (query != null && query.contains("token=")) {
            token = query.split("token=")[1];
            Claims claims = getUserIdFromToken(token);

            if (claims != null) {
                return claims::getSubject;
            }
        }
        return null;
    }
    public Claims getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public SecretKey getSigningKey() {
        String secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }
}
