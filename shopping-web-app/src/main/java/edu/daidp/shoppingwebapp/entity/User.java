package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {


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
    @Column(name = "phone_no",unique = true,nullable = false)
    private  String phoneNo;

    @NotBlank(message = "email can't be blank")
    @Column(name = "email",unique = true,nullable = false)
    private  String email;

    @NotBlank(message = "password can't be blank")
    @Column(name = "password",nullable = false)
    private  String password;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatus userStatus;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
