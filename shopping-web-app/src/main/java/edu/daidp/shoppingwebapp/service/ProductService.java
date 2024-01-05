package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.Product;
import edu.daidp.shoppingwebapp.repository.ProductRepository;
import edu.daidp.shoppingwebapp.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    public Optional<Page<ProductDto>> findAll(Integer pageNumber, Integer pageSize) {
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNumber, pageSize));

        if (!page.isEmpty()) {
            page = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
            Page<ProductDto> pageDto = page.map(productMapper::toDto);
            return Optional.of(pageDto);
        }
        return Optional.empty();
    }
}
