package pl.barbarski.pawel.funds.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.barbarski.pawel.funds.repository.FundRepository;
import pl.barbarski.pawel.funds.repository.InvestmentStyleRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseInitialInsertServiceTest {

    @Mock
    private FundRepository fundRepository;

    @Mock
    private InvestmentStyleRepository investmentStyleRepository;

    @InjectMocks
    private DatabaseInitialInsertService databaseInitialInsertService;

    @Test
    public void insertInitialDataTest() {

        //when
        databaseInitialInsertService.insertInitialData();

        //then
        verify(fundRepository, times(1)).saveAll(any());
        verify(investmentStyleRepository, times(1)).saveAll(any());
    }
}