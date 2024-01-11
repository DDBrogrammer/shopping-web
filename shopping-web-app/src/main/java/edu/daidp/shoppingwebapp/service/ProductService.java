package edu.daidp.shoppingwebapp.service;

import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.Product;
import edu.daidp.shoppingwebapp.repository.PhotoRepository;
import edu.daidp.shoppingwebapp.repository.ProductRepository;
import edu.daidp.shoppingwebapp.repository.VideoRepository;
import edu.daidp.shoppingwebapp.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final PhotoRepository photoRepository;

    private final VideoRepository videoRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, PhotoRepository photoRepository,
                          VideoRepository videoRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.photoRepository = photoRepository;
        this.videoRepository = videoRepository;
        this.productMapper = productMapper;
    }


    public Optional<Page<ProductDto>> findAll(Integer pageNumber, Integer pageSize) {
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        if (!page.isEmpty()) {
            page.getContent().forEach(product -> {
                product.setPhotos(photoRepository.findPhotosByProduct(product));
                product.setVideos(videoRepository.findVideosByProduct(product));
            });
            Page<ProductDto> pageDto = page.map(productMapper::toDto);
            return Optional.of(pageDto);
        }
        return Optional.empty();
    }

    public ProductDto save(ProductDto productDto) throws NoContentFoundException {
        if (productDto.getId() == null) {
            return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));

        }
        Optional<Product> productOptional = productRepository.findById(productDto.getId());
        if (productOptional.isPresent()) {
            Product tempProduct = getProduct(productDto, productOptional);
            return productMapper.toDto(productRepository.save(tempProduct));
        }
        throw new NoContentFoundException("No product found with Id: " + productDto.getId().toString());
    }

    private static Product getProduct(ProductDto productDto, Optional<Product> productOptional) {
        Product tempProduct = productOptional.get();
        tempProduct.setTittle(productDto.getTittle());
        tempProduct.setSlug(productDto.getSlug());
        tempProduct.setSummary(productDto.getSummary());
        tempProduct.setSku(productDto.getSku());
        tempProduct.setPrice(productDto.getPrice());
        tempProduct.setDiscount(productDto.getDiscount());
        tempProduct.setQuantity(productDto.getQuantity());
        tempProduct.setOrigin(productDto.getOrigin());
        return tempProduct;
    }

    public ProductDto deleteProduct(Long productId) throws NoContentFoundException {
        Optional<Product> productOptional = productRepository.findById(BigInteger.valueOf(productId));
        if (productOptional.isEmpty()) {
            throw new NoContentFoundException("Product with id" + productId + " not exist");
        }
        productRepository.delete(productOptional.get());
        return productMapper.toDto(productOptional.get());
    }
}
