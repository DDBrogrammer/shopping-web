package edu.daidp.shoppingwebapp.dto;

import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong price format")
    private BigDecimal price;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong discount format")
    private BigDecimal discount;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$",message="Wrong quantity format")
    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;
}
