package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.CartItemDto;
import edu.daidp.shoppingwebapp.entity.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {


    private final ModelMapper modelMapper;

    public CartItemMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemDto toDto(CartItem product) {

        return modelMapper.map(product, CartItemDto.class);
    }

    public CartItem toEntity(CartItemDto productDto) {

        return modelMapper.map(productDto, CartItem.class);
    }
}
