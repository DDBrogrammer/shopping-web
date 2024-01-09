package edu.daidp.shoppingwebapp.common.validate;

import edu.daidp.shoppingwebapp.common.exception.DuplicatedDataException;
import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartValidator {

 public boolean validateCartItem(CartDto cart){
      List<ProductDto> products=new ArrayList<>();
      cart.getCartItems().stream().forEach(cartItemDto -> {
          if(products.stream().anyMatch(product -> product.getId().equals(cartItemDto.getProduct().getId()))){
              throw new DuplicatedDataException("Duplicated cart item product");
          }else {
              products.add(cartItemDto.getProduct());
          } });
      return true;
    }
}
