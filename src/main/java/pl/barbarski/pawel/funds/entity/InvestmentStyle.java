package pl.barbarski.pawel.funds.entity;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
public class InvestmentStyle {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<InvestmentStyleDetail> investmentStyleDetailSet;

    @SuppressWarnings("unused") //used by Spring Data
    private InvestmentStyle() {
    }

    @SuppressWarnings("unused") //used by Spring Data
    @PersistenceConstructor
    public InvestmentStyle(String name, Set<InvestmentStyleDetail> investmentStyleDetailSet) {

        this.name = name;
        this.investmentStyleDetailSet = investmentStyleDetailSet;
    }

    public InvestmentStyle(String name, Map<FundType, Integer> detailsMap) {

        if (!detailsMap.values().stream().reduce(0, (x, y) -> x + y).equals(100)) {
            throw new IllegalArgumentException("Percentages have to sum up to 100.");
        }

        this.name = name;
        this.investmentStyleDetailSet = new HashSet<>();
        detailsMap.forEach((fundType, percent) -> {
            InvestmentStyleDetail investmentStyleDetail = new InvestmentStyleDetail(fundType, percent);
            this.investmentStyleDetailSet.add(investmentStyleDetail);
        });
    }
}
