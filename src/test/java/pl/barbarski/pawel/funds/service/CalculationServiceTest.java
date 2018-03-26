package pl.barbarski.pawel.funds.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CalculationServiceTest {

    @InjectMocks
    private CalculationService calculationService;

    @Test
    public void calculateWithZeroRemainderTest() throws DataInconsistencyException {

        //given
        FundsModel fundsModel = new FundsModel();

        Integer amount = 10000;
        fundsModel.setAmount(amount);

        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");
        List<SelectedFundDto> selectedFundDtoList = new LinkedList<>();
        Fund fund1 = new Fund("Polish 1", polishFundType);
        Fund fund2 = new Fund("Polish 2", polishFundType);
        Fund fund3 = new Fund("Polish 3", polishFundType);
        Fund fund4 = new Fund("Foreign 1", foreignFundType);
        Fund fund5 = new Fund("Foreign 2", foreignFundType);
        Fund fund6 = new Fund("Foreign 3", foreignFundType);
        Fund fund7 = new Fund("Monetary 1", monetaryFundType);
        Fund fund8 = new Fund("Monetary 2", monetaryFundType);
        Fund fund9 = new Fund("Monetary 3", monetaryFundType);
        selectedFundDtoList.add(new SelectedFundDto(fund1, true));
        selectedFundDtoList.add(new SelectedFundDto(fund2, true));
        selectedFundDtoList.add(new SelectedFundDto(fund3, false));
        selectedFundDtoList.add(new SelectedFundDto(fund4, true));
        selectedFundDtoList.add(new SelectedFundDto(fund5, true));
        selectedFundDtoList.add(new SelectedFundDto(fund6, true));
        selectedFundDtoList.add(new SelectedFundDto(fund7, true));
        selectedFundDtoList.add(new SelectedFundDto(fund8, false));
        selectedFundDtoList.add(new SelectedFundDto(fund9, false));
        fundsModel.setSelectedFundDtoList(selectedFundDtoList);

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        InvestmentStyle selectedInvestmentStyle = new InvestmentStyle("safe", safeStyleDetailMap);
        fundsModel.setSelectedInvestmentStyle(selectedInvestmentStyle);

        List<Fund> selectedFundList = new LinkedList<>();
        selectedFundList.add(fund1);
        selectedFundList.add(fund2);
        selectedFundList.add(fund4);
        selectedFundList.add(fund5);
        selectedFundList.add(fund6);
        selectedFundList.add(fund7);

        Integer remainder = 0;

        Map<Fund, Integer> fundAmountMap = new HashMap<>();
        fundAmountMap.put(fund1, 1000);
        fundAmountMap.put(fund2, 1000);
        fundAmountMap.put(fund4, 2500);
        fundAmountMap.put(fund5, 2500);
        fundAmountMap.put(fund6, 2500);
        fundAmountMap.put(fund7, 500);

        //when
        calculationService.calculate(fundsModel);

        //then
        assertThat(fundsModel.getSelectedFundList()).isEqualTo(selectedFundList);
        assertThat(fundsModel.getRemainder()).isEqualTo(remainder);
        assertThat(fundsModel.getFundAmountMap()).isEqualTo(fundAmountMap);
    }

    @Test
    public void calculateWithNonZeroRemainder() throws DataInconsistencyException {

        //given
        FundsModel fundsModel = new FundsModel();

        Integer amount = 10001;
        fundsModel.setAmount(amount);

        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");
        List<SelectedFundDto> selectedFundDtoList = new LinkedList<>();
        Fund fund1 = new Fund("Polish 1", polishFundType);
        Fund fund2 = new Fund("Polish 2", polishFundType);
        Fund fund3 = new Fund("Polish 3", polishFundType);
        Fund fund4 = new Fund("Foreign 1", foreignFundType);
        Fund fund5 = new Fund("Foreign 2", foreignFundType);
        Fund fund6 = new Fund("Foreign 3", foreignFundType);
        Fund fund7 = new Fund("Monetary 1", monetaryFundType);
        Fund fund8 = new Fund("Monetary 2", monetaryFundType);
        Fund fund9 = new Fund("Monetary 3", monetaryFundType);
        selectedFundDtoList.add(new SelectedFundDto(fund1, true));
        selectedFundDtoList.add(new SelectedFundDto(fund2, true));
        selectedFundDtoList.add(new SelectedFundDto(fund3, false));
        selectedFundDtoList.add(new SelectedFundDto(fund4, true));
        selectedFundDtoList.add(new SelectedFundDto(fund5, true));
        selectedFundDtoList.add(new SelectedFundDto(fund6, true));
        selectedFundDtoList.add(new SelectedFundDto(fund7, true));
        selectedFundDtoList.add(new SelectedFundDto(fund8, false));
        selectedFundDtoList.add(new SelectedFundDto(fund9, false));
        fundsModel.setSelectedFundDtoList(selectedFundDtoList);

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        InvestmentStyle selectedInvestmentStyle = new InvestmentStyle("safe", safeStyleDetailMap);
        fundsModel.setSelectedInvestmentStyle(selectedInvestmentStyle);

        List<Fund> selectedFundList = new LinkedList<>();
        selectedFundList.add(fund1);
        selectedFundList.add(fund2);
        selectedFundList.add(fund4);
        selectedFundList.add(fund5);
        selectedFundList.add(fund6);
        selectedFundList.add(fund7);

        Integer remainder = 1;

        Map<Fund, Integer> fundAmountMap = new HashMap<>();
        fundAmountMap.put(fund1, 1000);
        fundAmountMap.put(fund2, 1000);
        fundAmountMap.put(fund4, 2500);
        fundAmountMap.put(fund5, 2500);
        fundAmountMap.put(fund6, 2500);
        fundAmountMap.put(fund7, 500);

        //when
        calculationService.calculate(fundsModel);

        //then
        assertThat(fundsModel.getSelectedFundList()).isEqualTo(selectedFundList);
        assertThat(fundsModel.getRemainder()).isEqualTo(remainder);
        assertThat(fundsModel.getFundAmountMap()).isEqualTo(fundAmountMap);
    }

    @Test
    public void calculateWithRandomAssignmentTest() throws DataInconsistencyException {

        //given
        FundsModel fundsModel = new FundsModel();

        Integer amount = 10000;
        fundsModel.setAmount(amount);

        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");
        List<SelectedFundDto> selectedFundDtoList = new LinkedList<>();
        Fund fund1 = new Fund("Polish 1", polishFundType);
        Fund fund2 = new Fund("Polish 2", polishFundType);
        Fund fund3 = new Fund("Polish 3", polishFundType);
        Fund fund4 = new Fund("Foreign 1", foreignFundType);
        Fund fund5 = new Fund("Foreign 2", foreignFundType);
        Fund fund6 = new Fund("Foreign 3", foreignFundType);
        Fund fund7 = new Fund("Monetary 1", monetaryFundType);
        Fund fund8 = new Fund("Monetary 2", monetaryFundType);
        Fund fund9 = new Fund("Monetary 3", monetaryFundType);
        selectedFundDtoList.add(new SelectedFundDto(fund1, true));
        selectedFundDtoList.add(new SelectedFundDto(fund2, true));
        selectedFundDtoList.add(new SelectedFundDto(fund3, true));
        selectedFundDtoList.add(new SelectedFundDto(fund4, true));
        selectedFundDtoList.add(new SelectedFundDto(fund5, true));
        selectedFundDtoList.add(new SelectedFundDto(fund6, false));
        selectedFundDtoList.add(new SelectedFundDto(fund7, true));
        selectedFundDtoList.add(new SelectedFundDto(fund8, false));
        selectedFundDtoList.add(new SelectedFundDto(fund9, false));
        fundsModel.setSelectedFundDtoList(selectedFundDtoList);

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        InvestmentStyle selectedInvestmentStyle = new InvestmentStyle("safe", safeStyleDetailMap);
        fundsModel.setSelectedInvestmentStyle(selectedInvestmentStyle);

        List<Fund> selectedFundList = new LinkedList<>();
        selectedFundList.add(fund1);
        selectedFundList.add(fund2);
        selectedFundList.add(fund3);
        selectedFundList.add(fund4);
        selectedFundList.add(fund5);
        selectedFundList.add(fund7);

        Integer remainder = 0;

        //when
        calculationService.calculate(fundsModel);

        //then
        assertThat(fundsModel.getSelectedFundList()).isEqualTo(selectedFundList);
        assertThat(fundsModel.getRemainder()).isEqualTo(remainder);
        Map<Fund, Integer> fundAmountMap = fundsModel.getFundAmountMap();
        assertThat(fundAmountMap.get(fund4)).isEqualTo(3750);
        assertThat(fundAmountMap.get(fund5)).isEqualTo(3750);
        assertThat(fundAmountMap.get(fund7)).isEqualTo(500);
        List<Integer> amountList = new LinkedList<>();
        amountList.add(fundAmountMap.get(fund1));
        amountList.add(fundAmountMap.get(fund2));
        amountList.add(fundAmountMap.get(fund3));
        assertThat(Collections.frequency(amountList, 666)).isEqualTo(2);
        assertThat(Collections.frequency(amountList, 668)).isEqualTo(1);
    }

    @Test(expected = DataInconsistencyException.class)
    public void calculateTestShouldThrowException() throws DataInconsistencyException {

        //given
        FundsModel fundsModel = new FundsModel();

        Integer amount = 10000;
        fundsModel.setAmount(amount);

        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");
        List<SelectedFundDto> selectedFundDtoList = new LinkedList<>();
        Fund fund1 = new Fund("Polish 1", polishFundType);
        Fund fund2 = new Fund("Polish 2", polishFundType);
        Fund fund3 = new Fund("Polish 3", polishFundType);
        Fund fund4 = new Fund("Foreign 1", foreignFundType);
        Fund fund5 = new Fund("Foreign 2", foreignFundType);
        Fund fund6 = new Fund("Foreign 3", foreignFundType);
        Fund fund7 = new Fund("Monetary 1", monetaryFundType);
        Fund fund8 = new Fund("Monetary 2", monetaryFundType);
        Fund fund9 = new Fund("Monetary 3", monetaryFundType);
        selectedFundDtoList.add(new SelectedFundDto(fund1, false));
        selectedFundDtoList.add(new SelectedFundDto(fund2, false));
        selectedFundDtoList.add(new SelectedFundDto(fund3, false));
        selectedFundDtoList.add(new SelectedFundDto(fund4, true));
        selectedFundDtoList.add(new SelectedFundDto(fund5, true));
        selectedFundDtoList.add(new SelectedFundDto(fund6, true));
        selectedFundDtoList.add(new SelectedFundDto(fund7, true));
        selectedFundDtoList.add(new SelectedFundDto(fund8, false));
        selectedFundDtoList.add(new SelectedFundDto(fund9, false));
        fundsModel.setSelectedFundDtoList(selectedFundDtoList);

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        InvestmentStyle selectedInvestmentStyle = new InvestmentStyle("safe", safeStyleDetailMap);
        fundsModel.setSelectedInvestmentStyle(selectedInvestmentStyle);

        //when
        calculationService.calculate(fundsModel);
    }
}