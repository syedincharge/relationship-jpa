package com.rizvi.spring.dto.responseDto;


import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {

    public Long id;
    private String name;
    private List<String> booksNames;
}
