package com.simple.restapi.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search extends SearchOnly{
    private String secondKeyword;
    private RequestData requestData;
    private Sort sort;
}
