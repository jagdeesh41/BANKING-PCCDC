package com.learn.mapper;


import com.learn.dto.CustomerDto;
import com.learn.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerDto customerDto);
    CustomerDto toCustomerDto(Customer customer);
}
