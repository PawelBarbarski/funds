package pl.barbarski.pawel.funds.entity;

import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class FundTypeTest {

    @Test
    public void fundTypeConstructorTest() {

        //given
        String type = "type";

        //when
        FundType fundType = new FundType(type);

        //then
        assertThat(fundType.getType()).isEqualTo(type);
        assertThat(fundType.getFundSet()).isEqualTo(new HashSet<>());
    }

}