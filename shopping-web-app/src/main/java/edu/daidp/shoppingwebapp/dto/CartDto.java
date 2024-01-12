package edu.daidp.shoppingwebapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private BigInteger id;

    private Set<CartItemDto> cartItems;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong subTotal format")
    private BigDecimal subTotal;

    private Timestamp createAt;

    private Timestamp updateAt;
}
