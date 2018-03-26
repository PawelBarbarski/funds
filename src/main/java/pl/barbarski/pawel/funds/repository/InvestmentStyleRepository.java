package pl.barbarski.pawel.funds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;

import java.util.List;
import java.util.Optional;

public interface InvestmentStyleRepository extends Repository<InvestmentStyle, Integer> {

    void saveAll(Iterable<InvestmentStyle> entities);

    List<InvestmentStyle> findAll();

    @Query("select ist from InvestmentStyle ist join fetch ist.investmentStyleDetailSet where ist.id = :id")
    Optional<InvestmentStyle> findById(@Param("id") Integer id);
}
