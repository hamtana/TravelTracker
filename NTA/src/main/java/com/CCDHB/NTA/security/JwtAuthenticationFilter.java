package com.CCDHB.NTA.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;

    public JwtAuthenticationFilter(JWTVerifier jwtVerifier){
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            try {
                DecodedJWT jwt = jwtVerifier.verify(token);

                //Extract Clerk User ID (sub)
                String userId = jwt.getSubject();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null,
                                List.of(() -> "USER")); // temporary authority
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return ;
            }
        }
        filterChain.doFilter(request, response);
    }

}
