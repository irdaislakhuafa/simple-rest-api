package com.simple.restapi.helpers;

import com.simple.restapi.dto.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    private List<String> messages = new ArrayList<>();

    public ResponseEntity<?> idNotFound(Long id) {
        this.messages.add("Data with ID: " + id + " not found");
        return new ResponseEntity<>(this, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> idNotFound(Long id, ResponseMessage<?> response) {

        response.getMessages().add("Data with ID: " + id + " not found");
        response.setStatus(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> idNotFound(String field, Long id) {
        this.messages.add("Data " + field + " with ID: " + id + " not found");
        return new ResponseEntity<>(this, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> uknownError() {
        this.messages.add("Unknown error, please contact admin");
        return new ResponseEntity<>(this, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> success() {
        this.messages.add("Success");
        return ResponseEntity.ok(this);
    }

    public ResponseEntity<?> nameNotFound(String className, String name) {
        this.messages.add("Data " + className + " with name (" + name + ") not found");
        return new ResponseEntity<>(this, HttpStatus.NOT_FOUND);
    }
}
