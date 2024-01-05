package edu.daidp.shoppingwebapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "cart")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private BigInteger id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy="cart",fetch=FetchType.LAZY)
    private Set<CartItem> cartItems;

    private BigDecimal subTotal;

    private Timestamp createAt;

    private Timestamp updateAt;

}
