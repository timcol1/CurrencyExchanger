package avlyakulov.timur.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyRequest {

    private String code;

    private String fullName;

    private String sign;

}