package pl.barbarski.pawel.funds.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.service.DomainService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvestmentSelectionControllerTest {

    @Mock
    private FundsModel fundsModel;

    @Mock
    private DomainService domainService;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private InvestmentSelectionController investmentSelectionController;

    @Test
    public void homePageTest() {

        //when
        String homePageString = investmentSelectionController.homePage();

        //then
        assertThat(homePageString).isEqualTo("redirect:/investmentSelection.xhtml");
    }

    @Test
    public void setAmountTest() {

        //given
        Integer amount = 123;

        //when
        investmentSelectionController.setAmount(amount);

        //then
        verify(fundsModel, times(1)).setAmount(amount);
    }

    @Test
    public void getAmountTest() {

        //given
        Integer amountExpected = 123;
        when(fundsModel.getAmount()).thenReturn(amountExpected);

        //when
        Integer amount = investmentSelectionController.getAmount();

        //then
        verify(fundsModel, times(1)).getAmount();
        assertThat(amount).isEqualTo(amountExpected);
    }

    @Test
    public void setSelectedInvestmentStyleIdTest() {

        //given
        Integer investmentStyleId = 123;

        //when
        investmentSelectionController.setSelectedInvestmentStyleId(investmentStyleId);

        //then
        verify(fundsModel, times(1)).setSelectedInvestmentStyleId(investmentStyleId);
    }

    @Test
    public void getSelectedInvestmentStyleIdTest() {

        //given
        Integer investmentStyleIdExpected = 123;
        when(fundsModel.getSelectedInvestmentStyleId()).thenReturn(investmentStyleIdExpected);

        //when
        Integer investmentStyleId = investmentSelectionController.getSelectedInvestmentStyleId();

        //then
        verify(fundsModel, times(1)).getSelectedInvestmentStyleId();
        assertThat(investmentStyleId).isEqualTo(investmentStyleIdExpected);
    }

    @Test
    public void getInvestmentStyleListTest() {

        //given
        FundType fundType1 = new FundType("type1");
        FundType fundType2 = new FundType("type2");

        List<InvestmentStyle> investmentStyleListExpected = new LinkedList<>();

        Map<FundType, Integer> detailsMap1 = new HashMap<>();
        detailsMap1.put(fundType1, 30);
        detailsMap1.put(fundType2, 70);
        investmentStyleListExpected.add(new InvestmentStyle("style1", detailsMap1));

        Map<FundType, Integer> detailsMap2 = new HashMap<>();
        detailsMap2.put(fundType1, 40);
        detailsMap2.put(fundType2, 60);
        investmentStyleListExpected.add(new InvestmentStyle("style2", detailsMap2));

        when(fundsModel.getInvestmentStyleList()).thenReturn(investmentStyleListExpected);

        //when
        List<InvestmentStyle> investmentStyleList = investmentSelectionController.getInvestmentStyleList();

        //then
        verify(fundsModel, times(1)).getInvestmentStyleList();
        assertThat(investmentStyleList).isEqualTo(investmentStyleListExpected);
    }

    @Test
    public void submitTest() throws DataInconsistencyException {

        //given
        FundType fundType1 = new FundType("type1");
        FundType fundType2 = new FundType("type2");
        Map<FundType, Integer> detailsMap = new HashMap<>();
        detailsMap.put(fundType1, 30);
        detailsMap.put(fundType2, 70);
        Integer investmentStyleId = 123;
        InvestmentStyle investmentStyle = new InvestmentStyle("style", detailsMap);

        SelectedFundDto selectedFundDto1 = new SelectedFundDto(new Fund("fund1", fundType1), false);
        SelectedFundDto selectedFundDto2 = new SelectedFundDto(new Fund("fund2", fundType2), true);
        List<SelectedFundDto> selectedFundDtoList = new LinkedList<>();
        selectedFundDtoList.add(selectedFundDto1);
        selectedFundDtoList.add(selectedFundDto2);

        when(fundsModel.getSelectedInvestmentStyleId()).thenReturn(investmentStyleId);
        when(domainService.retrieveInvestmentStyle(investmentStyleId)).thenReturn(investmentStyle);
        when(domainService.retrieveSelectedFundDtoList(investmentStyle)).thenReturn(selectedFundDtoList);

        //when
        String redirectString = investmentSelectionController.submit();

        //then
        verify(fundsModel, times(1)).getSelectedInvestmentStyleId();
        verify(domainService, times(1)).retrieveInvestmentStyle(investmentStyleId);
        verify(fundsModel, times(1)).setSelectedInvestmentStyle(investmentStyle);
        verify(domainService, times(1)).retrieveSelectedFundDtoList(investmentStyle);
        verify(fundsModel, times(1)).setSelectedFundDtoList(selectedFundDtoList);
        assertThat(redirectString).isEqualTo("fundsSelection?faces-redirect=true");
    }

    @Test
    public void submitWhenExceptionThrownTest() throws DataInconsistencyException {

        //given
        Integer investmentStyleId = 123;
        Faces.setContext(facesContext);
        String exceptionMessage = "exceptionMessage";

        when(fundsModel.getSelectedInvestmentStyleId()).thenReturn(investmentStyleId);
        when(domainService.retrieveInvestmentStyle(investmentStyleId)).thenThrow(new DataInconsistencyException(exceptionMessage));

        //when
        String redirectString = investmentSelectionController.submit();

        //then
        verify(fundsModel, times(1)).getSelectedInvestmentStyleId();
        verify(domainService, times(1)).retrieveInvestmentStyle(investmentStyleId);
        ArgumentCaptor<FacesMessage> captor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext, times(1)).addMessage(isNull(), captor.capture());
        assertThat(captor.getValue().getSummary()).isEqualTo(exceptionMessage);
        verify(fundsModel, never()).setSelectedInvestmentStyle(any());
        verify(domainService, never()).retrieveSelectedFundDtoList(any());
        verify(fundsModel, never()).setSelectedFundDtoList(any());
        assertThat(redirectString).isNull();
    }
}