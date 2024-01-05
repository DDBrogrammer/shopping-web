package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "cartItem")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private BigDecimal price;

    private BigDecimal discount;

    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;

}
