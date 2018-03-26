package pl.barbarski.pawel.funds.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvestmentStyleDetailTest {

    @Test
    public void investmentStyleDetailConstructorTest() {

        //given
        FundType fundType = new FundType("type");
        Integer percent = 12;

        //when
        InvestmentStyleDetail investmentStyleDetail = new InvestmentStyleDetail(fundType, percent);

        //then
        assertThat(investmentStyleDetail.getFundType()).isEqualTo(fundType);
        assertThat(investmentStyleDetail.getPercent()).isEqualTo(percent);
    }

}