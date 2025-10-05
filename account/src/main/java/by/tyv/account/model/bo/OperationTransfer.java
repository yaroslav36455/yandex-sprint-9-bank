package by.tyv.account.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@Accessors(chain = true)
@ToString
public class OperationTransfer {
    private String sourceLogin;
    private String targetLogin;
    private BigDecimal amount;
}
