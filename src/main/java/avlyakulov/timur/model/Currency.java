package avlyakulov.timur.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Currency {

    private int id;

    private String code;

    private String name;

    private String sign;


    public Currency(String code, String name, String sign) {
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public Currency(String code) {
        this.code = code;
    }
}