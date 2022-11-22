package com.marco.restdocexample.web.mappers;

import com.marco.restdocexample.domain.Beer;
import com.marco.restdocexample.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
