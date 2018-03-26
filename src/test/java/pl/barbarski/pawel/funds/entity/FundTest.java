package pl.barbarski.pawel.funds.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FundTest {

    @Test
    public void fundConstructorTest() {

        //given
        String name = "name";
        String type = "type";

        //when
        Fund fund = new Fund(name, new FundType(type));

        //then
        assertThat(fund.getName()).isEqualTo(name);
        assertThat(fund.getFundType().getType()).isEqualTo(type);
        assertThat(fund.getFundType().getFundSet()).contains(fund);
    }

    @Test
    public void compareToTest() {

        //given
        String name1 = "aName";
        String name2 = "bName";
        String type1 = "aType";
        String type2 = "bType";

        Fund fund1 = new Fund(name1, new FundType(type1));
        Fund fund2 = new Fund(name2, new FundType(type2));
        Fund fund3 = new Fund(name1, new FundType(type2));
        Fund fund4 = new Fund(name2, new FundType(type1));

        //when
        int compareResult1 = fund1.compareTo(fund1);
        int compareResult2 = fund1.compareTo(fund2);
        int compareResult3 = fund2.compareTo(fund1);
        int compareResult4 = fund1.compareTo(fund3);
        int compareResult5 = fund3.compareTo(fund1);
        int compareResult6 = fund1.compareTo(fund4);
        int compareResult7 = fund4.compareTo(fund1);
        int compareResult8 = fund3.compareTo(fund4);
        int compareResult9 = fund4.compareTo(fund3);

        //then
        assertThat(compareResult1).isEqualTo(0);
        assertThat(compareResult2).isLessThan(0);
        assertThat(compareResult3).isGreaterThan(0);
        assertThat(compareResult4).isLessThan(0);
        assertThat(compareResult5).isGreaterThan(0);
        assertThat(compareResult6).isLessThan(0);
        assertThat(compareResult7).isGreaterThan(0);
        assertThat(compareResult8).isGreaterThan(0);
        assertThat(compareResult9).isLessThan(0);
    }
}