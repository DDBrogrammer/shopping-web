package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "user_order")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId",nullable = true)
    private Address address;

    @OneToMany(mappedBy="order",fetch=FetchType.LAZY)
    private Set<OrderItem> orderItems;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String promo;

    private BigDecimal subTotal;

    // The total discount of the Order based on the promo code or store discount.
    private BigDecimal discount;

    // The tax on the Order Items.
    private BigDecimal taxFee;

    private BigDecimal shippingFee;

    //	The grand total of the order to be paid by the buyer.
    private BigDecimal grandTotal;

    private OrderStatus orderStatus;

    private Timestamp createAt;

    private Timestamp updateAt;
}
