package pl.barbarski.pawel.funds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DomainService {

    @Autowired
    private InvestmentStyleRepository investmentStyleRepository;

    public List<SelectedFundDto> retrieveSelectedFundDtoList(InvestmentStyle investmentStyle) {

        return investmentStyle.getInvestmentStyleDetailSet().stream()
                .flatMap(investmentStyleDetail -> investmentStyleDetail.getFundType().getFundSet().stream())
                .sorted(Comparator.naturalOrder())
                .collect(ArrayList::new,
                        ((selectedFundDtoList, fund)
                                -> selectedFundDtoList.add(new SelectedFundDto(fund, false))),
                        ArrayList::addAll);
    }

    public InvestmentStyle retrieveInvestmentStyle(Integer investmentStyleId) throws DataInconsistencyException {

        return investmentStyleRepository.findById(investmentStyleId)
                .orElseThrow(() -> new DataInconsistencyException("Selected investment style was removed."));
    }
}
