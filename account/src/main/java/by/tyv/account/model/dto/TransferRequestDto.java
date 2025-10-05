package by.tyv.account.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@Accessors(chain = true)
@ToString
public class TransferRequestDto {
    private String targetLogin;
    private BigDecimal amount;
}
