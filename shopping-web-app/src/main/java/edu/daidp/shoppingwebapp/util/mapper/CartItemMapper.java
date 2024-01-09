package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.CartDto;
import edu.daidp.shoppingwebapp.dto.CartItemDto;
import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.CartItem;
import edu.daidp.shoppingwebapp.entity.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {


    private final ModelMapper modelMapper;

    private final ProductMapper productMapper;

    public CartItemMapper(final ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public CartItemDto toDto(CartItem cartItem) {
        TypeMap<CartItem,CartItemDto > typeMap;

        typeMap=modelMapper.getTypeMap(CartItem.class,
                                       CartItemDto.class);
        if (typeMap==null){
            typeMap = modelMapper.createTypeMap(CartItem.class,
                                                CartItemDto.class);
        }
       // typeMap.addMappings(mapper ->mapper.skip(CartItemDto::setProduct));
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        cartItemDto.setProduct(productMapper.toDto(cartItem.getProduct()));
        return cartItemDto;
    }

    public CartItem toEntity(CartItemDto productDto) {
        TypeMap<CartItemDto,CartItem > typeMap;
        typeMap=modelMapper.getTypeMap(CartItemDto.class,
                                       CartItem.class);
        if (typeMap==null){
            typeMap = modelMapper.createTypeMap(CartItemDto.class,
                                                CartItem.class);
        }

        CartItem cartItem = modelMapper.map(productDto, CartItem.class);
        cartItem.setProduct(productMapper.toEntity(productDto.getProduct()));
        return cartItem;
    }
}
