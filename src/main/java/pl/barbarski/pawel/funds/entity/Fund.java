package pl.barbarski.pawel.funds.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
public class Fund implements Comparable<Fund> {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private FundType fundType;

    @SuppressWarnings("unused") //used by Spring Data
    private Fund() {
    }

    public Fund(String name, FundType fundType) {

        this.name = name;
        this.fundType = fundType;
        this.fundType.getFundSet().add(this);
    }

    @Override
    public int compareTo(Fund anotherFund) {
        String type = fundType.getType();
        String anotherType = anotherFund.fundType.getType();
        String anotherName = anotherFund.name;
        if (!type.equals(anotherType)) {
            return type.compareTo(anotherType);
        } else if (!name.equals(anotherName)) {
            return name.compareTo(anotherName);
        } else {
            return 0;
        }
    }
}
