package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.constant.OrderStatus;
import edu.daidp.shoppingwebapp.dto.OrderDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.Order;
import edu.daidp.shoppingwebapp.entity.OrderItem;
import edu.daidp.shoppingwebapp.entity.User;
import edu.daidp.shoppingwebapp.repository.CartItemRepository;
import edu.daidp.shoppingwebapp.repository.CartRepository;
import edu.daidp.shoppingwebapp.repository.OrderRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import edu.daidp.shoppingwebapp.util.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderMapper orderMapper;

    public OrderService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                        OrderRepository orderRepository, UserRepository userRepository,
                        OrderMapper orderMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDto confirmOrder(String userEmail) {
        Order order = convertCartToOrder(userEmail);
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        User user = userRepository.findByEmail(userEmail).get();
        Cart cart = cartRepository.findCartByUser(user).get();
        // clear cart
        cart.setSubTotal(BigDecimal.ZERO);
        cart.setCartItems(new HashSet<>());
        cartItemRepository.deleteAllByCart(cart);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public OrderDto getOrder(String userEmail) {
        Order order = convertCartToOrder(userEmail);
        return orderMapper.toDto(order);
    }

    public Order convertCartToOrder(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        Cart cart = cartRepository.findCartByUser(user).get();
        BigDecimal taxFee = cart.getSubTotal().multiply(
                BigDecimal.valueOf(0.1d));
        BigDecimal subTotal = cart.getSubTotal();
        BigDecimal discount = subTotal.compareTo(BigDecimal.valueOf(1000000)) == 1 ? BigDecimal.valueOf(
                100000) : BigDecimal.ZERO;
        BigDecimal shippingFee = BigDecimal.valueOf(35000);
        BigDecimal grandTotal = subTotal.add(shippingFee).add(taxFee).subtract(discount);
        Order order = Order.builder().shippingFee(BigDecimal.valueOf(30000))
                .taxFee(taxFee)
                .shippingFee(shippingFee)
                .discount(discount)
                .subTotal(subTotal)
                .grandTotal(grandTotal)
                .createAt(Timestamp.valueOf(LocalDateTime.now()))
                .orderItems(cart.getCartItems().stream().map(cartItem -> OrderItem.builder()
                        .product(cartItem.getProduct())
                        .price(cartItem.getPrice())
                        .quantity(cartItem.getQuantity())
                        .discount(cartItem.getDiscount())
                        .sku(cartItem.getProduct().getSku())
                        .build()).collect(
                        Collectors.toSet())).orderStatus(OrderStatus.OPEN).build();
        order.setUser(user);
        return order;
    }


}
