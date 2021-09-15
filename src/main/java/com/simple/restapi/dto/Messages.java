package com.simple.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class Messages {
    private List<String> messages = new ArrayList<>();

    public ResponseEntity<?> idNotFound(Long id) {
        this.messages.add("Data with ID: " +  id + " not found");
        return new ResponseEntity<>(this.messages, HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<?> uknownError(){
        this.messages.add("Unknown error, please contact admin");
        return new ResponseEntity<>(this.messages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<?> succes(){
        this.messages.add("Success");
        return ResponseEntity.ok(this.messages);
    }
}
