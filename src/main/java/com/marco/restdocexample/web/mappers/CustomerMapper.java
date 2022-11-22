package com.marco.restdocexample.web.mappers;

import com.marco.restdocexample.domain.Customer;
import com.marco.restdocexample.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
