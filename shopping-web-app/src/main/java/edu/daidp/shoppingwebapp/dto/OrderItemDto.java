package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OrderItemDto {

    private Long id;

    private OrderDto order;

    private ProductDto product;

    private String promo;

    private String sku;

    private BigDecimal price;

    private BigDecimal discount;

    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;
}
