package pl.barbarski.pawel.funds.model;

import lombok.Getter;
import lombok.Setter;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FundsModel {

    private List<InvestmentStyle> investmentStyleList;
    private Integer amount;
    private Integer selectedInvestmentStyleId;
    private InvestmentStyle selectedInvestmentStyle;
    private List<SelectedFundDto> selectedFundDtoList;
    private List<Fund> selectedFundList;
    private Map<Fund, Integer> fundAmountMap;
    private Integer remainder;
}
