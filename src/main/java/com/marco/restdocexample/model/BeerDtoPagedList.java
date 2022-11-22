package com.marco.restdocexample.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerDtoPagedList extends PageImpl<BeerDto> {
    public BeerDtoPagedList(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerDtoPagedList(List<BeerDto> content) {
        super(content);
    }

}
