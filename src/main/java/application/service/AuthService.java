package application.service;

import application.controllerAdvice.EmailAlreadyRegisteredException;
import application.dtos.AuthResponseDto;
import application.dtos.LoginRequestDto;
import application.model.User;
import application.repository.UserRepository;
import com.auth0.jwt.JWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public void register(User userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email já registrado!");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas!");
        }

        String successMessage = "Login realizado com sucesso!";
        String token = tokenService.generateToken(user.getEmail());

        // Configure o cookie com o token JWT
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // Torna o cookie inacessível a scripts de JavaScript
        cookie.setSecure(false);   // Envia o cookie apenas via HTTPS ?
        cookie.setPath("/");      // Define o path do cookie
        cookie.setMaxAge(10 * 60 * 60); // Define o tempo de expiração do cookie (10 hora)

        response.addCookie(cookie);

        return new AuthResponseDto(successMessage, null);
    }
}
