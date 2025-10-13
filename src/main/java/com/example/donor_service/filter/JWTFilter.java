package com.example.donor_service.filter;

import com.example.donor_service.config.BloodBankUserDetailsService;
import com.example.donor_service.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final BloodBankUserDetailsService userDetailsService;
    private final JWTService jwtService;

    /**
     * This filter checks whether the given JWT token is valid or invalid.
     * If it is valid, the request proceeds to further authentication.
     * Otherwise, the request is forwarded, and it fails during the UsernamePasswordAuthenticationFilter.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            token = headerValue.substring(7);
            try {
                username = jwtService.extractUsername(token);
            }
            catch (Exception ex){
                logger.error("Failed to extract username");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                logger.info("invalid token");
            }
        }
        filterChain.doFilter(request,response);
    }
}
