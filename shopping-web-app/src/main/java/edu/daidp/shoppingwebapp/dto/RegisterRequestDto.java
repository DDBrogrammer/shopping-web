package edu.daidp.shoppingwebapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "Email can't be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Wrong email format")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotBlank(message = "Phone can't be blank")
    @Pattern(regexp = "^\\d{9,11}$", message = "Wrong phone format")
    private String phoneNo;

    @Pattern(regexp = "^(CUSTOMER|ADMIN)$")
    private String role;

    private String firstName;
    private String middleName;
    private String lastName;
}
