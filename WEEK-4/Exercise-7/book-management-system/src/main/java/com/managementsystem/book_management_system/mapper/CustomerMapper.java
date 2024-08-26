// src/main/java/com/managementsystem/book_management_system/mapper/CustomerMapper.java
package com.managementsystem.book_management_system.mapper;

import com.managementsystem.book_management_system.dto.CustomerDTO;
import com.managementsystem.book_management_system.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toCustomerDTO(Customer customer);
    Customer toCustomer(CustomerDTO customerDTO);
}
