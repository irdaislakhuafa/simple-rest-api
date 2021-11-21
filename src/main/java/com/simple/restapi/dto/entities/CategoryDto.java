package com.simple.restapi.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {
    @NotEmpty(message = "Name is required")
    private String name;
}
