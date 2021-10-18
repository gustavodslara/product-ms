package com.github.gustavodslara.compasso.productms.application.port.api;

import com.github.gustavodslara.compasso.productms.adapters.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductServicePort {

    ProductDto addProduct(ProductDto product);
    Boolean deleteProductById(String id);
    Optional<ProductDto> updateProductById(String id, ProductDto product);
    Optional<ProductDto> getProductById(String id);
    List<ProductDto> getProducts();
    List<ProductDto> getProductsByQuery(String q, BigDecimal min, BigDecimal max);

}
