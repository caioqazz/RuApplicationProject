package br.ufop.ruapplicationmvvm.model.response;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.DemandReport;

public class ReportDemandResponse {
    private List<DemandReport> demands;

    public List<DemandReport> getDemands() {
        return demands;
    }

}
