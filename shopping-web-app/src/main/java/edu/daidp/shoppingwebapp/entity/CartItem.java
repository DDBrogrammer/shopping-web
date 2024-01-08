package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId", nullable = true)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private BigDecimal price;

    private BigDecimal discount;

    private BigInteger quantity;

    private Timestamp createAt;

    private Timestamp updateAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id)
                 &&  Objects.equals(price, cartItem.price) && Objects.equals(
                discount, cartItem.discount) && Objects.equals(quantity,
                                                               cartItem.quantity) && Objects.equals(
                createAt, cartItem.createAt) && Objects.equals(updateAt, cartItem.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  price, discount, quantity, createAt, updateAt);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
