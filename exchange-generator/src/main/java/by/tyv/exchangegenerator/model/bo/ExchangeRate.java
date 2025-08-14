package by.tyv.exchangegenerator.model.bo;

import by.tyv.exchangegenerator.enums.CurrencyCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeRate {
    CurrencyCode code;
    BigDecimal rate;
}
