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
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.service.CalculationService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FundsSelectionControllerTest {

    @Mock
    private FundsModel fundsModel;

    @Mock
    private CalculationService calculationService;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private FundsSelectionController fundsSelectionController;

    @Test
    public void getSelectedFundDtoListTest() {

        //given
        SelectedFundDto selectedFundDto1 = new SelectedFundDto(new Fund("fund1", new FundType("fundType1")), false);
        SelectedFundDto selectedFundDto2 = new SelectedFundDto(new Fund("fund2", new FundType("fundType2")), true);
        List<SelectedFundDto> selectedFundDtoListExpected = new LinkedList<>();
        selectedFundDtoListExpected.add(selectedFundDto1);
        selectedFundDtoListExpected.add(selectedFundDto2);

        when(fundsModel.getSelectedFundDtoList()).thenReturn(selectedFundDtoListExpected);

        //when
        List<SelectedFundDto> selectedFundDtoList = fundsSelectionController.getSelectedFundDtoList();

        //then
        assertThat(selectedFundDtoList).isEqualTo(selectedFundDtoListExpected);
    }

    @Test
    public void indexOfTest() {
        //given
        SelectedFundDto selectedFundDto1 = new SelectedFundDto(new Fund("fund1", new FundType("fundType1")), false);
        SelectedFundDto selectedFundDto2 = new SelectedFundDto(new Fund("fund2", new FundType("fundType2")), true);
        List<SelectedFundDto> selectedFundDtoListExpected = new LinkedList<>();
        selectedFundDtoListExpected.add(selectedFundDto1);
        selectedFundDtoListExpected.add(selectedFundDto2);

        when(fundsModel.getSelectedFundDtoList()).thenReturn(selectedFundDtoListExpected);

        //when
        int index1 = fundsSelectionController.indexOf(selectedFundDto1);
        int index2 = fundsSelectionController.indexOf(selectedFundDto2);

        //then
        assertThat(index1).isEqualTo(1);
        assertThat(index2).isEqualTo(2);
    }

    @Test
    public void submitTest() throws DataInconsistencyException {

        //when
        String redirectString = fundsSelectionController.submit();

        //then
        verify(calculationService, times(1)).calculate(fundsModel);
        assertThat(redirectString).isEqualTo("results?faces-redirect=true");
    }

    @Test
    public void submitTestWhenExceptionThrownTest() throws DataInconsistencyException {

        //given
        Faces.setContext(facesContext);
        String exceptionMessage = "exceptionMessage";
        doThrow(new DataInconsistencyException(exceptionMessage)).when(calculationService).calculate(fundsModel);

        //when
        String redirectString = fundsSelectionController.submit();

        //then
        verify(calculationService, times(1)).calculate(fundsModel);
        ArgumentCaptor<FacesMessage> captor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext, times(1)).addMessage(isNull(), captor.capture());
        assertThat(captor.getValue().getSummary()).isEqualTo(exceptionMessage);
        assertThat(redirectString).isNull();
    }
}