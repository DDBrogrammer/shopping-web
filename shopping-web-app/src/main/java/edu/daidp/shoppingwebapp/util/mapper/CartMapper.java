package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {


    private final ModelMapper modelMapper;

    public CartMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDto toDto(Cart cart) {

        return modelMapper.map(cart, CartDto.class);
    }

    public Cart toEntity(CartDto cartDto) {

        return modelMapper.map(cartDto, Cart.class);
    }
}
