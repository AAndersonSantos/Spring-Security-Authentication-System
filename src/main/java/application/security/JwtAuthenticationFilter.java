package application.security;

import application.service.TokenService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.getToken(request);

        if (token != null) {

            try {
                if (tokenService.isTokenValid(token)) {
                    String userEmail = tokenService.extractUserEmail(token);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userEmail, null, null);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Não Autorizado!");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        // Verifica se há cookies na solicitação
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue(); // Retorna o valor do cookie se o nome for "token"
                }
            }
        }
        return null; // Retorna null se o cookie "token" não foi encontrado
    }

    /*private String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null) {
            return null;
        }

        return this.replaceBearer(authHeader);
    }*/

    /*private String replaceBearer(String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        };

        return null;
    };*/
}
