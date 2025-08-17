package by.tyv.exchange.mapper;

import by.tyv.exchange.enums.CurrencyCode;
import by.tyv.exchange.model.dto.ExchangeRateRequestDto;
import by.tyv.exchange.model.dto.ExchangeRateResponseDto;
import by.tyv.exchange.model.entity.ExchangeRateEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExchangeRateMapper {
    @Mapping(target = "value", source = "code")
    @Mapping(target = "title", source = "code", qualifiedByName = "CurrencyCodeEntityToResponseDto")
    ExchangeRateResponseDto toResponseDto(ExchangeRateEntity exchangeRate);

    List<ExchangeRateEntity> toEntity(List<ExchangeRateRequestDto> exchangeRates);
    @Mapping(target = "id", ignore = true)
    ExchangeRateEntity toEntity(ExchangeRateRequestDto exchangeRate);

    @Named("CurrencyCodeEntityToResponseDto")
    default String currencyCodeEntityToResponseDto(String currencyCodeStr) {
        return CurrencyCode.valueOf(currencyCodeStr).getTitle();
    }
}
