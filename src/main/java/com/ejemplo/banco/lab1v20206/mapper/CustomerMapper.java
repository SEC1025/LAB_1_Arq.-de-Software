package com.ejemplo.banco.lab1v20206.mapper;
import com.ejemplo.banco.lab1v20206.DTO.CustomerDTO;
import com.ejemplo.banco.lab1v20206.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
