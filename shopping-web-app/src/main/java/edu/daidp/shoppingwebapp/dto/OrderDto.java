package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private UserDto user;

    private String promo;

    private Set<OrderItemDto> orderItems;

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
