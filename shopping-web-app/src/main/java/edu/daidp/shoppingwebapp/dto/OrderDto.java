package edu.daidp.shoppingwebapp.dto;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$", message = "Wrong subTotal format")
    private BigDecimal subTotal;

    // The total discount of the Order based on the promo code or store discount.
    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$", message = "Wrong discount format")
    private BigDecimal discount;

    // The tax on the Order Items.
    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$", message = "Wrong taxFee format")
    private BigDecimal taxFee;

    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$", message = "Wrong shippingFee format")
    private BigDecimal shippingFee;

    //	The grand total of the order to be paid by the buyer.
    @Pattern(regexp = "^[0-9]*\\.?[0-9]+$", message = "Wrong grandTotal format")
    private BigDecimal grandTotal;

    @Pattern(regexp = "^(OPEN|AWAITING_FULFILLMENT|AWAITING_SHIPMENT|AWAITING_PAYMENT|SHIPPED|COMPLETED|CANCEL)$",
            message = "Wrong grandTotal format")
    private OrderStatus orderStatus;

    private Timestamp createAt;

    private Timestamp updateAt;
}
