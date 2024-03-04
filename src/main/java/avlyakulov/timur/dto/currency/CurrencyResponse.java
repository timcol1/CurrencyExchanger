package avlyakulov.timur.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyResponse {

    private Long id;

    private String code;

    private String name;

    private String sign;

}