package by.tyv.frontui.mapper;

import by.tyv.frontui.model.bo.Account;
import by.tyv.frontui.model.dto.AccountInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(target = "value", source = "balance")
    @Mapping(target = "exists", constant = "true")
    Account toBO(AccountInfoDto accountInfoDto);
}
