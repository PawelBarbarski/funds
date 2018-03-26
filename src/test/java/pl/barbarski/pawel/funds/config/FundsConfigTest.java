package pl.barbarski.pawel.funds.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FundsConfigTest {

    @Mock
    private InvestmentStyleRepository investmentStyleRepository;

    @InjectMocks
    private FundsConfig fundsConfig;

    @Test
    public void fundsModelTest() {

        //given
        FundType fundType1 = new FundType("type1");
        FundType fundType2 = new FundType("type2");

        List<InvestmentStyle> investmentStyleList = new LinkedList<>();

        Map<FundType, Integer> detailsMap1 = new HashMap<>();
        detailsMap1.put(fundType1, 30);
        detailsMap1.put(fundType2, 70);
        investmentStyleList.add(new InvestmentStyle("style1", detailsMap1));

        Map<FundType, Integer> detailsMap2 = new HashMap<>();
        detailsMap2.put(fundType1, 40);
        detailsMap2.put(fundType2, 60);
        investmentStyleList.add(new InvestmentStyle("style2", detailsMap2));

        when(investmentStyleRepository.findAll()).thenReturn(investmentStyleList);

        //when
        FundsModel fundsModel = fundsConfig.fundsModel();

        //then
        verify(investmentStyleRepository, times(1)).findAll();
        assertThat(fundsModel.getInvestmentStyleList()).isEqualTo(investmentStyleList);
    }
}