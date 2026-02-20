package com.example.friendfinder.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Pagination {
    private int page;
    private int size;

  static public Pageable pageableMethod(int page, int size) {

if(page < 1) {
    page = 1;
}
if(size < 1) {
    size = 10;
}
        return PageRequest.of(page - 1, size);
}

    }

