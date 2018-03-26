package pl.barbarski.pawel.funds.repository;

import org.springframework.data.repository.Repository;
import pl.barbarski.pawel.funds.entity.Fund;

public interface FundRepository extends Repository<Fund, Integer> {

    void saveAll(Iterable<Fund> entities);
}
