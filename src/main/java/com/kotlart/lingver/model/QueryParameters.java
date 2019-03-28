package com.kotlart.lingver.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class QueryParameters {
    private int size = 10;
    private int page = 0;
    private String sortField = "insert_date";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String search = "";
}
