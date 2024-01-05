package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    private final ModelMapper modelMapper;

    public ProductMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toDto(Product product) {

        return modelMapper.map(product, ProductDto.class);
    }

    public Product toEntity(ProductDto productDto) {

        return modelMapper.map(productDto, Product.class);
    }
}
