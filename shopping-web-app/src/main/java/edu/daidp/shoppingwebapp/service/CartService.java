package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.exception.DuplicatedDataException;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.common.exception.ProductOutOfStockException;
import edu.daidp.shoppingwebapp.util.validate.CartValidator;
import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.dto.CartItemDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.CartItem;
import edu.daidp.shoppingwebapp.repository.CartRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import edu.daidp.shoppingwebapp.util.mapper.CartItemMapper;
import edu.daidp.shoppingwebapp.util.mapper.CartMapper;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final UserRepository userRepository;


    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    private final CartValidator cartValidator;

    public CartService(CartRepository cartRepository, UserRepository userRepository, CartMapper cartMapper,
                       CartItemMapper cartItemMapper, CartValidator cartValidator) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
        this.cartValidator = cartValidator;
    }
    @RolesAllowed("ROLE_CUSTOMER")
    public CartDto getCartByUserEmail(String userEmail) throws NoContentFoundException {
        Optional<Cart> cart = cartRepository.findCartByUser(userRepository.findByEmail(userEmail).get());
        List<Cart> carts = cartRepository.findAll();
        if (cart.isPresent()) {
            return cartMapper.toDto(cart.get());
        }
        throw new NoContentFoundException("no cart found");
    }
    @RolesAllowed("ROLE_CUSTOMER")
    public CartDto updateCart(CartDto newCartDto, String userEmail) throws NoContentFoundException,
            ProductOutOfStockException, DuplicatedDataException {
         if ( cartValidator.validateCartItem(newCartDto)){
             Optional<Cart> cartSaved = cartRepository.findCartByUser(userRepository.findTopByEmail(userEmail));
             if (cartSaved.isPresent()) {
                 List<CartItem> itemsToRemove = new ArrayList<>();
                 Cart oldCart = cartSaved.get();

                 // Update existing items and mark items for removal
                 for (CartItem oldItem : oldCart.getCartItems()) {
                     boolean found = false;
                     for (CartItemDto newItem : newCartDto.getCartItems()) {
                         if (newItem.getId().equals(oldItem.getId())) {
                             // If the item already exists in the new cart, update quantity or any other relevant fields
                             oldItem.setQuantity(newItem.getQuantity());
                             oldItem.setPrice(newItem.getProduct().getPrice());
                             oldItem.setDiscount(BigDecimal.valueOf(newItem.getProduct().getDiscount()));
                             found = true;
                             break;
                         }
                     }
                     if (!found) {
                         // Item exists in old cart but not in new cart, mark it for removal
                         itemsToRemove.add(oldItem);
                     }
                 }
                 // Remove items that are not present in the new cart
                 itemsToRemove.forEach(oldCart.getCartItems()::remove);
                 // Add new items to the old cart
                 for (CartItemDto newItem : newCartDto.getCartItems()) {
                     boolean found = false;
                     for (CartItem oldItem : oldCart.getCartItems()) {
                         if (newItem.getId().equals(oldItem.getId())) {
                             found = true;
                             break;
                         }
                     }
                     if (!found) {
                         // If the item does not exist in the old cart, add it
                         oldCart.getCartItems().add(cartItemMapper.toEntity(newItem));
                     }
                 }

                 // update subtotal
                 BigDecimal subTotal = BigDecimal.ZERO;
                 for (CartItem cartItem : oldCart.getCartItems()) {
                     System.out.println(BigDecimal.valueOf(cartItem.getQuantity().intValue()));
                     System.out.println(cartItem.getPrice());
                     BigDecimal itemPrice = cartItem.getPrice().subtract(cartItem.getPrice().multiply(
                             cartItem.getDiscount() == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(
                                     cartItem.getDiscount().doubleValue() / 100)));
                     subTotal = subTotal.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity().intValue())));
                 }
                 oldCart.setSubTotal(subTotal);
                 return cartMapper.toDto(cartRepository.save(oldCart));
             }

         }
        throw new NoContentFoundException("no cart found");
    }
}
