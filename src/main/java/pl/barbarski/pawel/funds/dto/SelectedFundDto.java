package pl.barbarski.pawel.funds.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.barbarski.pawel.funds.entity.Fund;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class SelectedFundDto {

    private Fund fund;
    private Boolean selected;
}
