package com.simple.restapi.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private String keyword;
    private String secondKeyword;
    private String sortType;
}
