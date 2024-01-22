package avlyakulov.timur.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyResponse {

    private int id;

    private String code;

    @JsonProperty("name")
    private String fullName;

    private String sign;

}