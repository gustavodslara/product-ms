package com.github.gustavodslara.compasso.productms.adapters.outbound.persistence;

import com.github.gustavodslara.compasso.productms.adapters.dto.ProductDto;
import com.github.gustavodslara.compasso.productms.adapters.outbound.persistence.entity.ProductEntity;
import com.github.gustavodslara.compasso.productms.adapters.outbound.persistence.repository.ProductRepository;
import com.github.gustavodslara.compasso.productms.application.port.spi.ProductRepositoryPort;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductRepositoryImpl implements ProductRepositoryPort {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto product) {
        var entity = productRepository.save(
                modelMapper.map(product, ProductEntity.class)
        );
        return modelMapper.map(entity, ProductDto.class);
    }

    @Override
    public Boolean deleteProductById(String id) {
        var optionalProductEntity = getProductById(id);
        if (optionalProductEntity.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ProductDto> updateProductById(String id, ProductDto product) {
        var optionalProductEntity = productRepository.findById(id);
        if (optionalProductEntity.isPresent()) {
            var newProductEntity = modelMapper.map(product, ProductEntity.class);
            newProductEntity.setId(id);
            productRepository.save(newProductEntity);
            return Optional.of(modelMapper.map(newProductEntity, ProductDto.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductDto> getProductById(String id) {
        var entity = productRepository.findById(id);
        return entity.isPresent()
                ? Optional.of(modelMapper.map(entity.get(), ProductDto.class))
                : Optional.empty();
    }

    @Override
    public List<ProductDto> getProducts() {
        var productEntityList = productRepository.findAll();
        var listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        return modelMapper.map(productEntityList, listType);
    }

    @Override
    public List<ProductDto> getProductsByQuery(String q, BigDecimal min, BigDecimal max) {
        var productEntityList = productRepository.getProductsByQuery(entityManager, q, min, max);

        var listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        return modelMapper.map(productEntityList, listType);
    }

}
