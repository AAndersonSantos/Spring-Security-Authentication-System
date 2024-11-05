package application.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthResponseDto {

    private String message;
    private String token;

    public AuthResponseDto(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
