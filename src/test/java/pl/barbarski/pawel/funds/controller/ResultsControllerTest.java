package pl.barbarski.pawel.funds.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.entity.FundType;
import pl.barbarski.pawel.funds.model.FundsModel;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultsControllerTest {

    @Mock
    private FundsModel fundsModel;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private ResultsController resultsController;

    @Test
    public void initWhenRemainderZeroTest() {

        //given
        Faces.setContext(facesContext);
        Integer remainder = 0;

        when(fundsModel.getRemainder()).thenReturn(remainder);

        //when
        resultsController.init();

        //then
        verify(fundsModel, times(1)).getRemainder();
        verify(facesContext, never()).addMessage(any(), any());
    }

    @Test
    public void initWhenRemainderNonZeroTest() {

        //given
        Faces.setContext(facesContext);
        Integer remainder = 123;
        String messageSummary = "Amount " + remainder + " is unassigned and should be returned.";

        when(fundsModel.getRemainder()).thenReturn(remainder);

        //when
        resultsController.init();

        //then
        verify(fundsModel, times(1)).getRemainder();
        ArgumentCaptor<FacesMessage> captor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext, times(1)).addMessage(isNull(), captor.capture());
        assertThat(captor.getValue().getSummary()).isEqualTo(messageSummary);
    }

    @Test
    public void getSelectedFundListTest() {

        //given
        List<Fund> selectedFundList = new LinkedList<>();
        selectedFundList.add(new Fund("fund1", new FundType("type1")));
        selectedFundList.add(new Fund("fund2", new FundType("type2")));

        when(fundsModel.getSelectedFundList()).thenReturn(selectedFundList);

        //when
        List<Fund> fundList = resultsController.getSelectedFundList();

        //then
        verify(fundsModel, times(1)).getSelectedFundList();
        assertThat(fundList).isEqualTo(selectedFundList);
    }

    @Test
    public void indexOfTest() {

        //given
        List<Fund> selectedFundList = new LinkedList<>();
        Fund fund1 = new Fund("fund1", new FundType("type1"));
        Fund fund2 = new Fund("fund2", new FundType("type2"));
        selectedFundList.add(fund1);
        selectedFundList.add(fund2);

        when(fundsModel.getSelectedFundList()).thenReturn(selectedFundList);

        //when
        int index1 = resultsController.indexOf(fund1);
        int index2 = resultsController.indexOf(fund2);

        //then
        verify(fundsModel, times(2)).getSelectedFundList();
        assertThat(index1).isEqualTo(1);
        assertThat(index2).isEqualTo(2);
    }

    @Test
    public void getFundAmountMapTest() {

        //given
        Map<Fund, Integer> fundAmountMapExpected = new HashMap<>();
        Fund fund1 = new Fund("fund1", new FundType("type1"));
        Fund fund2 = new Fund("fund2", new FundType("type2"));
        Integer amount1 = 123;
        Integer amount2 = 456;
        fundAmountMapExpected.put(fund1, amount1);
        fundAmountMapExpected.put(fund2, amount2);

        when(fundsModel.getFundAmountMap()).thenReturn(fundAmountMapExpected);

        //when
        Map<Fund, Integer> fundAmountMap = resultsController.getFundAmountMap();

        //then
        verify(fundsModel, times(1)).getFundAmountMap();
        assertThat(fundAmountMap).isEqualTo(fundAmountMapExpected);
    }
}