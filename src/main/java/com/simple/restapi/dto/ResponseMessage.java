package com.simple.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseMessage <T>{
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private T data;
}
