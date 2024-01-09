package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    private String promo;

    private  String sku;

    private BigDecimal price;

    private BigDecimal discount;

    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;
}
