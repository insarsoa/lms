package my.project.lms.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.project.lms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);
                Optional<String> username = jwtUtil.extractUsername(jwt).describeConstable();
                username.ifPresent(s -> jwtUtil.validateToken(jwt, s));
            }else if(!request.getRequestURI().contains("login")){
                throw new Exception() {};
            }
            chain.doFilter(request, response);
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Error: Unauthorized - " + "Invalid JWT Token, Check Header Authorization token");
        return;
    }
    }
}
