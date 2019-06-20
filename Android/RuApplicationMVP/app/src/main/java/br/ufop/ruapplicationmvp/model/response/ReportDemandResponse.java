package br.ufop.ruapplicationmvp.model.response;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.DemandReport;

public class ReportDemandResponse {
    private List<DemandReport> demands;

    public List<DemandReport> getDemands() {
        return demands;
    }

}
