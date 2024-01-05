package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.entity.Product;
import edu.daidp.shoppingwebapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<Page<ProductDto>> retrieveCars(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return productService.findAll(pageNumber, pageSize).map(cars -> ResponseEntity.status(HttpStatus.OK).
                body(cars)).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
    }
}
