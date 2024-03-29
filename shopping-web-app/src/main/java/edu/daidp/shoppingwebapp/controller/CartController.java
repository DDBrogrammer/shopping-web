package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import edu.daidp.shoppingwebapp.common.exception.ApplicationResponse;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.common.meta_anotaion.IsCustomer;
import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    final
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    //@PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @IsCustomer
    public ResponseEntity<ApplicationResponse<CartDto>> retrieveUserCart() throws NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApplicationResponse<CartDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         cartService.getCartByUserEmail(
                                                                 SecurityContextHolder.getContext().getAuthentication()
                                                                         .getName()),
                                                         List.of()
                        ));
    }

    @PostMapping("/cart")
    //@PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @IsCustomer
    public ResponseEntity<ApplicationResponse<CartDto>> updateCart(@RequestBody CartDto cartDto) throws
            NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApplicationResponse<CartDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         cartService.updateCart(cartDto
                                                                 ,
                                                                                SecurityContextHolder.getContext()
                                                                                        .getAuthentication()
                                                                                        .getName()),
                                                         List.of()
                        ));
    }
}
