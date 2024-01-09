package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.dto.CartItemDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.CartItem;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CartMapper {


    private final ModelMapper modelMapper;
    
    private final CartItemMapper cartItemMapper;

    public CartMapper(final ModelMapper modelMapper, CartItemMapper cartItemMapper) {
        this.modelMapper = modelMapper;
        this.cartItemMapper = cartItemMapper;
    }

    public CartDto toDto(Cart cart) {
        TypeMap<Cart, CartDto> typeMap;
        try {
            typeMap = modelMapper.createTypeMap(Cart.class,
                                                CartDto.class);
        }catch (IllegalStateException e){
            typeMap=modelMapper.getTypeMap(Cart.class,
                                           CartDto.class);
        }
        typeMap.addMappings(mapper ->mapper.skip(CartDto::setCartItems));
        CartDto cartDto=modelMapper.map(cart, CartDto.class);
        Set<CartItemDto> targetSet = cart.getCartItems().stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
        cartDto.setCartItems(targetSet);
        return cartDto;
    }

    public Cart toEntity(CartDto cartDto) {

        return modelMapper.map(cartDto, Cart.class);
    }
}
