package ch.noseryoung.gg.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "3763432a2c277049365e597245742c79642a563940425027386566234b";
    private static final int  jwtExpiration = 86400000; // 24 hours

    /**
     * Extracts the username from the JWT token
     *
     * @param jwtToken the JWT token
     * @return the username
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String extractRole(String jwtToken) {
        return extractClaim(jwtToken, claims -> claims.get("role", String.class));
    }

    /**
     * Extracts a claim from the JWT token
     *
     * @param jwtToken       the JWT token
     * @param claimsResolver the claims resolver
     * @param <T>            the type of the claim
     * @return the claim
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Creates a JWT token that is valid for 24 hours
     *
     * @param userDetails the user details
     * @return the JWT token
     */
    public static String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Builds a JWT token that is valid for 24 hours
     *
     * @param extraClaims the extra claims
     * @param userDetails the user details
     * @return the JWT token
     */
    public static String generateToken(
            Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the JWT token is valid
     *
     * @param jwtToken    the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if the JWT token is expired
     *
     * @param jwtToken the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token
     *
     * @param jwtToken the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token
     *
     * @param jwtToken the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Returns the key (secret) used to sign the JWT token
     *
     * @return the key
     */
    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}