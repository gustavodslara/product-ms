package com.github.gustavodslara.compasso.productms.application.service;

import com.github.gustavodslara.compasso.productms.adapters.dto.ProductDto;
import com.github.gustavodslara.compasso.productms.application.port.api.ProductServicePort;
import com.github.gustavodslara.compasso.productms.application.port.spi.ProductRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductServicePort {

    @Autowired
    private ProductRepositoryPort productRepositoryPort;

    public ProductServiceImpl(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return productRepositoryPort.addProduct(product);
    }

    @Override
    public Boolean deleteProductById(String id) {
        var optionalProduct = getProductById(id);
        if (optionalProduct.isPresent()) {
            productRepositoryPort.deleteProductById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ProductDto> updateProductById(String id, ProductDto product) {
        return productRepositoryPort.updateProductById(id, product);
    }

    @Override
    public Optional<ProductDto> getProductById(String id) {
        return productRepositoryPort.getProductById(id);
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepositoryPort.getProducts();
    }

    @Override
    public List<ProductDto> getProductsByQuery(String q, BigDecimal min, BigDecimal max) {
        return productRepositoryPort.getProductsByQuery(q, min, max);
    }
}
