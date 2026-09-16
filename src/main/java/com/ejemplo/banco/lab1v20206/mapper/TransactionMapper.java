package com.ejemplo.banco.lab1v20206.mapper;
import com.ejemplo.banco.lab1v20206.DTO.TransactionDTO;
import com.ejemplo.banco.lab1v20206.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE= Mappers.getMapper(TransactionMapper.class);
    TransactionDTO toDTO(Transaction transaction);
}
