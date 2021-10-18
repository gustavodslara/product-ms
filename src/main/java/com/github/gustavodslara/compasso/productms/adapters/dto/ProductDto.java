package com.github.gustavodslara.compasso.productms.adapters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    @Digits(integer=9, fraction=2)
    private BigDecimal price;
}
