package com.github.gustavodslara.compasso.productms.adapters.configuration;

import com.github.gustavodslara.compasso.productms.ProductMsApplication;
import com.github.gustavodslara.compasso.productms.application.port.spi.ProductRepositoryPort;
import com.github.gustavodslara.compasso.productms.application.service.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ComponentScan(basePackageClasses = ProductMsApplication.class)
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    ProductServiceImpl productServiceImpl(ProductRepositoryPort repository) {
        return new ProductServiceImpl(repository);
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.github.gustavodslara.compasso")).build();
    }
}
