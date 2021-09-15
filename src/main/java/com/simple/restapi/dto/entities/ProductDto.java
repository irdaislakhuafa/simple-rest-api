package com.simple.restapi.dto.entities;

import com.simple.restapi.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    private Category category;

//    @NotBlank/@NotEmpty only for String field only
//    use @NotNull for integer/decimal
}
