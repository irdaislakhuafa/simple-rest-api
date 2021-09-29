package com.simple.restapi.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto extends UserDtoUpdate {
    @NotEmpty(message = "Access level is required")
    private String accessLevel;
}
