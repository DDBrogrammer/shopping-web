package edu.daidp.shoppingwebapp.util.mapper;

import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper {


    private final ModelMapper modelMapper;

    private final VideoMapper videoMapper;

    private final PhotoMapper photoMapper;

    public ProductMapper(final ModelMapper modelMapper, VideoMapper videoMapper, PhotoMapper photoMapper) {
        this.modelMapper = modelMapper;
        this.videoMapper = videoMapper;
        this.photoMapper = photoMapper;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setVideos(product.getVideos().stream().map(videoMapper::toDto).collect(
                Collectors.toSet()));
        productDto.setPhotos(product.getPhotos().stream().map(photoMapper::toDto).collect(
                Collectors.toSet()));
        return  productDto;
    }

    public Product toEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setVideos(Set.of());
        product.setPhotos(Set.of());
        return  product;
    }
}
