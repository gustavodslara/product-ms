package com.github.gustavodslara.compasso.productms.adapters.inbound.resource;

import com.github.gustavodslara.compasso.productms.adapters.dto.ProductDto;
import com.github.gustavodslara.compasso.productms.application.port.api.ProductServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductServicePort productServicePort;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productServicePort.addProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(required = true) String id, @RequestBody @Valid ProductDto bookDto) {
        var optionalUpdatedProduct = productServicePort.updateProductById(id, bookDto);
        return optionalUpdatedProduct.isPresent()
                ? new ResponseEntity<>(optionalUpdatedProduct.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable String id) {
        var optionalProduct = productServicePort.getProductById(id);
        return optionalProduct.isPresent()
                ? new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productServicePort.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> getProductsByQuery(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max
    ) {
        var products = productServicePort.getProductsByQuery(q, min, max);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable(required = false) String id) {
        boolean deleteSuccess = productServicePort.deleteProductById(id);
        return deleteSuccess
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


