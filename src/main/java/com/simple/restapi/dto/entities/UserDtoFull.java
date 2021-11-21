package com.simple.restapi.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoFull extends UserDto{
    @NotNull(message = "Id is required for update")
    private Long id;
}
