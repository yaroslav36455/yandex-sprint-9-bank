package by.tyv.account.model.entity;

import by.tyv.account.enums.CurrencyCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Accessors(chain=true)
@Table("account")
public class AccountEntity {
    Long id;
    String login;
    BigDecimal balance;
    CurrencyCode currency;
}
