package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.common.exception.ProductOutOfStockException;
import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.CartItem;
import edu.daidp.shoppingwebapp.repository.CartRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import edu.daidp.shoppingwebapp.util.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final UserRepository userRepository;


    private final CartMapper cartMapper;
    public CartService(CartRepository cartRepository, UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

   public CartDto getCartByUserEmail(String userEmail) throws NoContentFoundException {
      Optional<Cart> cart= cartRepository.getCartByUser(userRepository.findTopByEmail(userEmail));
      if (cart.isPresent()){
          return cartMapper.toDto(cart.get());
      }
      throw new NoContentFoundException("no cart found");
    }

    public CartDto updateCart(CartDto newCartDto,String userEmail) throws NoContentFoundException,ProductOutOfStockException {
        Optional<Cart> cartSaved= cartRepository.getCartByUser(userRepository.findTopByEmail(userEmail));
        if (cartSaved.isPresent()){
            List<CartItem> itemsToRemove = new ArrayList<>();
            Cart oldCart=cartSaved.get();

            // Update existing items and mark items for removal
            for (CartItem oldItem : oldCart.getCartItems()) {
                boolean found = false;
                for (CartItem newItem : newCartDto.getCartItems()) {
                    if (newItem.getId().equals(oldItem.getId())) {
                        // If the item already exists in the new cart, update quantity or any other relevant fields
                        oldItem.setQuantity( newItem.getQuantity());
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
            for (CartItem newItem : newCartDto.getCartItems()) {
                boolean found = false;
                for (CartItem oldItem : oldCart.getCartItems()) {
                    if (newItem.getId().equals(oldItem.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // If the item does not exist in the old cart, add it
                   oldCart.getCartItems().add(newItem);
                }
            }
            // Add new items to the old cart
           return cartMapper.toDto( cartRepository.save(oldCart));
        }
        throw new NoContentFoundException("no cart found");
    }
}
