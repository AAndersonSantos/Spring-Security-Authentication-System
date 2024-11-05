package application.controller;

import application.dtos.AuthResponseDto;
import application.dtos.LoginRequestDto;
import application.dtos.RegisterRequestDto;
import application.model.User;
import application.service.AuthService;
import application.service.LogoutService;
import application.service.VerifyTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private VerifyTokenService verifyTokenService;

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {

        User userRequest = modelMapper.map(registerRequestDto, User.class);
        authService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto,
                                                 HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequestDto, response));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(HttpServletRequest request) {
        String result = verifyTokenService.verifyToken(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        logoutService.logout(response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}