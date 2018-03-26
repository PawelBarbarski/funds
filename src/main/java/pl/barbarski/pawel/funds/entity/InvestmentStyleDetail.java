package pl.barbarski.pawel.funds.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
public class InvestmentStyleDetail {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FundType fundType;

    @Column(nullable = false)
    private Integer percent;

    @SuppressWarnings("unused") //used by Spring Data
    private InvestmentStyleDetail() {
    }

    InvestmentStyleDetail(FundType fundType, Integer percent) {

        this.fundType = fundType;
        this.percent = percent;
    }
}
