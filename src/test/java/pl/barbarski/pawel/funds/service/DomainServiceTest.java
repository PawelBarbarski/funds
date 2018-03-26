package pl.barbarski.pawel.funds.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DomainServiceTest {

    @Mock
    private InvestmentStyleRepository investmentStyleRepository;

    @InjectMocks
    private DomainService domainService;

    @Test
    public void retrieveSelectedFundDtoListTest() {

        //given
        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");
        List<SelectedFundDto> selectedFundDtoListExpected = new LinkedList<>();
        Fund fund1 = new Fund("Polish 1", polishFundType);
        Fund fund2 = new Fund("Polish 2", polishFundType);
        Fund fund3 = new Fund("Polish 3", polishFundType);
        Fund fund4 = new Fund("Foreign 1", foreignFundType);
        Fund fund5 = new Fund("Foreign 2", foreignFundType);
        Fund fund6 = new Fund("Foreign 3", foreignFundType);
        Fund fund7 = new Fund("Monetary 1", monetaryFundType);
        Fund fund8 = new Fund("Monetary 2", monetaryFundType);
        Fund fund9 = new Fund("Monetary 3", monetaryFundType);
        selectedFundDtoListExpected.add(new SelectedFundDto(fund4, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund5, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund6, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund7, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund8, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund9, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund1, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund2, false));
        selectedFundDtoListExpected.add(new SelectedFundDto(fund3, false));

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        InvestmentStyle investmentStyle = new InvestmentStyle("safe", safeStyleDetailMap);

        //when
        List<SelectedFundDto> selectedFundDtoList = domainService.retrieveSelectedFundDtoList(investmentStyle);

        //then
        assertThat(selectedFundDtoList).isEqualTo(selectedFundDtoListExpected);
    }

    @Test
    public void retrieveInvestmentStyleTest() throws DataInconsistencyException {

        //given
        Integer investmentStyleId = 123;

        FundType fundType1 = new FundType("type1");
        FundType fundType2 = new FundType("type2");
        FundType fundType3 = new FundType("type3");

        Integer percent1 = 30;
        Integer percent2 = 45;
        Integer percent3 = 25;

        Map<FundType, Integer> detailsMap = new HashMap<>();
        detailsMap.put(fundType1, percent1);
        detailsMap.put(fundType2, percent2);
        detailsMap.put(fundType3, percent3);

        String name = "name";

        InvestmentStyle investmentStyleExpected = new InvestmentStyle(name, detailsMap);
        Optional<InvestmentStyle> investmentStyleOptional = Optional.of(investmentStyleExpected);

        when(investmentStyleRepository.findById(investmentStyleId)).thenReturn(investmentStyleOptional);

        //when
        InvestmentStyle investmentStyle = domainService.retrieveInvestmentStyle(investmentStyleId);

        //then
        verify(investmentStyleRepository, times(1)).findById(investmentStyleId);
        assertThat(investmentStyle).isEqualTo(investmentStyleExpected);
    }

    @Test(expected = DataInconsistencyException.class)
    public void retrieveInvestmentStyleTestThrowsException() throws DataInconsistencyException {

        //given
        Integer investmentStyleId = 123;
        Optional<InvestmentStyle> investmentStyleOptional = Optional.empty();
        when(investmentStyleRepository.findById(investmentStyleId)).thenReturn(investmentStyleOptional);

        //when
        domainService.retrieveInvestmentStyle(investmentStyleId);

        //then
        verify(investmentStyleRepository, times(1)).findById(investmentStyleId);
    }
}