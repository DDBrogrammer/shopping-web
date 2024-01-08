package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private BigInteger id;

    private ProductDto product;

    private BigDecimal price;

    private BigDecimal discount;

    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;
}
