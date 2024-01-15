package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import edu.daidp.shoppingwebapp.common.exception.ApplicationResponse;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.common.meta_anotaion.IsCustomer;
import edu.daidp.shoppingwebapp.dto.OrderDto;
import edu.daidp.shoppingwebapp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@IsCustomer
public class OrderController {
    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    //@PreAuthorize("hasRole('ROLE_CUSTOMER')")
    //@IsCustomer
    public ResponseEntity<ApplicationResponse<OrderDto>> retrieveUserOrder(OrderDto orderDto) throws
            NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApplicationResponse<OrderDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                          COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                          orderService.getOrder(
                                                                  SecurityContextHolder.getContext()
                                                                          .getAuthentication()
                                                                          .getName()), List.of()
                        ));
    }

    @PostMapping("/order")
    //@PreAuthorize("hasRole('ROLE_CUSTOMER')")
    //@IsCustomer
    public ResponseEntity<ApplicationResponse<OrderDto>> confirmUserOrder(OrderDto orderDto) throws
            NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApplicationResponse<OrderDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                          COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                          orderService.confirmOrder(
                                                                  SecurityContextHolder.getContext()
                                                                          .getAuthentication()
                                                                          .getName()), List.of()

                        ));
    }
}
