package com.github.gustavodslara.compasso.productms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavodslara.compasso.productms.adapters.dto.ProductDto;
import com.github.gustavodslara.compasso.productms.adapters.inbound.resource.ProductResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductResource.class)
@EnableWebMvc
public class ProductResourceIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductResource productResource;

    private ProductDto invalidProductDto;
    private ProductDto validProductDto;

    @BeforeEach
    public void setup() {
        invalidProductDto = new ProductDto();
        invalidProductDto.setName("invalid");
        invalidProductDto.setPrice(BigDecimal.ZERO);

        validProductDto = new ProductDto();
        validProductDto.setName("valid");
        validProductDto.setDescription("valid test");
        validProductDto.setPrice(BigDecimal.TEN);
    }

    @Test
    public void resourceInitialized() {
        assertNotNull(productResource);
    }

    @Test
    public void productNotValidReturn400() throws Exception {
        var body = objectMapper.writeValueAsString(invalidProductDto);
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status_code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void productValidReturn201() throws Exception {
        var body = objectMapper.writeValueAsString(validProductDto);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("valid"))
                .andExpect(jsonPath("$.description").value("valid test"))
                .andExpect(jsonPath("$.price").value(BigDecimal.TEN))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo((value) -> {
                    validProductDto = objectMapper.readValue(value.getResponse().getContentAsString(), ProductDto.class);
                });
    }

    @Test
    public void getProductThatDoesntExistReturn404() throws Exception {
        mockMvc.perform(get("/products/invalid-uuid")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void getProductThatExistReturn200() throws Exception {
        var body = objectMapper.writeValueAsString(validProductDto);
        mockMvc.perform(get("/products/" + validProductDto.getId())
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(validProductDto.getName()))
                .andExpect(jsonPath("$.description").value(validProductDto.getDescription()))
                .andExpect(jsonPath("$.price").value(validProductDto.getPrice()))
                .andExpect(jsonPath("$.id").value(validProductDto.getId()));
    }
}
