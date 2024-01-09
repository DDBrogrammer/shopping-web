package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.OrderItemDto;
import edu.daidp.shoppingwebapp.entity.OrderItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

   private final ModelMapper modelMapper;

    private final ProductMapper productMapper;

    public OrderItemMapper(final ModelMapper modelMapper, ProductMapper productMapper) {
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public OrderItemDto toDto(OrderItem orderItem) {
        TypeMap<OrderItem,OrderItemDto > typeMap;

        typeMap=modelMapper.getTypeMap(OrderItem.class,
                                       OrderItemDto.class);
        if (typeMap==null){
            typeMap = modelMapper.createTypeMap(OrderItem.class,
                                                OrderItemDto.class);
        }
//        typeMap.addMappings(mapper ->mapper.skip(OrderItemDto::setOrder));

        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);
        orderItemDto.setOrder(null);
        orderItemDto.setProduct(productMapper.toDto(orderItem.getProduct()));
        return orderItemDto;
    }

    public OrderItem toEntity(OrderItemDto orderDto) {
        TypeMap<OrderItemDto,OrderItem > typeMap;
        typeMap=modelMapper.getTypeMap(OrderItemDto.class,
                                       OrderItem.class);
        if (typeMap==null){
            typeMap = modelMapper.createTypeMap(OrderItemDto.class,
                                                OrderItem.class);
        }
        typeMap.addMappings(mapper ->mapper.skip(OrderItem::setOrder));
        OrderItem orderItem = modelMapper.map(orderDto, OrderItem.class);
        orderItem.setOrder(null);
        return orderItem;
    }
}
