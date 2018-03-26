package pl.barbarski.pawel.funds.service;

import org.springframework.stereotype.Service;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.entity.InvestmentStyleDetail;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

@Service
public class CalculationService {

    public void calculate(FundsModel fundsModel) throws DataInconsistencyException {

        Integer amount = fundsModel.getAmount();

        List<Fund> selectedFundList = getSelectedFundList(fundsModel.getSelectedFundDtoList());
        fundsModel.setSelectedFundList(selectedFundList);

        Map<FundType, Integer> fundTypeAmountMap = getFundTypeAmountMap(fundsModel.getSelectedInvestmentStyle(), amount);

        fundsModel.setRemainder(amount - getAssignedAmount(fundTypeAmountMap));

        Map<FundType, List<Fund>> fundTypeFundListMap = getFundTypeFundListMap(selectedFundList);
        Map<FundType, Long> noOfFundsOfTypeMap = getNoOfFundsOfTypeMap(selectedFundList);

        fundTypeCheck(noOfFundsOfTypeMap.keySet(), fundTypeAmountMap.keySet());

        Map<Fund, Integer> fundAmountMap = getFundAmountMap(selectedFundList, fundTypeAmountMap, noOfFundsOfTypeMap);
        Map<FundType, Integer> fundTypeAssignedAmountMap = getFundTypeAssignedAmountMap(fundAmountMap);

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (Map.Entry<FundType, Integer> fundTypeAmountEntry : fundTypeAmountMap.entrySet()) {
            int id = threadLocalRandom.nextInt(toIntExact(noOfFundsOfTypeMap.get(fundTypeAmountEntry.getKey())));
            Fund fund = fundTypeFundListMap.get(fundTypeAmountEntry.getKey()).get(id);
            fundAmountMap.put(fund, fundAmountMap.get(fund) + fundTypeAmountEntry.getValue()
                    - fundTypeAssignedAmountMap.get(fundTypeAmountEntry.getKey()));
        }

        fundsModel.setFundAmountMap(fundAmountMap);
    }

    private List<Fund> getSelectedFundList(List<SelectedFundDto> selectedFundDtoList) {
        return selectedFundDtoList.stream()
                .filter(SelectedFundDto::getSelected)
                .map(SelectedFundDto::getFund)
                .collect(Collectors.toList());
    }

    private Map<FundType, Long> getNoOfFundsOfTypeMap(List<Fund> selectedFundList) {
        return selectedFundList.stream().collect(Collectors.groupingBy(Fund::getFundType, Collectors.counting()));
    }

    private Map<FundType, List<Fund>> getFundTypeFundListMap(List<Fund> selectedFundList) {
        return selectedFundList.stream().collect(Collectors.groupingBy(Fund::getFundType));
    }

    private Map<FundType, Integer> getFundTypeAmountMap(InvestmentStyle selectedInvestmentStyle, Integer amount) {
        return selectedInvestmentStyle.getInvestmentStyleDetailSet().stream()
                .collect(Collectors.toMap(InvestmentStyleDetail::getFundType,
                        investmentStyleDetail -> investmentStyleDetail.getPercent() * amount / 100));
    }

    private Integer getAssignedAmount(Map<FundType, Integer> fundTypeAmountMap) {
        return fundTypeAmountMap.values().stream().reduce(0, (x, y) -> x + y);
    }

    private Map<Fund, Integer> getFundAmountMap(List<Fund> selectedFundList, Map<FundType, Integer> fundTypeAmountMap, Map<FundType, Long> noOfFundsOfTypeMap) {
        Map<Fund, Integer> fundAmountMap = new HashMap<>();
        selectedFundList.forEach(fund -> {
            FundType fundType = fund.getFundType();
            fundAmountMap.put(
                    fund,
                    fundTypeAmountMap.get(fundType) / toIntExact(noOfFundsOfTypeMap.get(fundType))
            );
        });
        return fundAmountMap;
    }

    private Map<FundType, Integer> getFundTypeAssignedAmountMap(Map<Fund, Integer> fundAmountMap) {
        Map<FundType, Integer> fundTypeAssignedAmountMap = new HashMap<>();
        for (Map.Entry<Fund, Integer> fundAmountEntry : fundAmountMap.entrySet()) {
            Fund fund = fundAmountEntry.getKey();
            FundType fundType = fund.getFundType();
            if (fundTypeAssignedAmountMap.containsKey(fundType)) {
                fundTypeAssignedAmountMap.put(fundType, fundTypeAssignedAmountMap.get(fundType) + fundAmountMap.get(fund));
            } else {
                fundTypeAssignedAmountMap.put(fundType, fundAmountMap.get(fund));
            }
        }
        return fundTypeAssignedAmountMap;
    }

    private void fundTypeCheck(Set<FundType> fundTypeSet, Set<FundType> fundTypeSetExpected) throws DataInconsistencyException {

        if(!fundTypeSet.containsAll(fundTypeSetExpected)) {
            throw new DataInconsistencyException("All fund types should have at least one fund chosen.");
        }


    }
}
