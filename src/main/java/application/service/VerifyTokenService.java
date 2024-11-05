package application.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VerifyTokenService {

    @Autowired
    private TokenService tokenService;

    public String verifyToken(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue(); // Retorna o valor do cookie se o nome for "token"
                }
            }
        }

        if (token != null && tokenService.isTokenValid(token)) {
            return "Token válido!";

        } else {
            System.out.println("Token inválido!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido!");
        }
    }
}
