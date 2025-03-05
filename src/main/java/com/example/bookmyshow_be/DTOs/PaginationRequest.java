package com.example.bookmyshow_be.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    private int page = 0;
    private int pageSize = 10;
    private String sortField = "updatedAt";
    private String sortOrder = "desc";
}
