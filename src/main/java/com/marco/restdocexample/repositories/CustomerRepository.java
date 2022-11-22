package com.marco.restdocexample.repositories;

import com.marco.restdocexample.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
}
