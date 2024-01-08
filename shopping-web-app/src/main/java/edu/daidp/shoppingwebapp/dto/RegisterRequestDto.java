package edu.daidp.shoppingwebapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
   private  String name;
  private String email;
  private String password;
  private String phoneNo;
  private String role;
    private String firstName;
    private String middleName;
    private String lastName;
}
