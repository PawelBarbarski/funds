package pl.barbarski.pawel.funds.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class FundType {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "fundType")
    private Set<Fund> fundSet;

    @SuppressWarnings("unused") //used by Spring Data
    private FundType() {
    }

    public FundType(String type) {

        this.type = type;
        this.fundSet = new HashSet<>();
    }
}
