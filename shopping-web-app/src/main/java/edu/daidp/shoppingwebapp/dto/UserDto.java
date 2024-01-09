package edu.daidp.shoppingwebapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @NotBlank(message = "firstName can't be blank")
    @Column(nullable = false)
    private  String firstName;

    @NotBlank(message = "middleName can't be blank")
    @Column(nullable = false)
    private  String middleName;

    @NotBlank(message = "lastName can't be blank")
    @Column(nullable = false)
    private  String lastName;

    @NotBlank(message = "phone number can't be blank")
    @Column(unique = true,nullable = false)
    private  String phoneNo;

    @NotBlank(message = "email can't be blank")
    @Column(name = "email",unique = true,nullable = false)
    private  String email;

    @NotBlank(message = "password can't be blank")
    @Column(name = "password",nullable = false)
    private  String password;
}
