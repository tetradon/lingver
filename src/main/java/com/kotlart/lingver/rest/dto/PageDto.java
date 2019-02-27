package com.kotlart.lingver.rest.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PageDto {
    private int size = 10;
    private int page = 0;
    private String sortField = "id";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
}
