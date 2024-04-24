package rw.dyna.ecommerce.v1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import rw.dyna.ecommerce.v1.exceptions.JWTVerificationException;
import rw.dyna.ecommerce.v1.exceptions.TokenException;
import rw.dyna.ecommerce.v1.security.jwt.JWTUserInfo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = httpServletRequest.getHeader("Authorization");
        JWTUserInfo jwtUserInfo = null;
        String jwtToken = null;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            jwtUserInfo = tokenProvider.decodeToken(jwtToken);
        } catch (JWTVerificationException e) {
            throwErrors(httpServletRequest , httpServletResponse , filterChain , e);
        }

        assert jwtUserInfo != null;
        if (jwtUserInfo.getEmail() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserSecurityDetails userSecurityDetails = (UserSecurityDetails) customUserDetailsService.loadUserByUsername(jwtUserInfo.getEmail());
                if (tokenProvider.isTokenValid(jwtToken, userSecurityDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userSecurityDetails, jwtToken, userSecurityDetails.getGrantedAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (UsernameNotFoundException e) {
                throwErrors(httpServletRequest , httpServletResponse , filterChain , e);
                e.printStackTrace();
            }
        }
    }
    public void throwErrors(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain , Exception e) throws IOException, ServletException {
        TokenException exception = new TokenException(e.getMessage());

        // status
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");

        // set a new response object as a json one
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString("Token is invalid please provide a valid token"));
        System.out.println(response.getWriter().toString());
        // exit the filter chain
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
