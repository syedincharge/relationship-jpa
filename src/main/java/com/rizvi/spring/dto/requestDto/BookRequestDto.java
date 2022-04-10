package com.rizvi.spring.dto.requestDto;


import lombok.Data;

import java.util.List;

@Data
public class BookRequestDto {

    private String name;
    private List<Long> authorsId;
    private Long categoryId;


}
