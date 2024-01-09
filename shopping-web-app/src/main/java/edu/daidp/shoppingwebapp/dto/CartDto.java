package edu.daidp.shoppingwebapp.dto;

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

    private BigDecimal subTotal;

    private Timestamp createAt;

    private Timestamp updateAt;
}
