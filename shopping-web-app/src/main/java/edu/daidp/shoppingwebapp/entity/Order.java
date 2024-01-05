package edu.daidp.shoppingwebapp.entity;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    private Address address;

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String promo;


    private BigDecimal subTotal;

    // The total discount of the Order Items.
    private Float itemDiscount;

    // The total discount of the Order based on the promo code or store discount.
    private BigDecimal discount;

    // The tax on the Order Items.
    private BigDecimal taxFee;

    private BigDecimal shippingFee;

    // 	The total price of the Order including tax and shipping. It excludes the items discount.
    private BigDecimal total;

    //	The grand total of the order to be paid by the buyer.
    private BigDecimal grandTotal;

    private OrderStatus orderStatus;

    private Timestamp createAt;

    private Timestamp updateAt;
}
