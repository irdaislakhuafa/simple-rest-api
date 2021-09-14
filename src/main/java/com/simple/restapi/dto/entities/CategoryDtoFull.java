package com.simple.restapi.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDtoFull extends CategoryDto {
    @NotEmpty(message = "Id is required for update")
    private Long id;
}
