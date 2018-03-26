package pl.barbarski.pawel.funds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.repository.FundRepository;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DatabaseInitialInsertService {

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private InvestmentStyleRepository investmentStyleRepository;

    @PostConstruct
    public void insertInitialData() {

        FundType polishFundType = new FundType("Polish");
        FundType foreignFundType = new FundType("Foreign");
        FundType monetaryFundType = new FundType("Monetary");

        Set<Fund> fundSet = new HashSet<>();
        fundSet.add(new Fund("Polish 1", polishFundType));
        fundSet.add(new Fund("Polish 2", polishFundType));
        fundSet.add(new Fund("Polish 3", polishFundType));
        fundSet.add(new Fund("Foreign 1", foreignFundType));
        fundSet.add(new Fund("Foreign 2", foreignFundType));
        fundSet.add(new Fund("Foreign 3", foreignFundType));
        fundSet.add(new Fund("Monetary 1", monetaryFundType));
        fundSet.add(new Fund("Monetary 2", monetaryFundType));
        fundSet.add(new Fund("Monetary 3", monetaryFundType));
        fundRepository.saveAll(fundSet);

        Set<InvestmentStyle> investmentStyleSet = new HashSet<>();

        Map<FundType, Integer> safeStyleDetailMap = new HashMap<>();
        safeStyleDetailMap.put(polishFundType, 20);
        safeStyleDetailMap.put(foreignFundType, 75);
        safeStyleDetailMap.put(monetaryFundType, 5);
        investmentStyleSet.add(new InvestmentStyle("safe", safeStyleDetailMap));

        Map<FundType, Integer> balancedStyleDetailMap = new HashMap<>();
        balancedStyleDetailMap.put(polishFundType, 30);
        balancedStyleDetailMap.put(foreignFundType, 60);
        balancedStyleDetailMap.put(monetaryFundType, 10);
        investmentStyleSet.add(new InvestmentStyle("balanced", balancedStyleDetailMap));

        Map<FundType, Integer> aggressiveStyleDetailMap = new HashMap<>();
        aggressiveStyleDetailMap.put(polishFundType, 20);
        aggressiveStyleDetailMap.put(foreignFundType, 75);
        aggressiveStyleDetailMap.put(monetaryFundType, 5);
        investmentStyleSet.add(new InvestmentStyle("aggressive", aggressiveStyleDetailMap));

        investmentStyleRepository.saveAll(investmentStyleSet);
    }
}
