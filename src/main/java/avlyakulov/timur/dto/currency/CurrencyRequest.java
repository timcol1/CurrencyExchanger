package avlyakulov.timur.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyRequest {

    private String code;

    private String name;

    private String sign;

}