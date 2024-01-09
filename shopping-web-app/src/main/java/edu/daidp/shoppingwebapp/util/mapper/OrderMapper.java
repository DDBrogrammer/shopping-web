package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.OrderDto;
import edu.daidp.shoppingwebapp.dto.OrderItemDto;
import edu.daidp.shoppingwebapp.entity.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;
    private final ModelMapper modelMapper;
    public OrderMapper(final ModelMapper modelMapper, OrderItemMapper orderItemMapper) {
        this.modelMapper = modelMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDto toDto(Order order) {
        TypeMap<Order, OrderDto> typeMap;
        try {
            typeMap = modelMapper.createTypeMap(Order.class,
                                                OrderDto.class);
        }catch (IllegalStateException e){
            typeMap=modelMapper.getTypeMap(Order.class,
                                           OrderDto.class);
        }
        typeMap.addMappings(mapper ->mapper.skip(OrderDto::setOrderItems));
        OrderDto orderDto=modelMapper.map(order, OrderDto.class);
        Set<OrderItemDto> targetSet = order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
        orderDto.setOrderItems(targetSet);
        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {

        return modelMapper.map(orderDto, Order.class);
    }
}
