package pl.barbarski.pawel.funds.entity;


import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InvestmentStyleTest {

    @Test
    public void investmentStylePersistenceConstructorTest() {

        //given
        Set<InvestmentStyleDetail> investmentStyleDetailSet = new HashSet<>();
        InvestmentStyleDetail investmentStyleDetail1 = new InvestmentStyleDetail(new FundType("type1"), 12);
        InvestmentStyleDetail investmentStyleDetail2 = new InvestmentStyleDetail(new FundType("type2"), 34);
        investmentStyleDetailSet.add(investmentStyleDetail1);
        investmentStyleDetailSet.add(investmentStyleDetail2);
        String name = "name";

        //when
        InvestmentStyle investmentStyle = new InvestmentStyle(name, investmentStyleDetailSet);

        //then
        assertThat(investmentStyle.getName()).isEqualTo(name);
        assertThat(investmentStyle.getInvestmentStyleDetailSet()).isEqualTo(investmentStyleDetailSet);
    }

    @Test
    public void investmentStyleConstructorTest() {

        //given
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

        Set<InvestmentStyleDetail> investmentStyleDetailSet = new HashSet<>();
        InvestmentStyleDetail investmentStyleDetail1 = new InvestmentStyleDetail(fundType1, percent1);
        InvestmentStyleDetail investmentStyleDetail2 = new InvestmentStyleDetail(fundType2, percent2);
        InvestmentStyleDetail investmentStyleDetail3 = new InvestmentStyleDetail(fundType3, percent3);
        investmentStyleDetailSet.add(investmentStyleDetail1);
        investmentStyleDetailSet.add(investmentStyleDetail2);
        investmentStyleDetailSet.add(investmentStyleDetail3);

        String name = "name";

        //when
        InvestmentStyle investmentStyle = new InvestmentStyle(name, detailsMap);

        //then
        assertThat(investmentStyle.getName()).isEqualTo(name);
        assertThat(investmentStyle.getInvestmentStyleDetailSet()).isEqualTo(investmentStyleDetailSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void investmentStyleConstructorThrowsExceptionTest() {

        //given
        FundType fundType1 = new FundType("type1");
        FundType fundType2 = new FundType("type2");
        FundType fundType3 = new FundType("type3");

        Integer percent1 = 10;
        Integer percent2 = 20;
        Integer percent3 = 30;

        Map<FundType, Integer> detailsMap = new HashMap<>();
        detailsMap.put(fundType1, percent1);
        detailsMap.put(fundType2, percent2);
        detailsMap.put(fundType3, percent3);

        String name = "name";

        //when
        new InvestmentStyle(name, detailsMap);
    }
}