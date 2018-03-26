package pl.barbarski.pawel.funds.controller;

import org.springframework.stereotype.Controller;
import pl.barbarski.pawel.funds.entity.Fund;
import pl.barbarski.pawel.funds.model.FundsModel;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

@Controller
public class ResultsController {

    @Resource
    private FundsModel fundsModel;

    public void init() {
        Integer remainder = fundsModel.getRemainder();
        if (!remainder.equals(0)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Amount " + remainder + " is unassigned and should be returned."));
        }
    }

    public List<Fund> getSelectedFundList() {
        return fundsModel.getSelectedFundList();
    }

    public int indexOf(Fund fund) {
        return getSelectedFundList().indexOf(fund) + 1;
    }

    public Map<Fund, Integer> getFundAmountMap() {
        return fundsModel.getFundAmountMap();
    }
}
