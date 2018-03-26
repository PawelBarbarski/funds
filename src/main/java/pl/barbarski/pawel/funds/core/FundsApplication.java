package pl.barbarski.pawel.funds.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "pl.barbarski.pawel.funds"
})
@EnableJpaRepositories(basePackages = {
        "pl.barbarski.pawel.funds"
})
@EntityScan(basePackages = {
        "pl.barbarski.pawel.funds"
})
public class FundsApplication {

    public static void main(String[] args) {

        SpringApplication.run(FundsApplication.class, args);
    }
}
