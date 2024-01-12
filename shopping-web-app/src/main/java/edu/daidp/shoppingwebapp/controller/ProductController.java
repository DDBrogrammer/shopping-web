package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import edu.daidp.shoppingwebapp.common.exception.ApplicationResponse;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.dto.ProductDto;
import edu.daidp.shoppingwebapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ApplicationResponse<Page<ProductDto>>> retrieveProducts(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        return productService.findAll(pageNumber, pageSize).map(cars -> ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<Page<ProductDto>>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                               COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                               cars, List.of()

                ))).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApplicationResponse<ProductDto>> retrieveProduct(
            @PathVariable Long productId
    ) throws NoContentFoundException {
        return
                ResponseEntity.ok(new ApplicationResponse<ProductDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                                      COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                                      productService.findProductById(productId),
                                                                      List.of()));
    }

    @PostMapping()
    public ResponseEntity<ApplicationResponse<ProductDto>> saveProduct(@RequestBody ProductDto productDto) throws
            NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<ProductDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         productService.save(productDto), List.of()));
    }

    @PutMapping()
    public ResponseEntity<ApplicationResponse<ProductDto>> editProduct(@RequestBody ProductDto productDto) throws
            NoContentFoundException {
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<ProductDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                         COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                         productService.save(productDto), List.of()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApplicationResponse<String>> deleteProduct(@PathVariable Long productId) throws
            NoContentFoundException {

        return ResponseEntity.status(HttpStatus.OK).
                body(new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                     COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                     productService.deleteProduct(
                                                             productId) ? "DELETE SUCCESS" : "DELETE FAILED"
                        , List.of()));
    }
}
