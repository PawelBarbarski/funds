package pl.barbarski.pawel.funds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.barbarski.pawel.funds.entity.InvestmentStyle;
import pl.barbarski.pawel.funds.exception.DataInconsistencyException;
import pl.barbarski.pawel.funds.model.FundsModel;
import pl.barbarski.pawel.funds.service.DomainService;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Controller
public class InvestmentSelectionController {

    @Resource
    private FundsModel fundsModel;

    @Autowired
    private DomainService domainService;

    @RequestMapping(path = "/home")
    public String homePage() {
        return "redirect:/investmentSelection.xhtml";
    }

    public void setAmount(Integer amount) {
        fundsModel.setAmount(amount);
    }

    public Integer getAmount() {
        return fundsModel.getAmount();
    }

    public void setSelectedInvestmentStyleId(Integer selectedInvestmentStyleId) {
        fundsModel.setSelectedInvestmentStyleId(selectedInvestmentStyleId);
    }

    public Integer getSelectedInvestmentStyleId() {
        return fundsModel.getSelectedInvestmentStyleId();
    }

    public List<InvestmentStyle> getInvestmentStyleList() {
        return fundsModel.getInvestmentStyleList();
    }

    public String submit() {
        InvestmentStyle selectedInvestmentStyle;
        try {
            selectedInvestmentStyle = domainService.retrieveInvestmentStyle(getSelectedInvestmentStyleId());
        } catch (DataInconsistencyException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        fundsModel.setSelectedInvestmentStyle(selectedInvestmentStyle);
        fundsModel.setSelectedFundDtoList(domainService.retrieveSelectedFundDtoList(selectedInvestmentStyle));
        return "fundsSelection?faces-redirect=true";
    }
}
