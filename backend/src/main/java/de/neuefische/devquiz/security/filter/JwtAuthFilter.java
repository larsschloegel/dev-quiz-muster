package de.neuefische.devquiz.security.filter;

import de.neuefische.devquiz.security.service.JWTUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private JWTUtilService jwtUtils;

    public JwtAuthFilter(JWTUtilService jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Aus request kann Token ausgelesen werden
        String token = getAuthToken(request);

        log.info("received token: "+ token);

        try {
            if (token != null && !token.isBlank())  {
                String username = jwtUtils.extractUserName(token);
                setSecurityContext(username);
            }
        } catch (Exception e){
            throw new AccessDeniedException("No valid token! Access denied!", e);
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String username) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, "", List.of());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null){
            return authHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}
