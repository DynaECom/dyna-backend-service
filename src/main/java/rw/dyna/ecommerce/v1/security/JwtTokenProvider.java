package rw.dyna.ecommerce.v1.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import rw.dyna.ecommerce.v1.exceptions.JWTVerificationException;
import rw.dyna.ecommerce.v1.models.User;
import rw.dyna.ecommerce.v1.security.jwt.JWTUserInfo;
import rw.dyna.ecommerce.v1.utils.Mapper;


import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_EMAIL = "email";
    private static final String CLAIM_KEY_ROLE = "role";


    @Value("${jwt.expires}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (GrantedAuthority role : userPrincipal.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }


        User authUser = Mapper.getUserFromDTO(userPrincipal);


        return Jwts.builder()
                .setId(authUser.getId() + "")
                .setSubject(userPrincipal.getId() + "")
                .claim("user", authUser)
                .claim("authorities", grantedAuthorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String createToken(UUID userId , String email , String role){

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH , 1);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return  Jwts.builder()
                .claim(CLAIM_KEY_USER_ID, userId)
                .claim(CLAIM_KEY_EMAIL, email)
                .claim(CLAIM_KEY_ROLE, role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256 , jwtSecret).compact();
    }
    public JWTUserInfo decodeToken(String token) throws JWTVerificationException {
        Claims claims = extractAllClaims(token);
        String userId = (String) claims.get(CLAIM_KEY_USER_ID);
        String email = (String) claims.get(CLAIM_KEY_EMAIL);
        String role = (String) claims.get(CLAIM_KEY_ROLE);
        return new JWTUserInfo().setEmail(email)
                .setRole(role)
                .setUserId(userId);
    }
    public Boolean isTokenValid(String token , UserSecurityDetails userSecurityDetails){
        Claims claims = extractAllClaims(token);
        String email = (String) claims.get(CLAIM_KEY_EMAIL);
        final String username = email;
        System.out.println("In token: " + email );
        System.out.println("In user details: " +  userSecurityDetails.getUsername());
//        System.out.println("Username : "+ username.equals(userSecurityDetails.getUsername()) );
//        System.out.println("Expiration: " + !isTokenExpired(token));
        return (username.equals(userSecurityDetails.getUsername()) && !isTokenExpired(token));
    }
    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }
    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Boolean isTokenExpired(String token){
        Date expirationDate = extractExpiration(token);
        Date currentTime  = new Date(System.currentTimeMillis());
        return !currentTime.before(expirationDate);
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String createToken(UUID userId , String email){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH , 1);

        return  Jwts.builder()
                .claim(CLAIM_KEY_USER_ID, userId)
                .claim(CLAIM_KEY_EMAIL, email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256 , jwtSecret).compact();
    }

    public String generateTokenWithUser(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getEmail() + "")
                .claim("user", user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public String getUserEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token" + ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token" + ex);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty" + ex);
        }
        return false;
    }
}

