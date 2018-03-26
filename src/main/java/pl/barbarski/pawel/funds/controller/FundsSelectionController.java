package pl.barbarski.pawel.funds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.barbarski.pawel.funds.dto.SelectedFundDto;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.service.CalculationService;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Controller
public class FundsSelectionController {

    @Resource
    private FundsModel fundsModel;

    @Autowired
    private CalculationService calculationService;

    public List<SelectedFundDto> getSelectedFundDtoList() {
        return fundsModel.getSelectedFundDtoList();
    }

    public int indexOf(SelectedFundDto selectedFundDto) {
        return getSelectedFundDtoList().indexOf(selectedFundDto) + 1;
    }

    public String submit() {
        try {
            calculationService.calculate(fundsModel);
        } catch (DataInconsistencyException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        return "results?faces-redirect=true";
    }
}
