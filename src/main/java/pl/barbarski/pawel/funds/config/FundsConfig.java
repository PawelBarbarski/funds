package pl.barbarski.pawel.funds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

@Configuration
public class FundsConfig {

    @Autowired
    private InvestmentStyleRepository investmentStyleRepository;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FundsModel fundsModel() {

        FundsModel fundsModel = new FundsModel();
        fundsModel.setInvestmentStyleList(investmentStyleRepository.findAll());
        return fundsModel;
    }
}
